package uth.edu.dieutrihiemmuon.controllers.Admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminAuthController {

    @GetMapping("/admin/auth/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            return "redirect:/admin";
        }
        return "admin/auth/login";
    }
    @GetMapping("/admin/auth/logout")
    public String logout() {
        return "/admin/auth/logout";
    }
    @GetMapping("/admin/auth/error403")
    public String error403() {
        return "admin/auth/error403";
    }
}
