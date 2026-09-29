package com.vietphan.notification_service.service;

import com.vietphan.notification_service.DTO.message.PaymentMessage;

public interface NotificationService {

    void processPaymentConfirmation(PaymentMessage message);
}
