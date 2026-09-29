package com.vietphan.notification_service.service.impl;

import com.vietphan.notification_service.DTO.message.PaymentMessage;
import com.vietphan.notification_service.service.EmailService;
import com.vietphan.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;

    @Override
    public void processPaymentConfirmation(PaymentMessage message) {
        if (message == null) {
            log.warn("Received empty payment message");
            return;
        }

        // 1. Log ra console chính xác theo yêu cầu đề bài
        System.out.println("================================================================================");
        System.out.println("Payment confirmed for paymentId: " + message.getPaymentId());
        System.out.println("Detail: AccountId=" + message.getAccountId() 
                + ", Amount=" + message.getAmount() 
                + " " + message.getCurrency()
                + ", Status=" + message.getStatus()
                + ", Timestamp=" + message.getTimestamp());
        System.out.println("================================================================================");

        log.info("Payment confirmed for paymentId: {}", message.getPaymentId());

        // 2. Tái sử dụng logic gửi email từ branch feature/otp
        String recipientEmail = "user" + message.getAccountId() + "@example.com";
        emailService.sendPaymentConfirmationEmail(recipientEmail, message.getPaymentId(), message.getAmount(), message.getCurrency());
    }
}
