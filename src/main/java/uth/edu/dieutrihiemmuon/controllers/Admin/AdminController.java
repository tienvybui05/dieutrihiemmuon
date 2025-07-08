package uth.edu.dieutrihiemmuon.controllers.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uth.edu.dieutrihiemmuon.dto.*;
import uth.edu.dieutrihiemmuon.models.TreatmentCycle;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private ITreatmentCycleService treatmentCycleService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ITreatmentSessionService treatmentSessionService;

    @Autowired
    private IDoctorService docService;

    @Autowired
    private IServicePackageService servicePackageService;

    @Autowired
    private IFeedbackService feedbackService;

    @GetMapping("/admin")
    public String index(Authentication authentication, Model model) {
        String username = authentication.getName();
        UserDTO userDTO = userService.getUserByUserName(username);
        long countCustomer =customerService.countCustomers();
        long countDoctor =doctorService.countDoctors();
        long countservice = servicePackageService.countServicePackage();
        double centralRevenue = treatmentCycleService.revenue();
        long countSchedule = treatmentCycleService.numberOfSchedulesToDayALL();
        List<FeedbackInformationDTO> feedbackInformationDTOS = feedbackService.getTop4FeedbackInformation();
        model.addAttribute("feedbackInformationDTOS", feedbackInformationDTOS);
        model.addAttribute("countSchedule", countSchedule);
        model.addAttribute("revenue",centralRevenue);
        model.addAttribute("countservice", countservice);
        model.addAttribute("countDoctor",countDoctor);
        model.addAttribute("countCustomer",countCustomer);
        model.addAttribute("user", userDTO);
        return "admin/index";
    }


}
