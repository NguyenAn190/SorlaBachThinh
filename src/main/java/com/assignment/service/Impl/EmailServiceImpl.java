package com.assignment.service.Impl;

import com.assignment.dto.ForgotPassDTO;
import com.assignment.dto.PaymentDTO;
import com.assignment.dto.RegisterDTO;
import com.assignment.service.EmailService;
import com.assignment.service.ThymeleafService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@EnableScheduling
public class EmailServiceImpl implements EmailService {

    Queue<RegisterDTO> emailQueue = new LinkedList<>();
    Queue<ForgotPassDTO> emailForgotQueue = new LinkedList<>();
    Queue<PaymentDTO> emailPaymentQueue = new LinkedList<>();

    @Autowired
    JavaMailSender sender;

    @Autowired
    ThymeleafService thymeleafService;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void queueEmail(RegisterDTO mail) {
        emailQueue.add(mail);
    }

    @Override
    public void queueEmail() {
        queueEmail(new RegisterDTO());
    }

    @Override
    public void sendMailRegister() {
        while (!emailQueue.isEmpty()) {
            RegisterDTO registerDTO = emailQueue.poll();
            try {
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

                helper.setTo(registerDTO.getEmail());

                Map<String, Object> variables = new HashMap<>();
                variables.put("email", registerDTO.getEmail());
                variables.put("password", registerDTO.getPasswords());
                variables.put("full_name", registerDTO.getFullname());
                variables.put("phone_number", registerDTO.getPhoneNumber());
                variables.put("token", registerDTO.getToken());
                SimpleDateFormat sdfdate = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss");
                variables.put("date", sdfdate.format(new Date()));
                variables.put("time", sdftime.format(new Date()));

                helper.setFrom(email);
                helper.setText(thymeleafService.createContent("verify-email", variables), true);
                helper.setSubject("SOLAR BÁCH THỊNH CẢM ƠN KHÁCH HÀNG");

                sender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void queueForgotEmail(ForgotPassDTO mail) {
        emailForgotQueue.add(mail);
    }

    @Override
    public void sendMailForgotPass() {
        while (!emailForgotQueue.isEmpty()) {
            ForgotPassDTO forgotPassDTO = emailForgotQueue.poll();
            try {
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

                helper.setTo(forgotPassDTO.getEmail());

                Map<String, Object> variables = new HashMap<>();
                variables.put("full_name", forgotPassDTO.getFull_name());
                variables.put("verifyCode", forgotPassDTO.getVerifyCode());

                helper.setFrom(email);
                helper.setText(thymeleafService.createContent("send-otp", variables), true);
                helper.setSubject("SOLAR BÁCH THỊNH CẢM ƠN QUÝ KHÁCH");

                sender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void queueMailPayment(PaymentDTO mail) {
        emailPaymentQueue.add(mail);
    }

    @Override
    public void sendMailPayment() {
        while (!emailPaymentQueue.isEmpty()) {
            PaymentDTO paymentDTO = emailPaymentQueue.poll();
            try {
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

                helper.setTo(paymentDTO.getEmail());

                Map<String, Object> variables = new HashMap<>();
                variables.put("order", paymentDTO.getOrders());
                variables.put("full_name", paymentDTO.getFull_name());
                variables.put("products", paymentDTO.getProducts());
                variables.put("cartItems", paymentDTO.getCartItems());
                SimpleDateFormat sdfdate = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss");
                variables.put("date", sdfdate.format(new Date()));
                variables.put("time", sdftime.format(new Date()));

                helper.setFrom(email);
                helper.setText(thymeleafService.createContent("mail-payment", variables), true);
                helper.setSubject("SOLAR BÁCH THỊNH CẢM ƠN QUÝ KHÁCH");

                sender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void processRegister() {
        sendMailRegister();
    }

    @Scheduled(fixedDelay = 5000)
    public void processForgot() {
        sendMailForgotPass();
    }

    @Scheduled(fixedDelay = 5000)
    public void processMailPayment() {
        sendMailPayment();
    }
}
