package com.vietphan.notification_service.listener;

import com.vietphan.notification_service.DTO.message.PaymentMessage;
import com.vietphan.notification_service.service.NotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentCompletedListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private PaymentCompletedListener paymentCompletedListener;

    @Test
    @DisplayName("Listener receives message and delegates to NotificationService")
    void testOnPaymentCompleted() {
        PaymentMessage message = PaymentMessage.builder()
                .paymentId(12345L)
                .accountId(1L)
                .amount(500000.0)
                .currency("VND")
                .status("SUCCESS")
                .timestamp(Instant.now())
                .build();

        doNothing().when(notificationService).processPaymentConfirmation(message);

        paymentCompletedListener.onPaymentCompleted(message);

        verify(notificationService, times(1)).processPaymentConfirmation(message);
    }
}
