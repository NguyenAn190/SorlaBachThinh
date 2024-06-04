package com.assignment.repository;

import com.assignment.entity.DiscountCodes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<DiscountCodes, String> {

    Page<DiscountCodes> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
