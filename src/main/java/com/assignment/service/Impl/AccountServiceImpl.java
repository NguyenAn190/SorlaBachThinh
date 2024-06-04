package com.assignment.service.Impl;

import com.assignment.dto.AccountDTO;
import com.assignment.dto.RegisterDTO;
import com.assignment.entity.Accounts;
import com.assignment.repository.AccountsRepository;
import com.assignment.service.AccountService;
import com.assignment.utils.RandomUtils;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountsRepository repo;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountServiceImpl(AccountsRepository repo, ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Accounts findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public Accounts findByPhone(String phone) {
        return repo.findByPhoneNumber(phone);
    }

    @Override
    public List<Accounts> findAll() {
        return repo.findAll();
    }

    @Override
    public Page<Accounts> findAll(Pageable pageable) {
        return repo.findAllByOrderByDateCreatedDesc(pageable);
    }

    @Override
    public Page<Accounts> findNonAdminAccounts(Pageable pageable) {
        return repo.findNonAdminAccounts(pageable);
    }

    @Override
    public Accounts findById(long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public Accounts findByToken(String token) {
        return repo.findByToken(token);
    }

    @Override
    public Accounts save(Accounts accounts) {
        return repo.save(accounts);
    }

    @Override
    public Accounts login(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public Accounts register(RegisterDTO accountDTO) {
        accountDTO.setDateCreated(new Timestamp(System.currentTimeMillis()));
        accountDTO.setIsAcctive(Boolean.FALSE);
        accountDTO.setRole("USER");
        accountDTO.setToken(RandomUtils.RandomToken(30));
        Accounts account = new Accounts();
        modelMapper.map(accountDTO, account);
        return save(account);
    }

    @Override
    public Accounts registerGoogle(String email, String password, String phone, String fullname) {
        Accounts accounts = new Accounts();
        accounts.setEmail(email);
        accounts.setPhoneNumber(phone);
        accounts.setPasswords(password);
        accounts.setFullname(fullname);
        accounts.setDateCreated(new Timestamp(System.currentTimeMillis()));
        accounts.setRole("USER");
        accounts.setAcctive(Boolean.TRUE);
        return repo.save(accounts);
    }

    @Override
    public Accounts forgotpass(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public Accounts updatePass(String email, String password) {
        // Bước 1: Tìm kiếm tài khoản dựa trên địa chỉ email
        Accounts accounts = repo.findByEmail(email);

        // Bước 2: Kiểm tra xem tài khoản có tồn tại hay không
        if (accounts == null) {
            // Xử lý tài khoản không tồn tại (ví dụ: thông báo lỗi)
            return null;
        }

        // Bước 3: Cập nhật mật khẩu và lưu tài khoản đã cập nhật vào cơ sở dữ liệu
        accounts.setPasswords(password);
        return repo.save(accounts);
    }

    @Override
    public Accounts add(AccountDTO accountDTO, String role) {
        accountDTO.setRole(role);
        Accounts accounts = new Accounts();
        modelMapper.map(accountDTO, accounts);
        return repo.save(accounts);
    }

    @Override
    public Accounts update(AccountDTO accountDTO, Boolean active) {
        accountDTO.setIsAcctive(active);
        Accounts accounts = findById(accountDTO.getAccountId());
        modelMapper.map(accountDTO, accounts);
        return repo.save(accounts);
    }

    @Override
    public void userupdateAccounts(String email, String fullname, String phone) {
        Accounts accounts = repo.findByEmail(email);
        if (accounts != null) {
            accounts.setFullname(fullname);
            accounts.setPhoneNumber(phone);
            repo.save(accounts);
        } else {
            throw new EntityNotFoundException("Không tìm thấy tài khoản với email: " + email);
        }
    }

}
