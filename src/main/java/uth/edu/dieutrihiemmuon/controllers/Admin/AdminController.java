package uth.edu.dieutrihiemmuon.controllers.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String index() {
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
