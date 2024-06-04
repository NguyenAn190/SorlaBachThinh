package com.assignment.repository;

import com.assignment.entity.Categorys;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categorys, Long> {

    @Query("SELECT c FROM Categorys c")
    List<Categorys> findTop4Categories(Pageable pageable);

    Categorys findByCategoryId(long categoryId);

}
