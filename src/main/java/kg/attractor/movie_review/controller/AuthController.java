package kg.attractor.movie_review.controller;

import jakarta.validation.Valid;
import kg.attractor.movie_review.dto.UserDto;
import kg.attractor.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String register() {
        return "/auth/register";
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    public String register(@Valid @ModelAttribute UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

}
