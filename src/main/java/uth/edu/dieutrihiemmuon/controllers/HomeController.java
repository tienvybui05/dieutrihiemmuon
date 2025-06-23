package uth.edu.dieutrihiemmuon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "customer/index";  // đúng đường dẫn tới file
    }
    @GetMapping("/about")
    public String about() {
        return "customer/about";
    }
    @GetMapping("/blog")
    public String blog(){ return "customer/blog";}
    @GetMapping("/contact")
    public String contact(){ return "customer/contact";}
    @GetMapping("/appointment")
    public String appointment() { return "customer/appointment";}
    @GetMapping("/history")
    public String history() { return "customer/history";}
    @GetMapping("/payment")
    public String payment() { return "customer/payment";}
    @GetMapping("/treatmentcyclecustomer")
    public String treatmentcyclecustomer() { return "customer/treatmentcycle";}
    @GetMapping("/treatmentschedulecustomer")
    public String treatmentschedulecustomer() { return "customer/treatmentschedule";}
    @GetMapping("/workscheduledoctor")
    public String workscheduledoctor() { return "customer/doctor/workschedule";}
    @GetMapping("/treatmentcycledoctor")
    public String treatmentcycledoctor() { return "customer/doctor/treatmentcycle";}
    @GetMapping("/profile")
    public String profile() { return "customer/profile";}
}
