package kg.attractor.movie_review.controller.impl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.movie_review.controller.AuthController;
import kg.attractor.movie_review.dto.UserDto;
import kg.attractor.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final UserService userService;

    @Override
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "/auth/register";
    }

    @Override
    public String register(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            userService.createUser(userDto);
            return "redirect:/";
        }
        model.addAttribute("userDto", userDto);
        return "/auth/register";
    }

    @Override
    public String login(
            @RequestParam(defaultValue = "false", required = false) Boolean error,
            Model model
    ) {
        if (error.equals(Boolean.TRUE)) {
            model.addAttribute("error", "Invalid Username or Password");
        }
        return "/auth/login";
    }

    @Override
    public String forgotPassword() {
        return "auth/forgot_password";
    }

    @Override
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

    @Override
    public String resetPasswordForm(@RequestParam String token, Model model) {
        try {
            userService.getByResetPasswdToken(token);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Invalid token");
        }
        return "auth/reset_password_form";
    }

    @Override
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

}
