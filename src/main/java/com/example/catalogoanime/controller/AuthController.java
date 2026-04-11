package com.example.catalogoanime.controller;

import com.example.catalogoanime.model.AppUser;
import com.example.catalogoanime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, 
                               @RequestParam String email,
                               @RequestParam String password, 
                               @RequestParam String confirmPassword, 
                               Model model) {
        
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Le password non coincidono.");
            return "register";
        }

        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username già in uso.");
            return "register";
        }

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email già in uso.");
            return "register";
        }

        AppUser newUser = new AppUser();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);

        return "redirect:/login?registered";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmNewPassword,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {
        
        AppUser user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("passwordError", "La password attuale è errata.");
            return "redirect:" + request.getHeader("Referer");
        }

        if (newPassword.equals(currentPassword)) {
            redirectAttributes.addFlashAttribute("passwordError", "La nuova password non può essere uguale a quella attuale.");
            return "redirect:" + request.getHeader("Referer");
        }
        
        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("passwordError", "Le nuove password non coincidono.");
            return "redirect:" + request.getHeader("Referer");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        SecurityContextHolder.clearContext();
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        
        return "redirect:/login?passwordChanged";
    }
}
