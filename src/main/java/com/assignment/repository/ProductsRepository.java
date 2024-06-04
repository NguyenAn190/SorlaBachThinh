package com.assignment.repository;

import com.assignment.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, String> {

    List<Products> findAllByOrderByDateCreatedDesc();

    List<Products> findByProductTypeId(long productTypeId);

    List<Products> findByProductNameContaining(String search);

    @Query("SELECT p FROM Products p JOIN p.cartItemsByProductId ct WHERE ct.invoiceCartItemId = :invoiceCartItemId")
    List<Products> findByInvoiceCartItemId(long invoiceCartItemId);

    @Query("SELECT p FROM Products p " +
            "JOIN p.productTypesByProductTypeId pt " +
            "JOIN pt.categorysByCategoryId c " +
            "WHERE c.categoryId = :categoryId " +
            "AND pt.productTypeId = :productTypeId")
    List<Products> findProductsByCategoryAndTypeName(
            @Param("categoryId") Long categoryId,
            @Param("productTypeId") Long productTypeId
    );


    @Query("SELECT p FROM Products p JOIN p.productTypesByProductTypeId pt JOIN pt.categorysByCategoryId c WHERE c.categoryId = :categoryId")
    List<Products> findByCategoryId(@Param("categoryId") Long categoryId);
}
