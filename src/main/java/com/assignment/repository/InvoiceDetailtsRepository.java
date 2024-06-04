package com.assignment.repository;

import com.assignment.entity.InvoiceDetailts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailtsRepository extends JpaRepository<InvoiceDetailts, Integer> {
}