package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.CheckScheduleDTO;
import uth.edu.dieutrihiemmuon.dto.TreatmentCycleDTO;

import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
import uth.edu.dieutrihiemmuon.models.*;
import uth.edu.dieutrihiemmuon.repositories.IServicePackageRepository;
import uth.edu.dieutrihiemmuon.repositories.ITreatmentCycleRepository;
import uth.edu.dieutrihiemmuon.repositories.ITreatmentSessionRepository;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TreatmentCycleService implements  ITreatmentCycleService {
    @Autowired
    private ITreatmentCycleRepository treatmentCycleRepository;

    @Autowired
    private IServicePackageRepository servicePackageRepository;

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ITreatmentSessionRepository sessionRepository;
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
            dto.setPaymentStatus("Đã thanh toán");
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

            // lay so buoi cua dich vu
            ServicePackage servicePackage = servicePackageRepository.findById(dto.getServiceId()).orElse(null);
            if (service == null) return false;

            int numberOfSessions = servicePackage.getNumberOfTreatmentSessions();

            // ✅ Tạo n TreatmentSession
            for (int i = 1; i <= numberOfSessions; i++) {
                TreatmentSession session = new TreatmentSession();
                if(i==1)
                {
                    session.setTreatmentDay(treatmentCycle.getStartDate());
                }
                else{
                    session.setTreatmentDay(null);
                }
                session.setTreatmentTime(i);
                session.setTreatmentStatus("Chưa thực hiện");
                session.setNote(null);
                session.setTreatmentCycle(treatmentCycle);

                treatmentCycle.getTreatmentSessions().add(session);
            }

            treatmentCycleRepository.save(treatmentCycle);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<WorkscheduledoctorDTO> getWorkscheduledoctor(long id) {
        try{

            List<TreatmentCycle> treatmentCycles = treatmentCycleRepository.findByDoctorTreatmentCycle_IdDoctor(id);
            List<WorkscheduledoctorDTO> wsd = new ArrayList<WorkscheduledoctorDTO>();
            for(TreatmentCycle treatmentCycle : treatmentCycles) {
                WorkscheduledoctorDTO workscheduledoctorDTO = new WorkscheduledoctorDTO(treatmentCycle);
                wsd.add(workscheduledoctorDTO);
            }
            return  wsd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateGeneralNotes(long id, String notes) {
        try {
            TreatmentCycle treatmentCycle = treatmentCycleRepository.findById(id).orElse(null);
            treatmentCycle.setGeneralNotes(notes);
            treatmentCycleRepository.save(treatmentCycle);
            return true;
        }
       catch (Exception e) {
           System.out.println("Lỗi khi ghi chú" + e);
           return false;
       }
    }
   public List<WorkscheduledoctorDTO> getTreatmentScheduleCustomer(long id){
       try{
           List<TreatmentCycle>  treatmentCycles= treatmentCycleRepository.findByUserTreatmentCycle_idUser(id);
           List<WorkscheduledoctorDTO> workscheduledoctorDTOS = new ArrayList<WorkscheduledoctorDTO>();
           for(TreatmentCycle treatmentCycle : treatmentCycles) {
               WorkscheduledoctorDTO wsd = new WorkscheduledoctorDTO(treatmentCycle);
               workscheduledoctorDTOS.add(wsd);
           }

           return workscheduledoctorDTOS;
       } catch (Exception e) {
           throw new RuntimeException("Lỗi khi lấy danh sách đi khám" + e);
       }
   }

    @Override
    public String getNameCustomerToTreatmentCycle(long id) {
        TreatmentCycle treatmentCycle = treatmentCycleRepository.findById(id).orElse(null);
        return treatmentCycle.getUserTreatmentCycle().getFullName();
    }

    @Override
    public boolean updateConfirmationStatus(long id) {
        try {
            TreatmentCycle treatmentCycle = treatmentCycleRepository.findById(id).orElse(null);
            treatmentCycle.setConfirmationStatus("Đã xác nhận");
//            treatmentCycle.setExecutionStatus("Đang thực hiện");
            treatmentCycleRepository.save(treatmentCycle);
            return true;
        }
        catch (Exception e) {
            System.out.println("Lỗi" + e);
            return false;
        }
    }

    @Override
    public boolean deleteTreatmentCycle(long id) {
        try {
            TreatmentCycle treatmentCycle = treatmentCycleRepository.findById(id).orElse(null);
            treatmentCycleRepository.delete(treatmentCycle);
            return true;
        }
        catch (Exception e) {
            System.out.println("Hủy lịch không thành công"+e);
            return false;
        }
    }
    @Override
    public List<WorkscheduledoctorDTO> getTreatmentCycleToDay(long idDoctor) {
        LocalDate today = LocalDate.now();

        List<TreatmentCycle> treatmentCycles = treatmentCycleRepository.findByDoctorTreatmentCycle_IdDoctor(idDoctor);
        List<WorkscheduledoctorDTO> wsd = new ArrayList<>();

        for (TreatmentCycle treatmentCycle : treatmentCycles) {
            List<TreatmentSession> treatmentSessions =
                    sessionRepository.findByTreatmentCycle_idTreatmentCycle(treatmentCycle.getIdTreatmentCycle());

            boolean hasTodaySession = false;

            for (TreatmentSession session : treatmentSessions) {
                if (session.getTreatmentDay() != null
                        && session.getTreatmentDay().equals(today)) {
                    hasTodaySession = true;
                    break;
                }
            }

            if (hasTodaySession) {
                wsd.add(new WorkscheduledoctorDTO(treatmentCycle));
            }
        }

        return wsd;
    }

    @Override
    public CheckScheduleDTO NumberOfExecutedAndUnexecutedSeriesInTheDay(long id) {
        int daThucHien = 0;
        int lichTrongNgay = 0;
        LocalDate today = LocalDate.now();

        List<TreatmentCycle> treatmentCycles = treatmentCycleRepository.findByDoctorTreatmentCycle_IdDoctor(id);
        for (TreatmentCycle treatmentCycle : treatmentCycles) {
            List<TreatmentSession> treatmentSessions =
                    sessionRepository.findByTreatmentCycle_idTreatmentCycle(treatmentCycle.getIdTreatmentCycle());
            for (TreatmentSession session : treatmentSessions) {
                if (session.getTreatmentDay() != null && session.getTreatmentDay().equals(today)) {
                    lichTrongNgay++;
                    if ("Đã thực hiện".equals(session.getTreatmentStatus())) {
                        daThucHien++;
                    }
                }
            }
        }
        CheckScheduleDTO checkScheduleDTO = new CheckScheduleDTO(lichTrongNgay-daThucHien,daThucHien,lichTrongNgay);
        return checkScheduleDTO;
    }

    @Override
    public double revenue() {
        List<TreatmentCycle> treatmentCycles = treatmentCycleRepository.findAll();
        double sum = 0;
        for (TreatmentCycle treatmentCycle : treatmentCycles) {
            if(treatmentCycle.getConfirmationStatus().equals("Đã xác nhận"))
            {
                sum += treatmentCycle.getServiceTreatmentCycle().getPrice();
            }
        }
        return sum;
    }

    @Override
    public long numberOfSchedulesToDayALL() {
        long count = 0;
        LocalDate today = LocalDate.now();
        List<TreatmentCycle> treatmentCycles = treatmentCycleRepository.findAll();
        for (TreatmentCycle treatmentCycle : treatmentCycles) {
            List<TreatmentSession> treatmentSessions = sessionRepository.findByTreatmentCycle_idTreatmentCycle(treatmentCycle.getIdTreatmentCycle());
            for (TreatmentSession session : treatmentSessions) {
                if (session.getTreatmentDay() != null && session.getTreatmentDay().equals(today)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
