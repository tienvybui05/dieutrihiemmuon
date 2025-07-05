package uth.edu.dieutrihiemmuon.controllers.Customer;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.IServicePackageService;

@Controller
public class HomeCustomerController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IServicePackageService servicePackageService;

    // Home Page
    @GetMapping(value = {"/", "/index"})
    public String adminservicepackageindex( Model model) {
        List<ServicePackageDTO> servicePackageDTOS = servicePackageService.getServicePackages();
        model.addAttribute("ServicePackageDTOs", servicePackageDTOS);
        return "customer/index";
    }

    //About
    @GetMapping("/about")
    public String about() {
        return "customer/about";
    }

    //Blog
    @GetMapping("/blog")
    public String blog(){ return "customer/blog";}

    // Contact
    @GetMapping("/contact")
    public String contact(){ return "customer/contact";}

    //Services
    @GetMapping("/services")
    public String services(Model model) {
        List<ServicePackageDTO> servicePackageDTOS = servicePackageService.getServicePackages();
        model.addAttribute("ServicePackageDTOs", servicePackageDTOS);
        return "customer/services/index";  // đúng đường dẫn tới file
    }
}
