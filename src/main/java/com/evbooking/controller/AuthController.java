package com.evbooking.controller;

import com.evbooking.model.User;
import com.evbooking.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    public AuthController(UserRepository users, PasswordEncoder encoder) { this.users=users; this.encoder=encoder; }

    @GetMapping("/login") public String login() { return "login"; }
    @PostMapping("/login") public String doLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = users.findByEmailIgnoreCase(email.trim()).orElse(null);
        if (user == null || !encoder.matches(password, user.getPasswordHash())) {
            model.addAttribute("error", "Invalid email or password. Please try again."); return "login";
        }
        session.setAttribute("userEmail", user.getEmail()); session.setAttribute("userName", user.getFullName());
        return "redirect:/dashboard";
    }
    @GetMapping("/register") public String register() { return "register"; }
    @PostMapping("/register") public String doRegister(@RequestParam String fullName, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword, Model model, HttpSession session) {
        String cleanEmail=email.trim().toLowerCase();
        if (fullName.trim().isEmpty() || cleanEmail.isEmpty()) { model.addAttribute("error", "Please fill in all required fields."); return "register"; }
        if (password.length() < 6) { model.addAttribute("error", "Password must contain at least 6 characters."); return "register"; }
        if (!password.equals(confirmPassword)) { model.addAttribute("error", "Passwords do not match."); return "register"; }
        if (users.existsByEmailIgnoreCase(cleanEmail)) { model.addAttribute("error", "An account with this email already exists. Please log in."); return "register"; }
        User user = new User(fullName.trim(), cleanEmail, encoder.encode(password)); users.save(user);
        session.setAttribute("userEmail", user.getEmail()); session.setAttribute("userName", user.getFullName());
        return "redirect:/dashboard";
    }
    @GetMapping("/logout") public String logout(HttpSession session) { session.invalidate(); return "redirect:/login?logout"; }
}
