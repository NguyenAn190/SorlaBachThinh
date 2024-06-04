package com.assignment.service;

import com.assignment.dto.ForgotPassDTO;
import com.assignment.dto.PaymentDTO;
import com.assignment.dto.RegisterDTO;

public interface EmailService {

    void queueEmail(RegisterDTO mail);

    void queueEmail();

    void sendMailRegister();

    void queueForgotEmail(ForgotPassDTO mail);

    void sendMailForgotPass();

    void queueMailPayment(PaymentDTO mail);

    void sendMailPayment();

}
