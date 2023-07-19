package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.UserDao;
import kg.attractor.movie_review.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void createUser(User user) {
        userDao.save(user);
    }
}
