package dev.jahid.user_auth_db_config_boilerplate.document.repository;

import dev.jahid.user_auth_db_config_boilerplate.document.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
