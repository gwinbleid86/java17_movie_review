package kg.attractor.movie_review.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.movie_review.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/auth")
public interface AuthController {

    @GetMapping("/register")
    String register(Model model);

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    String register(@Valid UserDto userDto, BindingResult bindingResult, Model model);

    @GetMapping("/login")
    String login(Boolean error, Model model);

    @GetMapping("/forgot_password")
    String forgotPassword();

    @PostMapping("/forgot_password")
    String forgotPassword(HttpServletRequest request, Model model);

    @GetMapping("reset_password")
    String resetPasswordForm(String token, Model model);

    @PostMapping("reset_password")
    String resetPassword(HttpServletRequest request, Model model);
}
