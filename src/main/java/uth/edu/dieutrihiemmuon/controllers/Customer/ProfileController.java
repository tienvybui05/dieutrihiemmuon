package uth.edu.dieutrihiemmuon.controllers.Customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uth.edu.dieutrihiemmuon.services.ICustomerService;

@Controller
public class ProfileController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/profile")
    public String profile() { return "customer/profile";}

}
