package dev.jahid.user_auth_db_config_boilerplate.config;

import io.minio.MinioClient;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties( prefix = "minio" )
public class MinioConfig {

    @Value( "${minio.url}" )
    private String url;

    @Value( "${minio.access-key}" )
    private String accessKey;

    @Value( "${minio.secret-key}" )
    private String secretKey;

    @Bean
    public MinioClient minioClient() throws Exception {
        log.info("Initializing MinIO client with endpoint: {}", url);

        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, secretKey)
                    .build();
            if ( minioClient != null ) {
                log.info( "Buckets available: {}", !minioClient.listBuckets().stream().map(Bucket::name).toList().isEmpty() );
                log.info( "Successfully connected to MinIO server at: {}", url );
            }

            return minioClient;

        } catch ( Exception e ) {
            log.error( "Failed to initialize MinIO client: {}", e.getMessage(), e );
            throw new Exception( "Failed to initialize MinIO client: " + e.getMessage(), e );
        }
    }
}
