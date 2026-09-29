package com.vietphan.notification_service.listener;

import com.vietphan.notification_service.DTO.message.PaymentMessage;
import com.vietphan.notification_service.constant.QueueConstants;
import com.vietphan.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCompletedListener {

    private final NotificationService notificationService;

    @JmsListener(destination = "${activemq.queue.payment-completed:" + QueueConstants.PAYMENT_COMPLETED_QUEUE + "}")
    public void onPaymentCompleted(PaymentMessage message) {
        log.info("Received payment completed message from ActiveMQ: paymentId={}", message.getPaymentId());
        notificationService.processPaymentConfirmation(message);
    }
}
