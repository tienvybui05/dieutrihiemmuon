package uth.edu.dieutrihiemmuon.controllers.Customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uth.edu.dieutrihiemmuon.services.ICustomerService;

@Controller
public class BillingController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/history")
    public String history() { return "customer/history";}
    @GetMapping("/payment")
    public String payment() { return "customer/payment";}

}
