package com.pnf.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 비동기 처리가 필요한 메소드를 위한 클래스
 */
@Service("asyncService")
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendEmail(String email, String token) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("재시에서 보낸 인증번호");
        msg.setText("인증 번호 : " + token);
        javaMailSender.send(msg);
    }
}
