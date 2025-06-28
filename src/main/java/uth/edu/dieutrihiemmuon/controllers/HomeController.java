package uth.edu.dieutrihiemmuon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import uth.edu.dieutrihiemmuon.dto.RegisterDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.services.CustomerService;
import uth.edu.dieutrihiemmuon.services.ServicePackageService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ServicePackageService servicePackageService;

//    @GetMapping("/")
//    public String index() {
//        return "customer/index";  // đúng đường dẫn tới file
//    }
    @GetMapping("/")
    public String adminservicepackageindex( Model model) {

        List<ServicePackageDTO> servicePackageDTOS = servicePackageService.getServicePackages();
        model.addAttribute("ServicePackageDTOs", servicePackageDTOS);
        return "customer/index";
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
    @GetMapping("/appointment/{id}")
    public String appointment(@PathVariable("id") Long id, Model model) {
        ServicePackageDTO servicePackage = servicePackageService.getServicePackage(id);
        model.addAttribute("servicePackageDTO", servicePackage);
        return "customer/appointment";
    }


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

    //Services
    @GetMapping("/services")
    public String services() {
        return "customer/services/index";  // đúng đường dẫn tới file
    }

    //Đăng nhập và đăng ký
    @GetMapping("/login")
    public String login(Model model) {
//        model.addAttribute("title", "Login");
        return "customer/auth/login";  // đúng đường dẫn tới file
    }

    @GetMapping("/register")
    public String register(Model model) {
          model.addAttribute("RegisterDTO", new RegisterDTO());
//        model.addAttribute("title", "Register");
        return "customer/auth/register";  // đúng đường dẫn tới file
    }
    @PostMapping("/register")
    public String addAccount(@Valid @ModelAttribute("RegisterDTO") RegisterDTO registerDTO,
                             BindingResult result,Model model) {

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
        return "redirect:/customer/index";
    }

    //
}
