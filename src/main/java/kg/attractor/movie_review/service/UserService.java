package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.UserDao;
import kg.attractor.movie_review.dto.UserDto;
import kg.attractor.movie_review.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder encoder;

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void createUser(UserDto userDto) {
        userDao.save(User.builder()
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .enabled(Boolean.TRUE)
                .build());
    }
}
