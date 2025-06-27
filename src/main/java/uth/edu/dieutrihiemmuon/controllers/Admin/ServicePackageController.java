package uth.edu.dieutrihiemmuon.controllers.Admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import uth.edu.dieutrihiemmuon.services.ServicePackageService;
import uth.edu.dieutrihiemmuon.services.IServicePackageService;

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
public class ServicePackageController {
    private final ServicePackageService servicePackageService;

    public ServicePackageController(ServicePackageService servicePackageService) {
        this.servicePackageService = servicePackageService;
    }

    @GetMapping("/admin/servicepackage/index")
    public String adminservicepackageindex( Model model) {

        List<ServicePackageDTO> servicePackageDTOS = servicePackageService.getServicePackages();
        model.addAttribute("ServicePackageDTOs", servicePackageDTOS);
        return "admin/servicepackage/index";
    }

    @GetMapping("/admin/servicepackage/create")
    public String adminservicepackagecreate( Model model)
    {
        model.addAttribute("ServicePackageDTO",new ServicePackageDTO());
        return "admin/servicepackage/create";
    }

    @PostMapping("/admin/servicepackage/create")
    public String addServicePackage(@Valid @ModelAttribute("ServicePackageDTO") ServicePackageDTO servicePackageDTO , BindingResult result, Model model) {

        if (servicePackageService.findByServiceName(servicePackageDTO.getServiceName()) != null) {
            result.rejectValue("serviceName", "error.ServicePackageDTO", "Tên dịch vụ đã tồn tại");
        }
        if (result.hasErrors()) {
            model.addAttribute("ServicePackageDTO", servicePackageDTO);
            return "admin/servicepackage/create";
        }
        if(servicePackageService.addServicePackage(servicePackageDTO)==true)
        {
            model.addAttribute("message","Thêm thành công!");
        }
        else{
            model.addAttribute("message","Thêm không thành công!");
        }
        return "redirect:/admin/servicepackage/index";

    }

    @GetMapping("/admin/servicepackage/detail/{id}")
    public String adminservicepackagedetail(@PathVariable long id, Model model) {
        ServicePackageDTO servicePackageDTO = servicePackageService.getServicePackage(id);
        model.addAttribute("ServicePackageDTO", servicePackageDTO);
        return "admin/servicepackage/detail";
    }
    @GetMapping("/admin/servicepackage/delete/{id}")
    public String adminservicepackagedelete(@PathVariable long id, Model model) {
        servicePackageService.deleteServicePackage(id);
        return "redirect:/admin/servicepackage/index";
    }
}
