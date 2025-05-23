package org.example.votekg.service;

import org.example.votekg.config.JwtUtil;
import org.example.votekg.model.User;
import org.example.votekg.model.VerificationToken;
import org.example.votekg.repository.UserRepository;
import org.example.votekg.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public void sendVerificationEmail(String to, String token){
        String subject = "Подтвердите свою почту";
        String confirmationUrl = "https://votekg-1.onrender.com/verify?token=" + token;

        String message = "Пожалуйста, подтвердите ваш email, перейдя по ссылке:\n" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
        System.out.println("your confirmation url is: " + confirmationUrl);
        System.out.println("Mail sent to " + to);
    }

    public String verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Недействительный токен"));

        if (verificationToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Срок действия токена истёк");
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);
        return jwtUtil.generateToken(user);
    }
    public boolean isEmailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return false;
        }
        return true;
    }
}
