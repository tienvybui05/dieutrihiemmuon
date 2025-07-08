package uth.edu.dieutrihiemmuon.CustomerControllerUnitTest;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import uth.edu.dieutrihiemmuon.controllers.Customer.AppointmentController;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.dto.FeedbackInformationDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {

    @InjectMocks
    private AppointmentController appointmentController;

    @Mock
    private ICustomerService customerService;

    @Mock
    private IServicePackageService servicePackageService;

    @Mock
    private IDoctorService doctorService;

    @Mock
    private ITreatmentCycleService treatmentCycleService;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private HttpServletRequest request;

    // ✅ Test GET /appointment/{id}
    @Test
    void testAppointment_ReturnsViewWithAttributes() {
        Long id = 1L;
        ServicePackageDTO service = new ServicePackageDTO();
        List<DoctorDTO> doctors = List.of(new DoctorDTO());
        List<FeedbackInformationDTO> feedbacks = List.of(new FeedbackInformationDTO());

        when(servicePackageService.getServicePackage(id)).thenReturn(service);
        when(doctorService.getDoctorsByServiceId(id)).thenReturn(doctors);
        when(feedbackService.getFeedbackInformationList()).thenReturn(feedbacks);

        String view = appointmentController.appointment(id, model);

        assertEquals("customer/appointment", view);
        verify(model).addAttribute("servicePackageDTO", service);
        verify(model).addAttribute("doctors", doctors);
        verify(model).addAttribute("FeedbackInformationDTOs", feedbacks);
    }

    // ✅ Test POST /appointment/{id} success
    @Test
    void testHandleAppointmentSubmit_Success() {
        Long id = 1L;
        when(request.getParameter("serviceId")).thenReturn("1");
        when(request.getParameter("doctorId")).thenReturn("2");
        when(request.getParameter("startDate")).thenReturn(LocalDate.now().plusDays(1).toString());
        when(authentication.getName()).thenReturn("user");
        User mockUser = new User();
        mockUser.setIdUser(123L);
        when(customerService.findByUsername("user")).thenReturn(mockUser);
        when(treatmentCycleService.addAppointment(anyLong(), anyLong(), any(LocalDate.class), eq(123L)))
                .thenReturn(true);
        when(servicePackageService.getServicePackage(id)).thenReturn(new ServicePackageDTO());
        when(doctorService.getDoctorsByServiceId(id)).thenReturn(List.of());

        String view = appointmentController.handleAppointmentSubmit(request, model, authentication, id);

        assertEquals("customer/appointment", view);
        verify(model).addAttribute("showPaymentModal", true);
    }

    // ✅ Test POST /appointment/{id} fail vì ngày quá khứ
    @Test
    void testHandleAppointmentSubmit_Fail_InvalidDate() {
        Long id = 1L;
        when(request.getParameter("serviceId")).thenReturn("1");
        when(request.getParameter("doctorId")).thenReturn("2");
        when(request.getParameter("startDate")).thenReturn(LocalDate.now().minusDays(1).toString());

        String view = appointmentController.handleAppointmentSubmit(request, model, authentication, id);

        assertEquals("customer/appointment", view);
        verify(model).addAttribute(eq("startDateError"), contains("từ hôm nay trở đi"));
    }

    // ✅ Test GET /appointmentnoid
    @Test
    void testAppointmentNoId_ReturnsViewWithServices() {
        List<ServicePackageDTO> services = List.of(new ServicePackageDTO());
        when(servicePackageService.getServicePackages()).thenReturn(services);

        String view = appointmentController.appointmentnoid(model);

        assertEquals("customer/appointmentnoid", view);
        verify(model).addAttribute("servicePackageDTOs", services);
    }

    // ✅ Test POST /appointmentnoid success
    @Test
    void testHandleAppointmentNoIdSubmit_Success() {
        when(request.getParameter("serviceId")).thenReturn("1");
        when(request.getParameter("doctorId")).thenReturn("2");
        when(request.getParameter("startDate")).thenReturn(LocalDate.now().plusDays(1).toString());
        when(authentication.getName()).thenReturn("user");
        User mockUser = new User();
        mockUser.setIdUser(123L);
        when(customerService.findByUsername("user")).thenReturn(mockUser);
        when(treatmentCycleService.addAppointment(anyLong(), anyLong(), any(LocalDate.class), eq(123L)))
                .thenReturn(true);
        when(servicePackageService.getServicePackages()).thenReturn(List.of());

        String view = appointmentController.handleAppointmentSubmit(request, model, authentication);

        assertEquals("customer/appointmentnoid", view);
        verify(model).addAttribute("showPaymentModal", true);
    }

    // ✅ Test GET /doctors/by-service/{id}
    @Test
    void testGetDoctorsByService_ReturnsDoctorList() {
        Long id = 1L;
        List<DoctorDTO> doctors = List.of(new DoctorDTO());
        when(doctorService.getDoctorsByServiceId(id)).thenReturn(doctors);

        List<DoctorDTO> result = appointmentController.getDoctorsByService(id);

        assertEquals(doctors, result);
    }
}
