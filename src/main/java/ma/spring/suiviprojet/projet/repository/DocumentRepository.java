package ma.spring.suiviprojet.projet.repository;

import ma.spring.suiviprojet.projet.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
}