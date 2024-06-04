package com.assignment.repository;

import com.assignment.entity.Accounts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Page<Accounts> findAllByOrderByDateCreatedDesc(Pageable pageable);

    Accounts findByEmailAndPasswords(String email, String password);

    Accounts findByEmail(String email);

    Accounts findByPhoneNumber(String phone);

    Accounts findByToken(String token);

    @Query("SELECT a FROM Accounts a WHERE a.role != 'ADMIN' ORDER BY a.dateCreated desc")
    Page<Accounts> findNonAdminAccounts(Pageable pageable);
}
