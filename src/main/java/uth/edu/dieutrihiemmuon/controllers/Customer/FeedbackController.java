package uth.edu.dieutrihiemmuon.controllers.Customer;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.dieutrihiemmuon.dto.FeedbackDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.dto.TreatmentCycleDTO;
import uth.edu.dieutrihiemmuon.models.TreatmentCycle;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.IFeedbackService;
import uth.edu.dieutrihiemmuon.services.IServicePackageService;
import uth.edu.dieutrihiemmuon.services.TreatmentCycleService;


import java.time.LocalDate;
import java.util.List;

@Controller("customerFeedbackController")
public class FeedbackController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IServicePackageService servicePackageService;

    @Autowired
    private IFeedbackService feedbackService;
    @Autowired
    private TreatmentCycleService treatmentCycleService;

    @GetMapping("/feedback/{id}")
    public String feedback(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("FeedbackDTO")) {
            model.addAttribute("FeedbackDTO", new FeedbackDTO());
        }

        TreatmentCycleDTO treatmentCycle = treatmentCycleService.getTreatmentCycle(id);
        model.addAttribute("treatmentCycleDTO", treatmentCycle);
        ServicePackageDTO servicePackage = servicePackageService.getServicePackage(treatmentCycle.getServiceId());
        model.addAttribute("servicePackageDTO", servicePackage);

        return "customer/feedback";
    }

    @PostMapping("/feedback/{id}")
    public String handleFeedbackSubmit(@PathVariable("id") Long id,
                                       @Valid @ModelAttribute("FeedbackDTO") FeedbackDTO feedbackDTO,
                                       BindingResult result,
                                       Model model,
                                       Authentication authentication,
                                       RedirectAttributes redirectAttributes) {

        TreatmentCycleDTO treatmentCycle = treatmentCycleService.getTreatmentCycle(id);
        ServicePackageDTO servicePackage = servicePackageService.getServicePackage(treatmentCycle.getServiceId());

        if (result.hasErrors()) {
            // Đưa lại dữ liệu feedback và lỗi để hiển thị lại sau redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.FeedbackDTO", result);
            redirectAttributes.addFlashAttribute("FeedbackDTO", feedbackDTO);
            return "redirect:/feedback/" + id;
        }

        String username = authentication.getName();
        User user = customerService.findByUsername(username);

        boolean success = feedbackService.addFeedback(
                feedbackDTO.getTreatmentCycleId(),
                feedbackDTO.getReviewText(),
                feedbackDTO.getRating()
        );

        if (success) {
            redirectAttributes.addFlashAttribute("showPaymentModal", true);
        } else {
            redirectAttributes.addFlashAttribute("error", "Đánh giá thất bại.");
        }

        return "redirect:/feedback/" + id;
    }


}
