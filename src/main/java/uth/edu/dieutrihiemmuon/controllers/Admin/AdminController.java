package uth.edu.dieutrihiemmuon.controllers.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String index() {
        return "admin/index";  // đúng đường dẫn tới file
    }

    //admin/customer
    @GetMapping("/admin/customer/index")
    public String admincustomerindex() {
        return "admin/customer/index";
    }
    @GetMapping("/admin/customer/create")
    public String admincustomercreate() {
        return "admin/customer/create";
    }
    @GetMapping("/admin/customer/edit")
    public String admincustomeredit() {
        return "admin/customer/edit";
    }

    // employee
    @GetMapping("/admin/employee/create")
    public String adminemployeecreate() {
        return "admin/employee/create";
    }
    // auth
    @GetMapping("/admin/auth/login")
    public String adminauthlogin() {
        return "admin/auth/login";
    }

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
