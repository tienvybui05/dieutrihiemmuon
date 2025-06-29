package uth.edu.dieutrihiemmuon.controllers.Admin;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uth.edu.dieutrihiemmuon.dto.FeedbackDTO;
import uth.edu.dieutrihiemmuon.services.FeedbackService;
import java.util.List;

@Controller
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/admin/feedback/index")
    public String adminfeedbackindex( Model model) {

        List<FeedbackDTO> feedbackDTOS = feedbackService.getFeedbacks();
        model.addAttribute("FeedbackDTOs", feedbackDTOS);
        return "admin/feedback/index";
    }

    @GetMapping("/admin/feedback/create")
    public String adminfeedbackcreate( Model model)
    {
        model.addAttribute("FeedbackDTO",new FeedbackDTO());
        return "admin/feedback/create";
    }

    @PostMapping("/admin/feedback/create")
    public String addFeedback(@Valid @ModelAttribute("FeedbackDTO") FeedbackDTO feedbackDTO , BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("FeedbackDTO", feedbackDTO);
            return "admin/feedback/create";
        }
        if(feedbackService.addFeedback(feedbackDTO)==true)
        {
            model.addAttribute("message","Thêm thành công!");
        }
        else{
            model.addAttribute("message","Thêm không thành công!");
        }
        return "redirect:/admin/feedback/index";

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

    @GetMapping("/admin/feedback/edit/{id}")
    public String adminfeedbackedit(@PathVariable long id, Model model) {
        FeedbackDTO feedbackDTO = feedbackService.getFeedback(id);
        model.addAttribute("FeedbackDTO", feedbackDTO);
        return "admin/feedback/edit";
    }
    @PostMapping("/admin/feedback/edit/{id}")
    public String editFeedback( @PathVariable long id,@Valid @ModelAttribute("FeedbackDTO") FeedbackDTO feedbackDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("FeedbackDTO", feedbackDTO);
            return "/admin/feedback/edit";
        }
        feedbackService.updateFeedback(feedbackDTO);
        return "redirect:/admin/feedback/index";
    }
}
