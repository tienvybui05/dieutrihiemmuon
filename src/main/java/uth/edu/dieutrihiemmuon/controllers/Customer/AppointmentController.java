package uth.edu.dieutrihiemmuon.controllers.Customer;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.dto.FeedbackInformationDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AppointmentController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IServicePackageService servicePackageService;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private ITreatmentCycleService treatmentCycleService;
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/appointment/{id}")
    public String appointment(@PathVariable("id") Long id, Model model) {
        ServicePackageDTO servicePackage = servicePackageService.getServicePackage(id);
        model.addAttribute("servicePackageDTO", servicePackage);
        List<DoctorDTO> doctors = doctorService.getDoctorsByServiceId(id);
        model.addAttribute("doctors", doctors);
        List<FeedbackInformationDTO> feedbackInformationDTOS = feedbackService.getFeedbackInformationList();
        model.addAttribute("FeedbackInformationDTOs", feedbackInformationDTOS);
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

    @GetMapping("/appointmentnoid")
    public String appointmentnoid(Model model) {
        List<ServicePackageDTO> servicePackage = servicePackageService.getServicePackages();
        model.addAttribute("servicePackageDTOs", servicePackage);
        return "customer/appointmentnoid";
    }

    @GetMapping("/doctors/by-service/{id}")
    @ResponseBody
    public List<DoctorDTO> getDoctorsByService(@PathVariable("id") Long id) {
        return doctorService.getDoctorsByServiceId(id);
    }

    @PostMapping("/appointmentnoid")
    public String handleAppointmentSubmit(HttpServletRequest request,
                                          Model model,
                                          Authentication authentication) {
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
            List<ServicePackageDTO> servicePackage = servicePackageService.getServicePackages();
            model.addAttribute("servicePackageDTOs", servicePackage);
            return "customer/appointmentnoid";
        }

        // Xử lý nếu không có lỗi
        String username = authentication.getName();
        User user = customerService.findByUsername(username);

        boolean success = treatmentCycleService.addAppointment(serviceId, doctorId, startDate, user.getIdUser());

        List<ServicePackageDTO> servicePackage = servicePackageService.getServicePackages();
        model.addAttribute("servicePackageDTOs", servicePackage);

        if (success) {
            model.addAttribute("showPaymentModal", true);
        } else {
            model.addAttribute("error", "Đặt lịch thất bại.");
        }

        return "customer/appointmentnoid";
    }

}
