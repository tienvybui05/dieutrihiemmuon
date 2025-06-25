package uth.edu.dieutrihiemmuon.controllers.Admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import uth.edu.dieutrihiemmuon.services.IEmployeeService;

@Controller
public class EmployeeController {
    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Trang index của nhân viên
    @GetMapping("/admin/employee/index")
    public String adminEmployeeIndex() {
        return "admin/employee/index"; // Trả về trang index của nhân viên
    }

    // Trang tạo mới nhân viên
    @GetMapping("/admin/employee/create")
    public String adminEmployeeCreate() {
        return "admin/employee/create"; // Trả về trang tạo mới nhân viên
    }
  

    // Trang xem chi tiết nhân viên




}
