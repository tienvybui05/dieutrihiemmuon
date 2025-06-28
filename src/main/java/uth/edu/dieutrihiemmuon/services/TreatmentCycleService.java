package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.TreatmentCycleDTO;
import uth.edu.dieutrihiemmuon.models.*;
import uth.edu.dieutrihiemmuon.repositories.ITreatmentCycleRepository;

import java.time.LocalDate;

@Service
public class TreatmentCycleService implements  ITreatmentCycleService {
    @Autowired
    private ITreatmentCycleRepository treatmentCycleRepository;

    // implementation
    @Override
    public boolean addAppointment(Long serviceId, Long doctorId, LocalDate startDate, Long userId) {
        try {
            // ✅ Tạo DTO và gán tất cả dữ liệu tại đây
            TreatmentCycleDTO dto = new TreatmentCycleDTO();
            dto.setServiceId(serviceId);
            dto.setDoctorId(doctorId);
            dto.setStartDate(startDate);
            dto.setUserId(userId);

            dto.setServiceBookingDate(LocalDate.now());
            dto.setExecutionStatus("Chưa thực hiện");
            dto.setPaymentStatus("Chưa thanh toán");
            dto.setConfirmationStatus("Đang chờ xác nhận");
            dto.setGeneralNotes(null);

            // ✅ Dưới đây giữ nguyên
            TreatmentCycle treatmentCycle = new TreatmentCycle();

            treatmentCycle.setStartDate(dto.getStartDate());
            treatmentCycle.setServiceBookingDate(dto.getServiceBookingDate());
            treatmentCycle.setExecutionStatus(dto.getExecutionStatus());
            treatmentCycle.setPaymentStatus(dto.getPaymentStatus());
            treatmentCycle.setConfirmationStatus(dto.getConfirmationStatus());
            treatmentCycle.setGeneralNotes(dto.getGeneralNotes());

            Doctor doctor = new Doctor();
            doctor.setIdDoctor(dto.getDoctorId());
            treatmentCycle.setDoctorTreatmentCycle(doctor);

            ServicePackage service = new ServicePackage();
            service.setIdService(dto.getServiceId());
            treatmentCycle.setServiceTreatmentCycle(service);

            User user = new User();
            user.setIdUser(dto.getUserId());
            treatmentCycle.setUserTreatmentCycle(user);

            TreatmentSession session = new TreatmentSession();
            session.setTreatmentTime(1);
            session.setTreatmentStatus("Chưa hoàn thành");
            session.setNote(null);
            session.setTreatmentDay(null);
            session.setTreatmentCycle(treatmentCycle);

            treatmentCycle.getTreatmentSessions().add(session);

            treatmentCycleRepository.save(treatmentCycle);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
