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

    //admin
    @GetMapping("/admin/doctor/index")
    public String admindoctorindex() {
        return "admin/doctor/index";
    }
    @GetMapping("/admin/doctor/create")
    public String admindoctorcreate() {
        return "admin/doctor/create";
    }
}
