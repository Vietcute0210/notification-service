package com.vietphan.notification_service.service;

import com.vietphan.notification_service.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    @DisplayName("Send payment confirmation email successfully")
    void testSendPaymentConfirmationEmail_Success() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.sendPaymentConfirmationEmail("test@example.com", 12345L, 500000.0, "VND");

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Send payment confirmation email handles mail exception gracefully")
    void testSendPaymentConfirmationEmail_Exception() {
        doThrow(new MailSendException("SMTP error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Should not throw exception
        emailService.sendPaymentConfirmationEmail("test@example.com", 12345L, 500000.0, "VND");

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Send OTP email successfully")
    void testSendOtpEmail_Success() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.sendOtpEmail("test@example.com", "123456");

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
