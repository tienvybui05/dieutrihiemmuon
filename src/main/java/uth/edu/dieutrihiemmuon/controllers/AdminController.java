package uth.edu.dieutrihiemmuon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String index() {
        return "admin/index";  // đúng đường dẫn tới file
    }

    //admin/doctor
    @GetMapping("/admin/doctor/index")
    public String admindoctorindex() {
        return "admin/doctor/index";
    }
    @GetMapping("/admin/doctor/create")
    public String admindoctorcreate() {
        return "admin/doctor/create";
    }
    @GetMapping("/admin/doctor/edit")
    public String admindoctoredit() {
        return "admin/doctor/edit";
    }
    @GetMapping("/admin/doctor/detail")
    public String admindoctordetail() {
        return "admin/doctor/detail";
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
}
