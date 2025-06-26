package uth.edu.dieutrihiemmuon.controllers.Admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uth.edu.dieutrihiemmuon.config.CustomUserDetails;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String index(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);
        return "admin/index";  // đúng đường dẫn tới file
    }


    // employee


    @GetMapping("admin/ScheduleManagement/DoctorScheduleManagement")
    public String ScheduleManagement() {
        return "admin/ScheduleManagement/DoctorScheduleManagement";
    }

    @GetMapping("admin/ScheduleManagement/DetailDoctorScheduleManagement")
    public String DetailDoctorScheduleManagement() {
        return "admin/ScheduleManagement/DetailDoctorScheduleManagement";
    }

    @GetMapping("admin/ScheduleManagement/ConfirmDoctorSchedule")
    public String ConfirmDoctorSchedule() {
        return "admin/ScheduleManagement/ConfirmDoctorSchedule";
    }



}
