package uth.edu.dieutrihiemmuon.controllers.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

@Controller
public class AdminController {

    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/admin")
    public String index(Authentication authentication, Model model) {

        String username = authentication.getName();
        User user = userRepository.findByUserName(username);
        model.addAttribute("user", user);
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
