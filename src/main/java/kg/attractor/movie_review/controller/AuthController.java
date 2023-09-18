package kg.attractor.movie_review.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.movie_review.dto.UserDto;
import kg.attractor.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

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
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    public String register(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            userService.createUser(userDto);
            return "redirect:/";
        }
        model.addAttribute("userDto", userDto);
        return "/auth/register";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(defaultValue = "false", required = false) Boolean error,
            Model model
    ) {
        if (error.equals(Boolean.TRUE)) {
            model.addAttribute("error", "Invalid Username or Password");
        }
        return "/auth/login";
    }

    @GetMapping("/forgot_password")
    public String forgotPassword() {
        return "auth/forgot_password";
    }

    @PostMapping("/forgot_password")
    public String forgotPassword(HttpServletRequest request, Model model) {
        try {
            userService.makeResetPasswdLink(request);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UsernameNotFoundException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "auth/forgot_password";
    }

    @GetMapping("reset_password")
    public String resetPasswordForm(@RequestParam String token, Model model) {
        try {
            userService.getByResetPasswdToken(token);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Invalid token");
        }
        return "auth/reset_password_form";
    }

    @PostMapping("reset_password")
    public String resetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        try {
            var user = userService.getByResetPasswdToken(token);
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        } catch (UsernameNotFoundException e) {
            model.addAttribute("message", "Invalid token.");
        }
        return "auth/message";
    }

//    public void someMethod(HttpSession session) {
//        session.setAttribute("list", List.of("one", "two", "three"));
//
//        var list = (List<String>) session.getAttribute("list");
//    }
//
//    @GetMapping("/show")
//    public String showNames(@SessionAttribute Optional<String> name) {
//        // TODO что-то делаем с коллекцией
//        return "sample";
//    }
}
