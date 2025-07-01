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









}
