package uth.edu.dieutrihiemmuon.controllers.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    String image = user.getImage();
    // Truyền user vào Thymeleaf
    model.addAttribute("user", user);
    model.addAttribute("image", image);
    return "customer/profile"; // Trả về view
}
@PostMapping("/profile")
public String updateProfile(@ModelAttribute("user") User updatedUser, Authentication authentication) {
    String username = authentication.getName();
    User existingUser = customerService.findByUsername(username);

    if (existingUser != null) {
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setGender(updatedUser.getGender());
        customerService.updateCustomer(existingUser.getIdUser(), existingUser);
    }
    return "redirect:/profile"; // Quay lại profile sau khi lưu
}


}
