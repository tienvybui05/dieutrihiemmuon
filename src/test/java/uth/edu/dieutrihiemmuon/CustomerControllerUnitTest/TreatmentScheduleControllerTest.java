package uth.edu.dieutrihiemmuon.CustomerControllerUnitTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import uth.edu.dieutrihiemmuon.controllers.Customer.TreatmentScheduleController;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.ITreatmentCycleService;
import uth.edu.dieutrihiemmuon.services.ITreatmentSessionService;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TreatmentScheduleControllerTest {

    @InjectMocks
    private TreatmentScheduleController treatmentScheduleController;

    @Mock
    private ICustomerService customerService;

    @Mock
    private ITreatmentCycleService treatmentCycleService;

    @Mock
    private ITreatmentSessionService treatmentSessionService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    // ✅ Test GET /treatmentschedulecustomer
    @Test
    void testTreatmentschedulecustomer_ReturnsViewWithData() {
        String username = "testuser";
        when(authentication.getName()).thenReturn(username);

        User user = new User();
        user.setIdUser(1L);
        when(customerService.findByUsername(username)).thenReturn(user);

        List<WorkscheduledoctorDTO> schedules = Collections.singletonList(new WorkscheduledoctorDTO());
        when(treatmentCycleService.getTreatmentScheduleCustomer(1L)).thenReturn(schedules);

        String view = treatmentScheduleController.treatmentschedulecustomer(model, authentication);

        assertEquals("customer/treatmentschedule", view);
        verify(model).addAttribute("scheduleList", schedules);
    }

    // ✅ Test GET /treatmentcyclecustomer/{id}
    @Test
    void testTreatmentcyclecustomer_ReturnsViewWithSessions() {
        long id = 1L;
        List<TreatmentSessionDoctorDTO> sessions = Collections.singletonList(new TreatmentSessionDoctorDTO());
        when(treatmentSessionService.getTreatmentSessions(id)).thenReturn(sessions);

        String view = treatmentScheduleController.treatmentcyclecustomer(id, model);

        assertEquals("customer/treatmentcycle", view);
        verify(model).addAttribute("sessionList", sessions);
    }

    // ✅ Test POST /cancelSchedule
    @Test
    void testCancelSchedule_RedirectsToTreatmentScheduleCustomer() {
        Long id = 1L;

        String view = treatmentScheduleController.cancelSchedule(id);

        assertEquals("redirect:/treatmentschedulecustomer", view);
        verify(treatmentCycleService).deleteTreatmentCycle(id);
    }
}
