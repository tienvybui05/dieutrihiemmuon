package uth.edu.dieutrihiemmuon.controllers.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uth.edu.dieutrihiemmuon.dto.CheckScheduleDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.ITreatmentCycleService;
import uth.edu.dieutrihiemmuon.services.ITreatmentSessionService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DoctorScheduleController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITreatmentSessionService treatmentSessionService;

    @Autowired
    private ITreatmentCycleService treatmentCycleService;

    @GetMapping("/workscheduledoctor")
    public String workscheduleFilter(
            @RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
            Model model,
            Authentication authentication) {

        String username = authentication.getName();
        User user = customerService.findByUsername(username);
        long idDoctor = user.getDoctor().getIdDoctor();

        List<WorkscheduledoctorDTO> wsd;

        if ("today".equalsIgnoreCase(filter)) {
            wsd = treatmentCycleService.getTreatmentCycleToDay(idDoctor);
        } else {
            wsd = treatmentCycleService.getWorkscheduledoctor(idDoctor);
        }
        CheckScheduleDTO numberSchedule = treatmentCycleService.NumberOfExecutedAndUnexecutedSeriesInTheDay(idDoctor);
        model.addAttribute("numberSchedule",numberSchedule);
        model.addAttribute("scheduleList", wsd);
        model.addAttribute("currentFilter", filter);
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
            , Model model,BindingResult result) {
        LocalDate today = LocalDate.now();
        if (treatmentSessionDoctorDTO.getTreatmentDay() == null ||
                treatmentSessionDoctorDTO.getTreatmentDay().isBefore(today)) {
            result.rejectValue("treatmentDay", "error.treatmentSessionDoctorDTO", "Vui lòng nhập ngày hợp lệ");
        }

        if (result.hasErrors()) {
            // Load lại danh sách buổi điều trị
            List<TreatmentSessionDoctorDTO> tsd = treatmentSessionService.getTreatmentSessions(treatmentSessionDoctorDTO.getIdTreatmentCycle());
            model.addAttribute("sessionList", tsd);
            return "customer/doctor/treatmentcycle"; // render lại view
        }
        treatmentSessionService.updateTreatmentSessionDTO(treatmentSessionDoctorDTO);

        return "redirect:/treatmentcycledoctor/" + treatmentSessionDoctorDTO.getIdTreatmentCycle();

    }

}
