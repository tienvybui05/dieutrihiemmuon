package uth.edu.dieutrihiemmuon.controllers.Customer;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import uth.edu.dieutrihiemmuon.dto.FeedbackInformationDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.services.FeedbackService;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.IServicePackageService;

@Controller
public class HomeCustomerController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IServicePackageService servicePackageService;
    @Autowired
    private FeedbackService feedbackService;

    // Home Page
    @GetMapping(value = {"/", "/index"})
    public String adminservicepackageindex( Model model) {
        List<ServicePackageDTO> servicePackageDTOS = servicePackageService.getServicePackages();
        model.addAttribute("ServicePackageDTOs", servicePackageDTOS);
        model.addAttribute("activePage", "index");
        List<FeedbackInformationDTO> feedbackInformationDTOS = feedbackService.getFeedbackInformationList();
        model.addAttribute("FeedbackInformationDTOs", feedbackInformationDTOS);
        return "customer/index";
    }

    //About
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("activePage", "about");
        return "customer/about";
    }

    //Blog
    @GetMapping("/blog")
    public String blog(Model model){
        model.addAttribute("activePage", "blog");
        return "customer/blog";
    }

    @GetMapping("/blog-detail-1")
    public String blog1(Model model){
        model.addAttribute("activePage", "blog-detail-1");
        return "customer/blog-detail-1";
    }

    @GetMapping("/blog-detail-2")
    public String blogdetail2(Model model){
        model.addAttribute("activePage", "blog-detail-2");
        return "customer/blog-detail-2";
    }

    @GetMapping("/blog-detail-3")
    public String blogdetail3(Model model){
        model.addAttribute("activePage", "blog-detail-3");
        return "customer/blog-detail-3";
    }

    // Contact
    @GetMapping("/contact")
    public String contact(Model model){
        model.addAttribute("activePage", "contact");
        return "customer/contact";
    }

    //Services
    @GetMapping("/services")
    public String services(Model model) {
        List<ServicePackageDTO> servicePackageDTOS = servicePackageService.getServicePackages();
        model.addAttribute("ServicePackageDTOs", servicePackageDTOS);
        model.addAttribute("activePage", "services");
        List<FeedbackInformationDTO> feedbackInformationDTOS = feedbackService.getFeedbackInformationList();
        model.addAttribute("FeedbackInformationDTOs", feedbackInformationDTOS);
        return "customer/services/index";  // đúng đường dẫn tới file
    }


    // view doctors
    @GetMapping("/viewdoctors")
    public String viewdoctors(Model model) {
        model.addAttribute("activePage", "viewdoctors");
        return "customer/viewdoctors";
    }
}
