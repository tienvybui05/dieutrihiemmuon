package uth.edu.dieutrihiemmuon.controllers.Admin;

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
}
