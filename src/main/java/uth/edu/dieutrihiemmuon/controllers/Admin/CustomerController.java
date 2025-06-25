package uth.edu.dieutrihiemmuon.controllers.Admin;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.CustomerService;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @ModelAttribute("customers")
    public List<User> getCustomers(){
        return customerService.getAllCustomers();
    }

    //admin/customer
    @GetMapping("/admin/customer/index")
    public String admincustomerindex() {
        return "admin/customer/index";
    }

    // xóa cus theo id
    @GetMapping("/admin/customer/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/customer/index";
    }

    // them tai khoan cus
    @GetMapping("/admin/customer/create")
    public String adminCustomerCreateForm(Model model) {
        model.addAttribute("customer", new User());
        return "admin/customer/create";
    }



    @PostMapping("/admin/customer/create")
    public String adminCustomerAdd(
            @ModelAttribute("customer") @Valid User customer,
            BindingResult result,
            Model model) {

        // Kiểm tra trùng username
        if (customerService.isUsernameExists(customer.getUserName())) {
            result.rejectValue("userName", "error.customer", "Tên đăng nhập đã tồn tại");
        }
        // Kiểm tra trùng email
        if (customerService.isEmailExists(customer.getEmail())) {
            result.rejectValue("email", "error.customer", "Email đã tồn tại");
        }
        // Kiểm tra trùng SĐT
        if (customerService.isPhoneNumberExists(customer.getPhoneNumber())) {
            result.rejectValue("phoneNumber", "error.customer", "Số điện thoại đã tồn tại");
        }

        // Nếu có lỗi thì quay lại form
        if (result.hasErrors()) {
            return "admin/customer/create";
        }

        // Không có lỗi => Lưu
        customer.setRole("CUSTOMER");
        customerService.addCustomer(customer);
        return "redirect:/admin/customer/index";
    }



    // Chinh sua cus theo id
    @GetMapping("/admin/customer/edit/{id}")
    public String adminCustomerEdit(@PathVariable("id") Long id, org.springframework.ui.Model model) {
        User customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "admin/customer/edit"; // trang form edit
    }

    @PostMapping("/admin/customer/edit/{id}")
    public String updateCustomer(
            @PathVariable("id") Long id,
            @ModelAttribute("customer") @Valid User updatedCustomer,
            BindingResult result,
            Model model) {

        User existingCustomer = customerService.getCustomerById(id);

        // Nếu không tồn tại thì trả về
        if (existingCustomer == null) {
            model.addAttribute("error", "Không tìm thấy người dùng");
            return "admin/customer/edit";
        }

        // Kiểm tra trùng username (loại trừ bản ghi hiện tại)
        if (!updatedCustomer.getUserName().equals(existingCustomer.getUserName()) &&
                customerService.isUsernameExists(updatedCustomer.getUserName())) {
            result.rejectValue("userName", "error.customer", "Tên đăng nhập đã tồn tại");
        }

        // Kiểm tra trùng email
        if (!updatedCustomer.getEmail().equals(existingCustomer.getEmail()) &&
                customerService.isEmailExists(updatedCustomer.getEmail())) {
            result.rejectValue("email", "error.customer", "Email đã tồn tại");
        }

        // Kiểm tra trùng SĐT
        if (!updatedCustomer.getPhoneNumber().equals(existingCustomer.getPhoneNumber()) &&
                customerService.isPhoneNumberExists(updatedCustomer.getPhoneNumber())) {
            result.rejectValue("phoneNumber", "error.customer", "Số điện thoại đã tồn tại");
        }

        // Nếu có lỗi thì quay lại form
        if (result.hasErrors()) {
            return "admin/customer/edit";
        }

        // Gán lại role và id cũ
        updatedCustomer.setRole(existingCustomer.getRole());
        updatedCustomer.setIdUser(existingCustomer.getIdUser());

        // Cập nhật thông tin
        customerService.updateCustomer(id, updatedCustomer);

        return "redirect:/admin/customer/index";
    }


    //admin/customer
    @GetMapping("/admin/customer/detail/{id}")
    public String admincustomerdetail(@PathVariable("id") Long id, org.springframework.ui.Model model) {
        User customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "admin/customer/detail";
    }


}
