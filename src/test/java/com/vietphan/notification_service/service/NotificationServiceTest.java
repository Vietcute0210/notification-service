package com.vietphan.notification_service.service;

import com.vietphan.notification_service.DTO.message.PaymentMessage;
import com.vietphan.notification_service.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private PaymentMessage sampleMessage;

    @BeforeEach
    void setUp() {
        sampleMessage = PaymentMessage.builder()
                .paymentId(12345L)
                .accountId(1L)
                .amount(500000.0)
                .currency("VND")
                .status("SUCCESS")
                .timestamp(Instant.now())
                .build();
    }

    @Test
    @DisplayName("Process payment confirmation logs to console and sends email")
    void testProcessPaymentConfirmation_Success() {
        doNothing().when(emailService).sendPaymentConfirmationEmail(anyString(), anyLong(), anyDouble(), anyString());

        notificationService.processPaymentConfirmation(sampleMessage);

        verify(emailService, times(1)).sendPaymentConfirmationEmail(
                eq("user1@example.com"),
                eq(12345L),
                eq(500000.0),
                eq("VND")
        );
    }

    @Test
    @DisplayName("Process payment confirmation with null message does not throw error")
    void testProcessPaymentConfirmation_NullMessage() {
        notificationService.processPaymentConfirmation(null);

        verify(emailService, never()).sendPaymentConfirmationEmail(anyString(), anyLong(), anyDouble(), anyString());
    }
}
