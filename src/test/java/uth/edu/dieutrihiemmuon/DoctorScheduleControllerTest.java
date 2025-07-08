package uth.edu.dieutrihiemmuon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uth.edu.dieutrihiemmuon.controllers.Customer.DoctorScheduleController;
import uth.edu.dieutrihiemmuon.dto.CheckScheduleDTO;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.ITreatmentCycleService;
import uth.edu.dieutrihiemmuon.services.ITreatmentSessionService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorScheduleControllerTest {

    @InjectMocks
    private DoctorScheduleController controller;

    @Mock
    private ICustomerService customerService;

    @Mock
    private ITreatmentSessionService treatmentSessionService;

    @Mock
    private ITreatmentCycleService treatmentCycleService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    // ✅ Test GET /workscheduledoctor filter=all
    @Test
    void testWorkScheduleFilter_All() {
        User user = mockDoctorUser(1L);
        when(authentication.getName()).thenReturn("doctorUser");
        when(customerService.findByUsername("doctorUser")).thenReturn(user);
        when(treatmentCycleService.getWorkscheduledoctor(1L)).thenReturn(List.of(new WorkscheduledoctorDTO()));
        when(treatmentCycleService.NumberOfExecutedAndUnexecutedSeriesInTheDay(1L))
                .thenReturn(new CheckScheduleDTO());

        String view = controller.workscheduleFilter("all", model, authentication);

        assertEquals("customer/doctor/workschedule", view);
        verify(model).addAttribute(eq("scheduleList"), anyList());
        verify(model).addAttribute(eq("currentFilter"), eq("all"));
    }

    // ✅ Test GET /workscheduledoctor filter=today
    @Test
    void testWorkScheduleFilter_Today() {
        User user = mockDoctorUser(2L);
        when(authentication.getName()).thenReturn("doctor2");
        when(customerService.findByUsername("doctor2")).thenReturn(user);
        when(treatmentCycleService.getTreatmentCycleToDay(2L)).thenReturn(List.of(new WorkscheduledoctorDTO()));
        when(treatmentCycleService.NumberOfExecutedAndUnexecutedSeriesInTheDay(2L))
                .thenReturn(new CheckScheduleDTO());

        String view = controller.workscheduleFilter("today", model, authentication);

        assertEquals("customer/doctor/workschedule", view);
        verify(model).addAttribute(eq("scheduleList"), anyList());
        verify(model).addAttribute(eq("currentFilter"), eq("today"));
    }

    // ✅ Test POST /saveGeneralNotes
    @Test
    void testSaveGeneralNotes() {

        String view = controller.saveGeneralNotes(1L, "Ghi chú test");

        assertEquals("redirect:/workscheduledoctor", view);
        verify(treatmentCycleService).updateGeneralNotes(1L, "Ghi chú test");
    }

    // ✅ Test GET /treatmentcycledoctor/{id}
    @Test
    void testTreatmentCycleDoctor_Get() {
        long treatmentCycleId = 5L;
        when(treatmentSessionService.getTreatmentSessions(treatmentCycleId))
                .thenReturn(List.of(new TreatmentSessionDoctorDTO()));

        String view = controller.treatmentcycledoctor(treatmentCycleId, model);

        assertEquals("customer/doctor/treatmentcycle", view);
        verify(model).addAttribute(eq("sessionList"), anyList());
        verify(model).addAttribute(eq("treatmentSession"), any(TreatmentSessionDoctorDTO.class));
    }

    // ✅ Test POST /treatmentcycledoctor success
    @Test
    void testTreatmentCycleDoctor_Post_Success() {
        TreatmentSessionDoctorDTO dto = new TreatmentSessionDoctorDTO();
        dto.setTreatmentDay(LocalDate.now().plusDays(1));
        dto.setIdTreatmentCycle(10L);

        String view = controller.treatmentcycledoctorUpdate(dto, model, bindingResult);

        assertEquals("redirect:/treatmentcycledoctor/10", view);
        verify(treatmentSessionService).updateTreatmentSessionDTO(dto);
    }

    // ✅ Test POST /treatmentcycledoctor fail (invalid date)
    @Test
    void testTreatmentCycleDoctor_Post_Fail_InvalidDate() {
        TreatmentSessionDoctorDTO dto = new TreatmentSessionDoctorDTO();
        dto.setTreatmentDay(LocalDate.now().minusDays(1));
        dto.setIdTreatmentCycle(10L);

        when(bindingResult.hasErrors()).thenReturn(true);
        when(treatmentSessionService.getTreatmentSessions(10L))
                .thenReturn(List.of(new TreatmentSessionDoctorDTO()));

        String view = controller.treatmentcycledoctorUpdate(dto, model, bindingResult);

        assertEquals("customer/doctor/treatmentcycle", view);
        verify(model).addAttribute(eq("sessionList"), anyList());
    }

    // ✅ Helper to create mock User with Doctor
    private User mockDoctorUser(Long doctorId) {
        User user = new User();
        Doctor doctor = new Doctor();
        doctor.setIdDoctor(doctorId);
        user.setDoctor(doctor);
        return user;
    }
}
