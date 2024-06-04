package com.assignment.service;

import com.assignment.dto.AccountDTO;
import com.assignment.dto.RegisterDTO;
import com.assignment.entity.Accounts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {

    Accounts findByEmail(String email);

    Accounts findByPhone(String phone);

    List<Accounts> findAll();

    Page<Accounts> findAll(Pageable pageable);

    Page<Accounts> findNonAdminAccounts(Pageable pageable);

    Accounts findById(long id);

    Accounts findByToken(String token);

    Accounts save(Accounts accounts);

    Accounts login(String email);

    Accounts register(RegisterDTO accountDTO);

    Accounts registerGoogle(String email, String password, String phone, String fullname);

    Accounts forgotpass(String email);

    Accounts updatePass(String email, String password);

    Accounts add(AccountDTO accountDTO, String role);

    Accounts update(AccountDTO accountDTO, Boolean active);

    void userupdateAccounts(String email, String fullname, String phone);
}
