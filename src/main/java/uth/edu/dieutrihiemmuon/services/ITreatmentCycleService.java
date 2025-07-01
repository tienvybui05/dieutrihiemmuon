package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.CheckScheduleDTO;
import uth.edu.dieutrihiemmuon.dto.TreatmentCycleDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;

import java.time.LocalDate;
import java.util.List;

public interface ITreatmentCycleService {
    boolean addAppointment(Long serviceId, Long doctorId, LocalDate startDate, Long userId);
    public List<WorkscheduledoctorDTO> getWorkscheduledoctor(long id);
    public boolean updateGeneralNotes(long id ,String notes);
    public List<WorkscheduledoctorDTO> getTreatmentScheduleCustomer(long id);
    public String getNameCustomerToTreatmentCycle(long id);
    public boolean updateConfirmationStatus(long id);
    public boolean deleteTreatmentCycle(long id);
    public List<WorkscheduledoctorDTO> getTreatmentCycleToDay(long id);
    public CheckScheduleDTO NumberOfExecutedAndUnexecutedSeriesInTheDay(long id);
}

