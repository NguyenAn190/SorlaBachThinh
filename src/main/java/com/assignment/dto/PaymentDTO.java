package com.assignment.dto;

import com.assignment.entity.CartItem;
import com.assignment.entity.Orders;
import com.assignment.entity.Products;
import lombok.Data;

import java.util.List;

@Data
public class PaymentDTO {

    String email;

    String full_name;

    String phone;

    Orders orders;

    List<Products> products;

    List<CartItem> cartItems;
}
