package uth.edu.dieutrihiemmuon.AdminControllerUnitTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

import uth.edu.dieutrihiemmuon.controllers.Admin.AdminController;
import uth.edu.dieutrihiemmuon.dto.*;
import uth.edu.dieutrihiemmuon.services.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private IUserService userService;

    @Mock
    private IDoctorService doctorService;

    @Mock
    private ITreatmentCycleService treatmentCycleService;

    @Mock
    private ICustomerService customerService;

    @Mock
    private ITreatmentSessionService treatmentSessionService;

    @Mock
    private IDoctorService docService;

    @Mock
    private IServicePackageService servicePackageService;

    @Mock
    private IFeedbackService feedbackService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    // Test GET /admin (dashboard)
    @Test
    void testIndex_ReturnsAdminIndexViewWithAttributes() {
        // Mock data
        String username = "adminUser";
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(username);

        List<FeedbackInformationDTO> feedbacks = List.of(
                new FeedbackInformationDTO(), new FeedbackInformationDTO(),
                new FeedbackInformationDTO(), new FeedbackInformationDTO()
        );

        when(authentication.getName()).thenReturn(username);
        when(userService.getUserByUserName(username)).thenReturn(userDTO);
        when(customerService.countCustomers()).thenReturn(10L);
        when(doctorService.countDoctors()).thenReturn(5L);
        when(servicePackageService.countServicePackage()).thenReturn(3L);
        when(treatmentCycleService.revenue()).thenReturn(15000.0);
        when(treatmentCycleService.numberOfSchedulesToDayALL()).thenReturn(7L);
        when(feedbackService.getTop4FeedbackInformation()).thenReturn(feedbacks);

        // Gọi controller
        String view = adminController.index(authentication, model);

        // Assert view name
        assertEquals("admin/index", view);

        // ✅ Verify model attributes
        verify(model).addAttribute("user", userDTO);
        verify(model).addAttribute("countCustomer", 10L);
        verify(model).addAttribute("countDoctor", 5L);
        verify(model).addAttribute("countservice", 3L);
        verify(model).addAttribute("revenue", 15000.0);
        verify(model).addAttribute("countSchedule", 7L);
        verify(model).addAttribute("feedbackInformationDTOS", feedbacks);

        // ✅ Verify all services called
        verify(userService).getUserByUserName(username);
        verify(customerService).countCustomers();
        verify(doctorService).countDoctors();
        verify(servicePackageService).countServicePackage();
        verify(treatmentCycleService).revenue();
        verify(treatmentCycleService).numberOfSchedulesToDayALL();
        verify(feedbackService).getTop4FeedbackInformation();
    }
}
