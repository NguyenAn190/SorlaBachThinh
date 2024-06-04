package com.assignment.controller.restcontroller;

import com.assignment.entity.Accounts;
import com.assignment.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200/")
public class AccountAPI {

    @Autowired
    AccountsRepository accountsRepository;

    @GetMapping("/account")
    public List<Accounts> displayMessage() {
        return accountsRepository.findAll();
    }

    @PostMapping("/account")
    public Accounts addAccount(@RequestBody Accounts accountsEntity) {
        return accountsRepository.save(accountsEntity);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Accounts> getAccountById(@PathVariable Long accountId) {
        Accounts accountsEntity = accountsRepository.getById(accountId);
        return ResponseEntity.ok().body(accountsEntity);
    }

    @PutMapping("/account/{accountId}")
    public ResponseEntity<Accounts> updateAccount(@PathVariable Long accountId, @RequestBody Accounts accountsEntity) {
        Accounts getAccounts = accountsRepository.getById(accountId);
        getAccounts.setEmail(accountsEntity.getEmail());
        getAccounts.setPasswords(accountsEntity.getPasswords());
        getAccounts.setDateCreated(new Timestamp(System.currentTimeMillis()));
        getAccounts.setAcctive(accountsEntity.getAcctive());

        Accounts updateAccounts = accountsRepository.save(getAccounts);
        return ResponseEntity.ok().body(updateAccounts);
    }

    @DeleteMapping("/account/{accountId}")
    public String deleteAccount(@PathVariable Long accountId) {
        Accounts accountsEntity = accountsRepository.getById(accountId);
        accountsRepository.delete(accountsEntity);
        return "A record successfully deleted !";
    }
}
