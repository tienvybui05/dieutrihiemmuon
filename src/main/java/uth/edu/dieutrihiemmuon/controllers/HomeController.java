package uth.edu.dieutrihiemmuon.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.dieutrihiemmuon.dto.*;

import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IServicePackageService servicePackageService;
    @Autowired
    private IDoctorService doctorService;
    @Autowired
    private ITreatmentCycleService treatmentCycleService;

    @Autowired
    private ITreatmentSessionService treatmentSessionService;
//    @GetMapping("/")
//    public String index() {
//        return "customer/index";  // đúng đường dẫn tới file
//    }
    @GetMapping("/")
    public String adminservicepackageindex( Model model) {
        List<ServicePackageDTO> servicePackageDTOS = servicePackageService.getServicePackages();
        model.addAttribute("ServicePackageDTOs", servicePackageDTOS);
        return "customer/index";
    }
    @GetMapping("/about")
    public String about() {
        return "customer/about";
    }
    @GetMapping("/blog")
    public String blog(){ return "customer/blog";}
    @GetMapping("/contact")
    public String contact(){ return "customer/contact";}

//    @GetMapping("/appointment")
//    public String appointment() { return "redirect:/";}

    @GetMapping("/appointment/{id}")
    public String appointment(@PathVariable("id") Long id, Model model) {
        ServicePackageDTO servicePackage = servicePackageService.getServicePackage(id);
        model.addAttribute("servicePackageDTO", servicePackage);
        List<DoctorDTO> doctors = doctorService.getDoctorsByServiceId(id);
        model.addAttribute("doctors", doctors);
        return "customer/appointment";
    }
    @PostMapping("/appointment/{id}")
    public String handleAppointmentSubmit(HttpServletRequest request,
                                          Model model,
                                          Authentication authentication,
                                          @PathVariable("id") Long id) {
        Long serviceId = Long.parseLong(request.getParameter("serviceId"));
        Long doctorId = Long.parseLong(request.getParameter("doctorId"));
        String startDateStr = request.getParameter("startDate");

        boolean hasError = false;

        LocalDate startDate = null;
        try {
            startDate = LocalDate.parse(startDateStr);

            if (startDate.isBefore(LocalDate.now())) {
                model.addAttribute("startDateError", "Ngày bắt đầu phải từ hôm nay trở đi.");
                hasError = true;
            }
        } catch (Exception e) {
            model.addAttribute("startDateError", "Vui lòng chọn ngày khám hợp lệ.");
            hasError = true;
        }

        // Nếu có lỗi, load lại form
        if (hasError) {
            ServicePackageDTO servicePackage = servicePackageService.getServicePackage(id);
            List<DoctorDTO> doctors = doctorService.getDoctorsByServiceId(id);
            model.addAttribute("servicePackageDTO", servicePackage);
            model.addAttribute("doctors", doctors);
            return "customer/appointment";
        }

        // Xử lý nếu không có lỗi
        String username = authentication.getName();
        User user = customerService.findByUsername(username);

        boolean success = treatmentCycleService.addAppointment(serviceId, doctorId, startDate, user.getIdUser());

        ServicePackageDTO servicePackage = servicePackageService.getServicePackage(id);
        List<DoctorDTO> doctors = doctorService.getDoctorsByServiceId(id);
        model.addAttribute("servicePackageDTO", servicePackage);
        model.addAttribute("doctors", doctors);

        if (success) {
            model.addAttribute("showPaymentModal", true);
        } else {
            model.addAttribute("error", "Đặt lịch thất bại.");
        }

        return "customer/appointment";
    }






    @GetMapping("/history")
    public String history() { return "customer/history";}
    @GetMapping("/payment")
    public String payment() { return "customer/payment";}
    @GetMapping("/treatmentschedulecustomer")
    public String treatmentschedulecustomer(Model model,Authentication authentication) {

        String username = authentication.getName();
        User user = customerService.findByUsername(username);
        List<WorkscheduledoctorDTO> wsd = treatmentCycleService.getTreatmentScheduleCustomer(user.getIdUser());
        model.addAttribute("scheduleList", wsd);
        return "customer/treatmentschedule";
    }
    @GetMapping("/treatmentcyclecustomer/{id}")
    public String treatmentcyclecustomer(@PathVariable long id,Model model) {
        List<TreatmentSessionDoctorDTO> tsd = treatmentSessionService.getTreatmentSessions(id);
        TreatmentSessionDoctorDTO treatmentSessionDoctorDTO = new TreatmentSessionDoctorDTO();
        model.addAttribute("sessionList", tsd);
        return "customer/treatmentcycle";

    }
    @PostMapping("/cancelSchedule")
    public String cancelSchedule(@RequestParam("id") Long id) {
        treatmentCycleService.deleteTreatmentCycle(id);
        return "redirect:/treatmentschedulecustomer";
    }
    @GetMapping("/workscheduledoctor")
    public String workscheduledoctor(Model model,Authentication authentication ) {
        String username = authentication.getName();
        User user = customerService.findByUsername(username);
        long idDoctor = user.getDoctor().getIdDoctor();
        List<WorkscheduledoctorDTO> wsd = treatmentCycleService.getWorkscheduledoctor(idDoctor);
        model.addAttribute("scheduleList", wsd);
        return "customer/doctor/workschedule";
    }
    @PostMapping("/saveGeneralNotes")
    public String saveGeneralNotes(@RequestParam Long id, @RequestParam String note) {
        treatmentCycleService.updateGeneralNotes(id, note);
        return "redirect:/workscheduledoctor";

    }

    @GetMapping("/treatmentcycledoctor/{id}")
    public String treatmentcycledoctor(@PathVariable long id,Model model) {
        List<TreatmentSessionDoctorDTO> tsd = treatmentSessionService.getTreatmentSessions(id);
        TreatmentSessionDoctorDTO treatmentSessionDoctorDTO = new TreatmentSessionDoctorDTO();
        model.addAttribute("treatmentSession", treatmentSessionDoctorDTO);
        model.addAttribute("sessionList", tsd);
        return "customer/doctor/treatmentcycle";
    }

    @PostMapping("/treatmentcycledoctor")
    public String treatmentcycledoctorUpdate(@ModelAttribute("treatmentSession") TreatmentSessionDoctorDTO treatmentSessionDoctorDTO
            , Model model) {
        treatmentSessionService.updateTreatmentSessionDTO(treatmentSessionDoctorDTO);

        return "redirect:/treatmentcycledoctor/" + treatmentSessionDoctorDTO.getIdTreatmentCycle();

    }
    @GetMapping("/profile")
    public String profile() { return "customer/profile";}

    //Services
    @GetMapping("/services")
    public String services() {
        return "customer/services/index";  // đúng đường dẫn tới file
    }

    //Đăng nhập và đăng ký
    @GetMapping("/login")
    public String login(Authentication authentication) {
//        model.addAttribute("title", "Login");
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            return "redirect:/";
        }
        return "customer/auth/login";  // đúng đường dẫn tới file
    }

    @GetMapping("/register")
    public String register(Model model) {
          model.addAttribute("RegisterDTO", new RegisterDTO());
//        model.addAttribute("title", "Register");
        return "customer/auth/register";  // đúng đường dẫn tới file
    }
    @PostMapping("/register")
    public String addAccount(@Valid @ModelAttribute("RegisterDTO") RegisterDTO registerDTO,
                             BindingResult result,Model model) {

        if (customerService.isUsernameExists(registerDTO.getUserName())) {
            result.rejectValue("userName", "error.customer", "Tên đăng nhập đã tồn tại");
        }
        // Kiểm tra trùng email
        if (customerService.isEmailExists(registerDTO.getEmail())) {
            result.rejectValue("email", "error.customer", "Email đã tồn tại");
        }
        // Kiểm tra trùng SĐT
        if (customerService.isPhoneNumberExists(registerDTO.getPhoneNumber())) {
            result.rejectValue("phoneNumber", "error.customer", "Số điện thoại đã tồn tại");
        }

        if (result.hasErrors()) {
            return "customer/auth/register";
        }
        customerService.addAccount(registerDTO);
        return "redirect:/";
    }
    @GetMapping("/customer/auth/error403")
    public String customerError403() {
        return "customer/auth/error403";
    }
    //
}
