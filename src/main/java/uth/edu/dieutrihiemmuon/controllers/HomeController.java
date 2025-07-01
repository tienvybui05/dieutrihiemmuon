package uth.edu.dieutrihiemmuon.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.dieutrihiemmuon.dto.*;

import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IServicePackageService servicePackageService;
    @Autowired
    private IDoctorService doctorService;
    @Autowired
    private ITreatmentCycleService treatmentCycleService;

    @Autowired
    private ITreatmentSessionService treatmentSessionService;
//    @GetMapping("/")
//    public String index() {
//        return "customer/index";  // đúng đường dẫn tới file
//    }






    @GetMapping("/history")
    public String history() { return "customer/history";}
    @GetMapping("/payment")
    public String payment() { return "customer/payment";}
    
    @GetMapping("/workscheduledoctor")
    public String workscheduledoctor(Model model,Authentication authentication ) {
        String username = authentication.getName();
        User user = customerService.findByUsername(username);
        long idDoctor = user.getDoctor().getIdDoctor();
        List<WorkscheduledoctorDTO> wsd = treatmentCycleService.getWorkscheduledoctor(idDoctor);
        model.addAttribute("scheduleList", wsd);
        return "customer/doctor/workschedule";
    }
    @PostMapping("/saveGeneralNotes")
    public String saveGeneralNotes(@RequestParam Long id, @RequestParam String note) {
        treatmentCycleService.updateGeneralNotes(id, note);
        return "redirect:/workscheduledoctor";

    }

    @GetMapping("/treatmentcycledoctor/{id}")
    public String treatmentcycledoctor(@PathVariable long id,Model model) {
        List<TreatmentSessionDoctorDTO> tsd = treatmentSessionService.getTreatmentSessions(id);
        TreatmentSessionDoctorDTO treatmentSessionDoctorDTO = new TreatmentSessionDoctorDTO();
        model.addAttribute("treatmentSession", treatmentSessionDoctorDTO);
        model.addAttribute("sessionList", tsd);
        return "customer/doctor/treatmentcycle";
    }

    @PostMapping("/treatmentcycledoctor")
    public String treatmentcycledoctorUpdate(@ModelAttribute("treatmentSession") TreatmentSessionDoctorDTO treatmentSessionDoctorDTO
            , Model model) {
        treatmentSessionService.updateTreatmentSessionDTO(treatmentSessionDoctorDTO);

        return "redirect:/treatmentcycledoctor/" + treatmentSessionDoctorDTO.getIdTreatmentCycle();

    }
    @GetMapping("/profile")
    public String profile() { return "customer/profile";}

    //Services
    @GetMapping("/services")
    public String services() {
        return "customer/services/index";  // đúng đường dẫn tới file
    }

    
    //
}
