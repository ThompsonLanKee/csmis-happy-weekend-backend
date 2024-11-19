package com.spring.csmis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail,String user){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("saung1872000@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Register success!");
        message.setText("Dear"+ user+ "!\n" +
                "\n" +
                "You've earned a new badge on Cheatography! Badges are awarded for community contributions, for great cheat sheets and more!\n" +
                "\n" +
                "Your new Bronze badge is:\n" +
                "\n" +
                "Cheatographer, earned for: Cheat Sheet Published\n" +
                "\n" +
                "You can see your badges at:\n" +
                "\n" +
                "http://localhost:4200/user/login\n" +
                "\n" +
                "Create and share your own cheat sheets free at Cheatography.com!\n" +
                "\n" +
                "If you would prefer not to receive these notifications, please click here (you can turn them back on later in your profile at Cheatography)");

        mailSender.send(message);
        System.out.println("Mail Send Successfully....");
    }

    public boolean sendOTP(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);
        return true;
    }

}
