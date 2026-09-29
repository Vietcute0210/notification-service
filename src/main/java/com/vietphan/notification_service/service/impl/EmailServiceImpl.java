package com.vietphan.notification_service.service.impl;

import com.vietphan.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:your-email@gmail.com}")
    private String fromEmail;

    @Override
    public void sendPaymentConfirmationEmail(String toEmail, Long paymentId, double amount, String currency) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Xác nhận thanh toán thành công - Bank Service");
            message.setText("Xin chào,\n\n"
                    + "Giao dịch thanh toán của bạn đã được xác nhận thành công!\n"
                    + "- Mã thanh toán (Payment ID): " + paymentId + "\n"
                    + "- Số tiền: " + amount + " " + currency + "\n\n"
                    + "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.\n\n"
                    + "Trân trọng,\nBank Service Team");

            mailSender.send(message);
            log.info("Payment confirmation email sent successfully to {} for paymentId {}", toEmail, paymentId);
        } catch (Exception e) {
            log.warn("Could not send payment confirmation email to {}: {}. (SMTP may not be configured)", toEmail, e.getMessage());
        }
    }

    @Override
    public void sendOtpEmail(String toEmail, String otpCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Mã OTP xác thực - Bank Service");
            message.setText("Xin chào,\n\n"
                    + "Mã OTP xác nhận của bạn là: " + otpCode + "\n\n"
                    + "Mã có hiệu lực trong 5 phút. Vui lòng tuyệt đối không chia sẻ mã này cho bất kỳ ai.\n\n"
                    + "Trân trọng,\nBank Service Team");

            mailSender.send(message);
            log.info("OTP email sent successfully to {}", toEmail);
        } catch (Exception e) {
            log.warn("Could not send OTP email to {}: {}. (SMTP may not be configured)", toEmail, e.getMessage());
        }
    }
}
