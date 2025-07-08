package uth.edu.dieutrihiemmuon.controllers.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;

@Controller
public class ProfileController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/profile")
public String profile(Model model, Authentication authentication) {
    // Lấy username của người đăng nhập
    String username = authentication.getName();

    // Gọi method có sẵn trong CustomerService
    User user = customerService.findByUsername(username);

    // Nếu không tìm thấy user
    if (user == null) {
        model.addAttribute("error", "Không tìm thấy người dùng");
        return "error"; // Trả về view error.html
    }

    // Truyền user vào Thymeleaf
    model.addAttribute("user", user);

    return "customer/profile"; // Trả về view
}

}
