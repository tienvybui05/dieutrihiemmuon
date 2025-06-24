package uth.edu.dieutrihiemmuon.controllers.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.services.DoctorService;
import uth.edu.dieutrihiemmuon.services.IDoctorService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private IDoctorService doctorService;

    @GetMapping("/admin/doctor/index")
    public String admindoctorindex( Model model) {

        List<DoctorDTO> doctorDTOS = doctorService.getDoctors();
        model.addAttribute("DoctorDTOs", doctorDTOS);
        return "admin/doctor/index";
    }


    @GetMapping("/admin/doctor/create")
    public String admindoctorcreate( Model model)
    {
            model.addAttribute("DoctorDTO",new DoctorDTO());
            return "admin/doctor/create";
    }

    @PostMapping("/admin/doctor/create")
    public String addDoctor(@ModelAttribute("DoctorDTO") DoctorDTO doctorDTO, Model model) {



        if(doctorService.addDoctor(doctorDTO)==true)
        {
            model.addAttribute("message","Thêm thành công!");
        }
        else{
            model.addAttribute("message","Thêm không thành công!");
        }
        return "redirect:/admin/doctor/index";

    }
    @GetMapping("/admin/doctor/edit")
    public String admindoctoredit() {
        return "admin/doctor/edit";
    }
    @GetMapping("/admin/doctor/detail/{id}")
    public String admindoctordetail(@PathVariable long id, Model model) {
        DoctorDTO doctorDTO = doctorService.getDoctor(id);
        model.addAttribute("DoctorDTO", doctorDTO);
        return "admin/doctor/detail";
    }
}
