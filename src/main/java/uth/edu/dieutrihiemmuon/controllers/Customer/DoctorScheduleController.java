package uth.edu.dieutrihiemmuon.controllers.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.ITreatmentCycleService;
import uth.edu.dieutrihiemmuon.services.ITreatmentSessionService;

import java.util.List;

@Controller
public class DoctorScheduleController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITreatmentSessionService treatmentSessionService;

    @Autowired
    private ITreatmentCycleService treatmentCycleService;

    @GetMapping("/treatmentcycledoctor/{id}")
    public String treatmentcycledoctor(@PathVariable long id, Model model) {
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

    @GetMapping("/workscheduledoctor")
    public String workscheduledoctor(Model model, Authentication authentication ) {
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

}
