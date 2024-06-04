package com.assignment.dto;

import com.assignment.entity.Accounts;
import com.assignment.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {

    private long cartId;

    private long accountId;

    private String productId;

    private int amount;

    private int price;

    private Timestamp createdAt;

    private Accounts accountsByAccountId;

    private Products productsByProductId;
}
