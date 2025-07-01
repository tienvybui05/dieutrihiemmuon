package uth.edu.dieutrihiemmuon.controllers.Customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.ITreatmentCycleService;
import uth.edu.dieutrihiemmuon.services.ITreatmentSessionService;

import java.util.List;

@Controller
public class TreatmentScheduleController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ITreatmentCycleService treatmentCycleService;

    @Autowired
    private ITreatmentSessionService treatmentSessionService;

    @GetMapping("/treatmentschedulecustomer")
    public String treatmentschedulecustomer(Model model, Authentication authentication) {

        String username = authentication.getName();
        User user = customerService.findByUsername(username);
        List<WorkscheduledoctorDTO> wsd = treatmentCycleService.getTreatmentScheduleCustomer(user.getIdUser());
        model.addAttribute("scheduleList", wsd);
        return "customer/treatmentschedule";
    }
    @GetMapping("/treatmentcyclecustomer/{id}")
    public String treatmentcyclecustomer(@PathVariable long id, Model model) {
        List<TreatmentSessionDoctorDTO> tsd = treatmentSessionService.getTreatmentSessions(id);
        TreatmentSessionDoctorDTO treatmentSessionDoctorDTO = new TreatmentSessionDoctorDTO();
        model.addAttribute("sessionList", tsd);
        return "customer/treatmentcycle";

    }
    @PostMapping("/cancelSchedule")
    public String cancelSchedule(@RequestParam("id") Long id) {
        treatmentCycleService.deleteTreatmentCycle(id);
        return "redirect:/treatmentschedulecustomer";
    }

}
