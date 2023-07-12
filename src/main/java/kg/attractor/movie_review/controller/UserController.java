package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.model.User;
import kg.attractor.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> printUser(@PathVariable int id) {
        userService.someMethod(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        log.error("Username: {}", user.getName());
        userService.createUser(user);
    }
}
