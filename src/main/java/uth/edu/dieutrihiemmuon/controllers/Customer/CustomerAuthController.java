package uth.edu.dieutrihiemmuon.controllers.Customer;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uth.edu.dieutrihiemmuon.dto.RegisterDTO;
import uth.edu.dieutrihiemmuon.services.ICustomerService;

@Controller
public class CustomerAuthController {
    @Autowired
    private ICustomerService customerService;

    //Login
    @GetMapping("/login")
    public String login(Authentication authentication) {
//        model.addAttribute("title", "Login");
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            return "redirect:/";
        }
        return "customer/auth/login";  // đúng đường dẫn tới file
    }

    // Register
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("RegisterDTO", new RegisterDTO());
//        model.addAttribute("title", "Register");
        return "customer/auth/register";  // đúng đường dẫn tới file
    }
    @PostMapping("/register")
    public String addAccount(@Valid @ModelAttribute("RegisterDTO") RegisterDTO registerDTO,
                             BindingResult result, Model model) {

        if (customerService.isUsernameExists(registerDTO.getUserName())) {
            result.rejectValue("userName", "error.customer", "Tên đăng nhập đã tồn tại");
        }
        // Kiểm tra trùng email
        if (customerService.isEmailExists(registerDTO.getEmail())) {
            result.rejectValue("email", "error.customer", "Email đã tồn tại");
        }
        // Kiểm tra trùng SĐT
        if (customerService.isPhoneNumberExists(registerDTO.getPhoneNumber())) {
            result.rejectValue("phoneNumber", "error.customer", "Số điện thoại đã tồn tại");
        }

        if (result.hasErrors()) {
            return "customer/auth/register";
        }
        customerService.addAccount(registerDTO);
        return "redirect:/";
    }

    // Error 403
    @GetMapping("/customer/auth/error403")
    public String customerError403() {
        return "customer/auth/error403";
    }

    @GetMapping("/customer/auth/changepassword")
    public String changepassword() {
        return "customer/auth/changepassword";
    }

}
