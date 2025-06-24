package uth.edu.dieutrihiemmuon.controllers.Admin;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @GetMapping("/admin/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/customer/index";
    }

    @GetMapping("/admin/customer/create")
    public String admincustomercreate() {
        return "admin/customer/create";
    }

    @GetMapping("/admin/customer/edit/{id}")
    public String adminCustomerEdit(@PathVariable("id") Long id, org.springframework.ui.Model model) {
        User customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "admin/customer/edit"; // trang form edit
    }

    @PostMapping("/admin/customer/edit/{id}")
    public String updateCustomer(@PathVariable("id") Long id, @ModelAttribute("customer") User updatedCustomer) {
        User existingCustomer = customerService.getCustomerById(id);

        if (existingCustomer != null) {
            // Gán lại role cũ
            updatedCustomer.setRole(existingCustomer.getRole());

            // Cập nhật thông tin
            customerService.updateCustomer(id, updatedCustomer);
        }

        return "redirect:/admin/customer/index";
    }




}
