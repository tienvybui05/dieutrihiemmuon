package uth.edu.dieutrihiemmuon.controllers.Admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String addDoctor(@Valid @ModelAttribute("DoctorDTO") DoctorDTO doctorDTO , BindingResult result, Model model) {

        if (doctorService.findByUsername(doctorDTO.getUserName()) != null) {
            result.rejectValue("userName", "error.DoctorDTO", "Tên đăng nhập đã tồn tại");
        }
        if(doctorService.findByEmail(doctorDTO.getEmail()) != null) {
            result.rejectValue("email", "error.DoctorDTO", "Email đã tồn tại");
        }
        if(doctorService.findByPhoneNumber(doctorDTO.getPhoneNumber()) != null) {
            result.rejectValue("phoneNumber", "error.DoctorDTO", "Số điện thoại đã tồn tại");
        }
        if (result.hasErrors()) {
            model.addAttribute("DoctorDTO", doctorDTO);
            return "admin/doctor/create";
        }
        if(doctorService.addDoctor(doctorDTO)==true)
        {
            model.addAttribute("message","Thêm thành công!");
        }
        else{
            model.addAttribute("message","Thêm không thành công!");
        }
        return "redirect:/admin/doctor/index";

    }


    @GetMapping("/admin/doctor/edit/{id}")
    public String admindoctoredit(@PathVariable long id, Model model) {
        DoctorDTO doctorDTO = doctorService.getDoctor(id);
        model.addAttribute("DoctorDTO", doctorDTO);
        return "admin/doctor/edit";
    }
    @PostMapping("/admin/doctor/edit/{id}")
    public String editDoctor(@PathVariable long id, @ModelAttribute("DoctorDTO") DoctorDTO doctorDTO, BindingResult result, Model model) {

        DoctorDTO doctorDTOUserName= doctorService.findByUsername(doctorDTO.getUserName());
        DoctorDTO doctorDTOEmail= doctorService.findByEmail(doctorDTO.getEmail());
        DoctorDTO doctorDTOPhoneNumber= doctorService.findByPhoneNumber(doctorDTO.getPhoneNumber());
        if (doctorDTOUserName != null && doctorDTOUserName.getId_doctor() != id) {
            result.rejectValue("userName", "error.doctorDTO", "Tên đăng nhập đã tồn tại");
        }
        if (doctorDTOEmail != null && doctorDTOEmail.getId_doctor() != id) {
            result.rejectValue("email", "error.doctorDTO", "Email đã tồn tại");
        }
        if (doctorDTOPhoneNumber != null && doctorDTOPhoneNumber.getId_doctor() != id) {
            result.rejectValue("phoneNumber", "error.doctorDTO", "Số điện thoại đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("DoctorDTO", doctorDTO);
            return "/admin/doctor/edit";
        }
        doctorService.updateDoctor(doctorDTO);
        return "redirect:/admin/doctor/index";
    }

    @GetMapping("/admin/doctor/detail/{id}")
    public String admindoctordetail(@PathVariable long id, Model model) {
        DoctorDTO doctorDTO = doctorService.getDoctor(id);
        model.addAttribute("DoctorDTO", doctorDTO);
        return "admin/doctor/detail";
    }
    @GetMapping("/admin/doctor/delete/{id}")
    public String admindoctordelete(@PathVariable long id, Model model) {
        doctorService.deleteDoctor(id);
        return "redirect:/admin/doctor/index";
    }
}
