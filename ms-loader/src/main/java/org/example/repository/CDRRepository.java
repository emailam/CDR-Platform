package org.example.repository;

import org.example.model.entity.CDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CDRRepository extends JpaRepository<CDR, UUID> {
    // I might add custom queries here.
}
