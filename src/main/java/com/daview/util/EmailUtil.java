package com.daview.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	@Autowired
    private JavaMailSender mailSender;

    // 메일 보내는 메서드
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // 받는 사람
        message.setSubject(subject); // 메일 제목
        message.setText(text); // 메일 내용
        mailSender.send(message); // 전송
    }
}
