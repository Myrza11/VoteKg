package org.example.votekg.service;

import org.example.votekg.config.JwtUtil;
import org.example.votekg.dto.OfficialUserRegisterDTO;
import org.example.votekg.dto.VoterUserRegisterDTO;
import org.example.votekg.model.OfficialUser;
import org.example.votekg.model.User;
import org.example.votekg.model.VerificationToken;
import org.example.votekg.model.VoterUser;
import org.example.votekg.repository.UserRepository;
import org.example.votekg.repository.VerificationTokenRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, VerificationTokenRepository tokenRepository, EmailService emailService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
    }

    public void registerVoterUser(VoterUserRegisterDTO voterUserRegisterDTO) {
        VoterUser voterUser = new VoterUser();
        voterUser.setPassword(new BCryptPasswordEncoder().encode(voterUserRegisterDTO.getPassword()));
        voterUser.setEmailVerified(false);
        voterUser.setName(voterUserRegisterDTO.getName());
        voterUser.setSurname(voterUserRegisterDTO.getSurname());
        voterUser.setEmail(voterUserRegisterDTO.getEmail());
        voterUser.setDistrict(voterUserRegisterDTO.getDistrict());
        voterUser.setDateOfBirth(voterUserRegisterDTO.getDateOfBirth());
        try {
            voterUser.setUserImage(voterUserRegisterDTO.getUserImage().getBytes());
            voterUser.setUserPassportPhoto(voterUserRegisterDTO.getUserPassportPhoto().getBytes());
            voterUser.setUserUpsidePassportPhoto(voterUserRegisterDTO.getUserUpsidePassportPhoto().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файлов", e);
        }
        voterUser.setIdPassword(voterUserRegisterDTO.getIdPassport());


        userRepository.save(voterUser);

        VerificationToken token = new VerificationToken(voterUser);
        tokenRepository.save(token);

        emailService.sendVerificationEmail(voterUser.getEmail(), token.getToken());
    }

    public void registerOrganisation(OfficialUserRegisterDTO dto) {
        OfficialUser officialUser = new OfficialUser();
        officialUser.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        officialUser.setEmailVerified(false);
        officialUser.setName(dto.getName());
        officialUser.setSurname(dto.getSurname());
        officialUser.setPosition(dto.getPosition());
        officialUser.setEmail(dto.getEmail());
        officialUser.setDistrict(dto.getDistrict());
        officialUser.setOrganization(dto.getOrganization());
        try {
            officialUser.setUserImage(dto.getUserImage().getBytes());
            officialUser.setUserPassportPhoto(dto.getUserPassportPhoto().getBytes());
            officialUser.setUserUpsidePassportPhoto(dto.getUserUpsidePassportPhoto().getBytes());
            officialUser.setCertificate(dto.getCertificate().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файлов", e);
        }
        officialUser.setDateOfBirth(dto.getDateOfBirth());
        officialUser.setIdPassword(dto.getIdPassword());

        userRepository.save(officialUser);

        VerificationToken token = new VerificationToken(officialUser);
        tokenRepository.save(token);

        emailService.sendVerificationEmail(dto.getEmail(), token.getToken());
    }

    public String authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new RuntimeException("user not found");
        }
        if (!user.get().isEmailVerified()){
            throw new RuntimeException("user is not verified");
        }
        if (!(user.get() instanceof OfficialUser && !((OfficialUser) user.get()).isVerified())) {
            throw new RuntimeException("user is not verified");
        }
        if (!new BCryptPasswordEncoder().matches(password, user.get().getPassword())) {
            throw new RuntimeException("password not match");
        }
        return jwtUtil.generateToken(user.get());
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }
}
