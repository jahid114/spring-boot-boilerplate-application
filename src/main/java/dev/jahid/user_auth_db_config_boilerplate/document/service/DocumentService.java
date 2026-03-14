package dev.jahid.user_auth_db_config_boilerplate.document.service;

import dev.jahid.user_auth_db_config_boilerplate.document.DocumentType;
import dev.jahid.user_auth_db_config_boilerplate.document.DocumentDTO;
import dev.jahid.user_auth_db_config_boilerplate.document.model.Document;
import dev.jahid.user_auth_db_config_boilerplate.document.repository.DocumentRepository;
import dev.jahid.user_auth_db_config_boilerplate.user.model.User;
import dev.jahid.user_auth_db_config_boilerplate.utility.enums.EntityName;
import dev.jahid.user_auth_db_config_boilerplate.utility.exceptions.NotFoundException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final MinioClient minioClient;
    private final DocumentRepository documentRepository;

    @Value( "${minio.bucket-name}" )
    private String defaultBucketName;

    @Value( "${minio.public-url-expiry:86400}" )
    private int publicUrlExpirySeconds;

    @PersistenceContext
    private EntityManager entityManager;

    public Document uploadDocument( MultipartFile file, DocumentType documentType,
                                    EntityName entityName, Long entityId ) throws IOException, ServerException, InsufficientDataException,
            ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        String baseDirectory = documentType.getDirectory();
        String subDirectory = generateSubDirectory();

        String objectPath = buildObjectPath( baseDirectory, subDirectory, file.getOriginalFilename() );
        
        try (InputStream inputStream = file.getInputStream()) {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket( defaultBucketName )
                            .object( objectPath )
                            .stream( inputStream, file.getSize(), -1 )
                            .contentType( file.getContentType() )
                            .build()
            );

            Document document = new Document();
            document.setFileName( file.getOriginalFilename() );
            document.setBaseDirectory( baseDirectory );
            document.setSubDirectory( subDirectory );
            document.setContentType( file.getContentType() );
            document.setUploadedForEntity( entityName );
            document.setUploadedForId( entityId );
            document.setDocumentType( documentType );
            
            return documentRepository.save( document );
        }
    }

    private String generateSubDirectory() {
        LocalDateTime now = LocalDateTime.now();

        return String.format( "%d-%02d-%02d/%d",
            now.getYear(), now.getMonthValue(),
            now.getDayOfMonth(), java.time.Instant.now().toEpochMilli() );
    }

    public Document uploadDocument( DocumentDTO documentDTO, Long entityId, EntityName entityName ) {

        String baseDirectory = documentDTO.getDocumentType().getDirectory();
        String subDirectory = generateSubDirectory();
        String objectPath = buildObjectPath( baseDirectory, subDirectory, documentDTO.getFileName() );
        
        try ( InputStream inputStream = new ByteArrayInputStream( documentDTO.getFileData() ) ) {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket( defaultBucketName )
                            .object( objectPath )
                            .stream( inputStream, documentDTO.getFileData().length, -1 )
                            .contentType( documentDTO.getContentType() )
                            .build()
            );

            Document document = new Document();
            document.setFileName( documentDTO.getFileName() );
            document.setBaseDirectory( baseDirectory );
            document.setSubDirectory( subDirectory );
            document.setContentType( documentDTO.getContentType() );
            document.setUploadedForEntity( entityName );
            document.setUploadedForId( entityId );
            document.setDocumentType( documentDTO.getDocumentType() );
            
            return documentRepository.save( document );
        } catch ( IOException | ServerException | InsufficientDataException | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e ) {
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension( String fileName ) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring( lastDotIndex + 1 ) : "";
    }

    public byte[] downloadDocument( Document document ) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String objectPath = buildObjectPath( document.getBaseDirectory(), document.getSubDirectory(), document.getFileName() );
        
        try ( InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket( defaultBucketName )
                        .object( objectPath )
                        .build()
        ) ) {
            return IOUtils.toByteArray(stream);
        }
    }

    public String getDocumentUrl( Document document) {
        String objectPath = buildObjectPath( document.getBaseDirectory(), document.getSubDirectory(), document.getFileName() );

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method( Method.GET )
                            .bucket( defaultBucketName )
                            .object( objectPath )
                            .expiry( publicUrlExpirySeconds )
                            .build()
            );
        } catch ( IOException | ServerException | InsufficientDataException | ErrorResponseException |
                  NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                  InternalException e ) {
            throw new RuntimeException(e);
        }
    }

    private void deleteDocumentFromRelatedEntity( Long entityId, DocumentType documentType ){

        if( documentType == DocumentType.PROFILE_PHOTO ) {
            Optional.ofNullable( entityManager.find( User.class, entityId ) )
                    .ifPresent( user -> user.setProfilePic( null ) );
        }
    }

    @Transactional
    public void deleteDocument( Long id ) {
        Document document = documentRepository.findById( id )
                .orElseThrow( () -> new NotFoundException( "Document", id ) );

        deleteDocumentFromRelatedEntity( document.getUploadedForId(), document.getDocumentType() );
        deleteDocument( document );
    }

    public void deleteDocument( Document document ){

        String objectPath = buildObjectPath( document.getBaseDirectory(), document.getSubDirectory(), document.getFileName() );

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(defaultBucketName)
                            .object(objectPath)
                            .build()
            );
            documentRepository.delete( document );
        } catch ( IOException | ServerException | InsufficientDataException | ErrorResponseException |
                  NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                  InternalException e ) {
            throw new RuntimeException(e);
        }
    }

    private String buildObjectPath( String baseDir, String subDir, String fileName ) {
        StringBuilder pathBuilder = new StringBuilder();
        
        if ( baseDir != null && !baseDir.trim().isEmpty() ) {
            pathBuilder.append( baseDir.trim().replaceAll( "^/+|/+$", "" ) );
            pathBuilder.append( "/");
        }
        
        if ( subDir != null && !subDir.trim().isEmpty() ) {
            pathBuilder.append( subDir.trim().replaceAll( "^/+|/+$", "" ) );
            pathBuilder.append( "/" );
        }
        
        pathBuilder.append( fileName );
        return pathBuilder.toString();
    }
    

    public Document editDocument( Document existingDocument, DocumentDTO documentDTO, Long entityId, EntityName entityName ) {
        if ( existingDocument != null )
            deleteDocument( existingDocument );

        Document updatedDocument = uploadDocument( documentDTO, entityId, entityName );

        return documentRepository.save( updatedDocument );
    }
}
