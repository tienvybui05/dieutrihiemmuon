package uth.edu.dieutrihiemmuon.controllers.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.dto.UserDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
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

    @GetMapping("/admin")
    public String index(Authentication authentication, Model model) {
        String username = authentication.getName();
        UserDTO userDTO = userService.getUserByUserName(username);
        model.addAttribute("user", userDTO);
        return "admin/index";
    }


    // employee

    @GetMapping("admin/ScheduleManagement/DoctorScheduleManagement")
    public String ScheduleManagement( Model model) {

        List<DoctorDTO> doctorDTOS = doctorService.getDoctors();
        model.addAttribute("DoctorDTOs", doctorDTOS);
        return "admin/ScheduleManagement/DoctorScheduleManagement";
    }


    @GetMapping("admin/ScheduleManagement/ConfirmDoctorSchedule/{id}")
    public String ConfirmDoctorSchedule(Model model,Authentication authentication, @PathVariable long id) {
        List<WorkscheduledoctorDTO> wsd = treatmentCycleService.getWorkscheduledoctor(id);
        model.addAttribute("scheduleList", wsd);
        return "admin/ScheduleManagement/ConfirmDoctorSchedule";
    }

    @GetMapping("admin/ScheduleManagement/DetailDoctorScheduleManagement/{id}")
    public String DetailDoctorScheduleManagement(@PathVariable long id,Model model) {
        List<TreatmentSessionDoctorDTO> tsd = treatmentSessionService.getTreatmentSessions(id);
        String nameCustomer = treatmentCycleService.getNameCustomerToTreatmentCycle(id);
        model.addAttribute("nameCustomer", nameCustomer);
        model.addAttribute("sessionList", tsd);
        return "admin/ScheduleManagement/DetailDoctorScheduleManagement";
    }

    @PostMapping("admin/ScheduleManagement/ConfirmDoctorSchedule/{id}")
    public String confirmSchedule(@PathVariable("id") Long id, @RequestParam("doctorId") Long doctorId) {
        treatmentCycleService.updateConfirmationStatus(id);
        return "redirect:/admin/ScheduleManagement/ConfirmDoctorSchedule/" + doctorId;
    }









}
