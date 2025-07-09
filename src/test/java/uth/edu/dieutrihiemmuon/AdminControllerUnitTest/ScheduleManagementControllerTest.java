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

import uth.edu.dieutrihiemmuon.controllers.Admin.ScheduleManagementController;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
import uth.edu.dieutrihiemmuon.services.IDoctorService;
import uth.edu.dieutrihiemmuon.services.ITreatmentCycleService;
import uth.edu.dieutrihiemmuon.services.ITreatmentSessionService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ScheduleManagementControllerTest {

    @InjectMocks
    private ScheduleManagementController scheduleManagementController;

    @Mock
    private IDoctorService doctorService;

    @Mock
    private ITreatmentCycleService treatmentCycleService;

    @Mock
    private ITreatmentSessionService treatmentSessionService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    // Test GET /admin/ScheduleManagement/DoctorScheduleManagement
    @Test
    void testScheduleManagement_ReturnsViewWithDoctorList() {
        List<DoctorDTO> doctors = List.of(new DoctorDTO(), new DoctorDTO());
        when(doctorService.getDoctors()).thenReturn(doctors);

        String view = scheduleManagementController.ScheduleManagement(model);

        assertEquals("admin/ScheduleManagement/DoctorScheduleManagement", view);
        verify(doctorService).getDoctors();
        verify(model).addAttribute("DoctorDTOs", doctors);
    }

    // Test GET /admin/ScheduleManagement/ConfirmDoctorSchedule/{id}
    @Test
    void testConfirmDoctorSchedule_ReturnsViewWithScheduleList() {
        long doctorId = 1L;
        List<WorkscheduledoctorDTO> schedules = List.of(new WorkscheduledoctorDTO(), new WorkscheduledoctorDTO());
        when(treatmentCycleService.getWorkscheduledoctor(doctorId)).thenReturn(schedules);

        String view = scheduleManagementController.ConfirmDoctorSchedule(model, authentication, doctorId);

        assertEquals("admin/ScheduleManagement/ConfirmDoctorSchedule", view);
        verify(treatmentCycleService).getWorkscheduledoctor(doctorId);
        verify(model).addAttribute("scheduleList", schedules);
    }

    // Test GET /admin/ScheduleManagement/DetailDoctorScheduleManagement/{id}
    @Test
    void testDetailDoctorScheduleManagement_ReturnsViewWithSessionList() {
        long cycleId = 1L;
        List<TreatmentSessionDoctorDTO> sessions = List.of(new TreatmentSessionDoctorDTO());
        String customerName = "Nguyen Van A";

        when(treatmentSessionService.getTreatmentSessions(cycleId)).thenReturn(sessions);
        when(treatmentCycleService.getNameCustomerToTreatmentCycle(cycleId)).thenReturn(customerName);

        String view = scheduleManagementController.DetailDoctorScheduleManagement(cycleId, model);

        assertEquals("admin/ScheduleManagement/DetailDoctorScheduleManagement", view);
        verify(treatmentSessionService).getTreatmentSessions(cycleId);
        verify(treatmentCycleService).getNameCustomerToTreatmentCycle(cycleId);
        verify(model).addAttribute("nameCustomer", customerName);
        verify(model).addAttribute("sessionList", sessions);
    }

    // Test POST /admin/ScheduleManagement/ConfirmDoctorSchedule/{id}
    @Test
    void testConfirmSchedule_RedirectsToConfirmPage() {
        Long sessionId = 1L;
        Long doctorId = 2L;

        String view = scheduleManagementController.confirmSchedule(sessionId, doctorId);

        assertEquals("redirect:/admin/ScheduleManagement/ConfirmDoctorSchedule/" + doctorId, view);
        verify(treatmentCycleService).updateConfirmationStatus(sessionId);
    }

    // Test POST /admin/ScheduleManagement/CancelDoctorSchedule/{id}
    @Test
    void testCancelConfirmSchedule_RedirectsToConfirmPage() {
        Long sessionId = 1L;
        Long doctorId = 2L;

        String view = scheduleManagementController.cancelConfirmSchedule(sessionId, doctorId);

        assertEquals("redirect:/admin/ScheduleManagement/ConfirmDoctorSchedule/" + doctorId, view);
        verify(treatmentCycleService).cancelConfirmationStatus(sessionId);
    }
}
