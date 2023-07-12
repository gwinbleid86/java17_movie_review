package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.UserDao;
import kg.attractor.movie_review.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void someMethod(int userId) {
        Optional<User> mayByUser = userDao.getOptionalUserById(userId);
        mayByUser.ifPresent(e -> System.out.printf("%s, %s, %s%n", e.getId(), e.getName(), e.getPasswd()));
    }

    public void createUser(User user) {
        userDao.createUser(user);
    }
}
