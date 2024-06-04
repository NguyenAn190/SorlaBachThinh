package com.assignment.repository;

import com.assignment.entity.HistoryUpdateProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryUpdateProducts, Long> {
    List<HistoryUpdateProducts> findAllByOrderByUpdateDateDesc();

    Page<HistoryUpdateProducts> findAllByOrderByUpdateDateDesc(Pageable pageable);
}
