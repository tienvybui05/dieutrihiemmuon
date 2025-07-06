package uth.edu.dieutrihiemmuon.controllers.Admin;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uth.edu.dieutrihiemmuon.dto.FeedbackDTO;
import uth.edu.dieutrihiemmuon.dto.FeedbackInformationDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.FeedbackService;
import java.util.List;
import uth.edu.dieutrihiemmuon.services.ServicePackageService;
import org.springframework.security.core.Authentication;


@Controller("adminFeedbackController")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final ServicePackageService servicePackageService;

    public FeedbackController(FeedbackService feedbackService, ServicePackageService servicePackageService) {
        this.feedbackService = feedbackService;
        this.servicePackageService = servicePackageService;
    }

    @GetMapping("/admin/feedback/index")
    public String adminfeedbackindex( Model model ) {
         List<FeedbackInformationDTO> feedbackInformationDTOS = feedbackService.getFeedbackInformationList();
         model.addAttribute("feedbackInformationDTOs", feedbackInformationDTOS);

        return "admin/feedback/index";
    }

    @GetMapping("/admin/feedback/detail/{id}")
    public String adminfeedbackdetail(@PathVariable long id, Model model) {
        FeedbackDTO feedbackDTO = feedbackService.getFeedback(id);
        model.addAttribute("FeebackDTO", feedbackDTO);
        return "admin/feedback/detail";
    }
    @GetMapping("/admin/feedback/delete/{id}")
    public String adminfeedbackdelete(@PathVariable long id, Model model) {
        feedbackService.deleteFeedback(id);
        return "redirect:/admin/feedback/index";
    }

}
