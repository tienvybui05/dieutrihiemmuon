package uth.edu.dieutrihiemmuon.controllers.Customer;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("FeedbackDTO", new FeedbackDTO());

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
                                       Authentication authentication) {

        TreatmentCycleDTO treatmentCycle = treatmentCycleService.getTreatmentCycle(id);
        model.addAttribute("treatmentCycleDTO", treatmentCycle);

        ServicePackageDTO servicePackage = servicePackageService.getServicePackage(id);
        model.addAttribute("servicePackageDTO", servicePackage);


        if (result.hasErrors()) {
            // Có lỗi trong form, quay lại trang đánh giá với thông báo lỗi
            return "customer/feedback";
        }

        // Lấy thông tin user
        String username = authentication.getName();
        User user = customerService.findByUsername(username);

        // Gửi đánh giá
        boolean success = feedbackService.addFeedback(
                feedbackDTO.getServiceId(),
                user.getIdUser(),
                feedbackDTO.getTreatmentCycleId(),
                feedbackDTO.getReviewText(),
                feedbackDTO.getRating()
        );

        if (success) {
            model.addAttribute("showPaymentModal", true); // nếu cần hiển thị modal
        } else {
            model.addAttribute("error", "Đánh giá thất bại.");
        }

        return "customer/feedback";
    }


}
