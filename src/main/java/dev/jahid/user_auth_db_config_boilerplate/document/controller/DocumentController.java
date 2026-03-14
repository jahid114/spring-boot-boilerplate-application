package dev.jahid.user_auth_db_config_boilerplate.document.controller;


import dev.jahid.user_auth_db_config_boilerplate.document.service.DocumentService;
import dev.jahid.user_auth_db_config_boilerplate.utility.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/documents" )
@RequiredArgsConstructor( onConstructor = @__( @Autowired) )
public class DocumentController {

    private final DocumentService documentService;

    @DeleteMapping( "/{id}" )
    public ApiResponse<?> deleteDocument( @PathVariable Long id ) {
        documentService.deleteDocument( id );
        return new ApiResponse<>( "Document deleted successfully", HttpStatus.NO_CONTENT.value() );
    }
}
