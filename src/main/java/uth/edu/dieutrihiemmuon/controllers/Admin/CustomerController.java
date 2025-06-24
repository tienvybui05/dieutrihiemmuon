package uth.edu.dieutrihiemmuon.controllers.Admin;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/admin/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/customer/index";
    }

    @GetMapping("/admin/customer/create")
    public String admincustomercreate() {
        return "admin/customer/create";
    }
    @GetMapping("/admin/customer/edit")
    public String admincustomeredit() {
        return "admin/customer/edit";
    }

}
