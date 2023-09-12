package kg.attractor.movie_review.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.movie_review.dto.UserDto;
import kg.attractor.movie_review.model.User;
import kg.attractor.movie_review.repository.UserRepository;
import kg.attractor.movie_review.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    private final EmailService emailService;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void createUser(UserDto userDto) {
        repository.save(User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(encoder.encode(userDto.getPassword()))
                .enabled(Boolean.TRUE)
                .build());

    }

    private void updateResetPasswordToken(String token, String email) {
        User user = repository.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setResetPasswordToken(token);
        repository.saveAndFlush(user);
    }

    public UserDto getByResetPasswdToken(String token) {
        User u = repository.findByResetPasswordToken(token).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDto.builder()
                .email(u.getEmail())
                .password(u.getPassword())
                .username(u.getUsername())
                .resetPasswordToken(u.getResetPasswordToken())
                .build();
    }

    public void updatePassword(UserDto userDto, String newPasswd) {
        User u = repository.getByEmail(userDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        u.setResetPasswordToken(null);
        u.setPassword(encoder.encode(newPasswd));
        repository.saveAndFlush(u);
    }

    public void makeResetPasswdLink(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(token, email);
        String resetPasswordLink = Utility.getSiteUrl(request) + "/auth/reset_password?token=" + token;
        emailService.sendEmail(email, resetPasswordLink);
    }
}
