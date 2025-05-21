package com.example.msbackend.repository;

import com.example.msbackend.model.CDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CDRRepository extends JpaRepository<CDR, UUID> {
}
