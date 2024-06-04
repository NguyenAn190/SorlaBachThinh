package com.assignment.dto;

import com.assignment.entity.Accounts;
import com.assignment.entity.DiscountCodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDiscountDTO {

    private long id;

    private long accountId;

    private String discountCode;

    private Timestamp usedAt;

    private Accounts accountsByAccountId;

    private DiscountCodes discountCodesByDiscountCode;
}
