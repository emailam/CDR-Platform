package com.example.msloader.repository;

import com.example.msloader.model.CDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CDRRepository extends JpaRepository<CDR, UUID> {

}
