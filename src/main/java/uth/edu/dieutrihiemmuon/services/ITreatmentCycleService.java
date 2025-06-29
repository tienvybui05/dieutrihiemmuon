package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.TreatmentCycleDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;

import java.time.LocalDate;
import java.util.List;

public interface ITreatmentCycleService {
    boolean addAppointment(Long serviceId, Long doctorId, LocalDate startDate, Long userId);
    public List<WorkscheduledoctorDTO> getWorkscheduledoctor();
    public boolean updateGeneralNotes(long id ,String notes);

}

