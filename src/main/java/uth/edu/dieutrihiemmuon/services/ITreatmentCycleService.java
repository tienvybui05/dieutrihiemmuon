package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.TreatmentCycleDTO;

import java.time.LocalDate;

public interface ITreatmentCycleService {
    boolean addAppointment(Long serviceId, Long doctorId, LocalDate startDate, Long userId);
}

