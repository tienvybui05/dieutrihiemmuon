package uth.edu.dieutrihiemmuon.controllers.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.ITreatmentSessionService;

import java.util.List;

@Controller
public class DoctorScheduleController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITreatmentSessionService treatmentSessionService;

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

}
