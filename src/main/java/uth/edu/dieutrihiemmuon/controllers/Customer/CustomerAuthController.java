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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.dieutrihiemmuon.dto.RegisterDTO;
import uth.edu.dieutrihiemmuon.dto.UserDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.UserService;

import java.util.List;

@Controller
public class CustomerAuthController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private UserService userService;

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
    public String showChangePasswordForm(Model model) {
        return "customer/auth/changepassword";
    }

    @PostMapping("/customer/auth/changepassword")
    public String changepassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        UserDTO userDTO = userService.getUserByUserName(username);

        // Kiểm tra mật khẩu mới và mật khẩu xác nhận có trùng không
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới và xác nhận không trùng khớp.");
            return "redirect:/customer/auth/changepassword";
        }

        // Kiểm tra mật khẩu cũ
        boolean isOldPasswordCorrect = userService.checkPassword(userDTO.getUserName(), oldPassword);
        if (!isOldPasswordCorrect) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu cũ không đúng.");
            return "redirect:/customer/auth/changepassword";
        }

        // Cập nhật mật khẩu mới
        userService.updatePassword(userDTO.getUserName(), newPassword);
        redirectAttributes.addFlashAttribute("success", "Đổi mật khẩu thành công.");
        return "redirect:/";  // Redirect đến trang thành công
    }

}
