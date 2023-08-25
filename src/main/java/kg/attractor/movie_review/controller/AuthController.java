package kg.attractor.movie_review.controller;

import jakarta.validation.Valid;
import kg.attractor.movie_review.dto.UserDto;
import kg.attractor.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "/auth/register";
    }

    @PostMapping("/register")
//    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    public String register(@Valid UserDto userDto, Model model, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.createUser(userDto);
            return "redirect:/";
        }
        model.addAttribute("userDto", userDto);
        return "/auth/register";
    }

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

}
