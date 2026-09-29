package com.vietphan.notification_service.service;

public interface EmailService {

    void sendPaymentConfirmationEmail(String toEmail, Long paymentId, double amount, String currency);

    void sendOtpEmail(String toEmail, String otpCode);
}
