package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.models.TreatmentCycle;
import uth.edu.dieutrihiemmuon.models.TreatmentSession;
import uth.edu.dieutrihiemmuon.repositories.ITreatmentCycleRepository;
import uth.edu.dieutrihiemmuon.repositories.ITreatmentSessionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TreatmentSessionService implements ITreatmentSessionService {

    @Autowired
    private ITreatmentCycleRepository treatmentCycleRepository;
    @Autowired
    private ITreatmentSessionRepository treatmentSessionRepository;
    @Override
    public List<TreatmentSessionDoctorDTO> getTreatmentSessions(long id) {
        try {

            List<TreatmentSession> treatmentSessions = treatmentSessionRepository.findByTreatmentCycle_idTreatmentCycle(id);
            treatmentSessions.sort(Comparator.comparing(TreatmentSession::getTreatmentTime));
            List<TreatmentSessionDoctorDTO> treatmentSessionDoctorDTOs = new ArrayList<TreatmentSessionDoctorDTO>();
            for (TreatmentSession treatmentSession : treatmentSessions) {
                    TreatmentSessionDoctorDTO treatmentSessionDoctorDTO = new TreatmentSessionDoctorDTO(treatmentSession);
                    treatmentSessionDoctorDTOs.add(treatmentSessionDoctorDTO);
            }

            return treatmentSessionDoctorDTOs;
        }catch (Exception e) {
            System.out.println("Lỗi khi lấy day sách treatmentSession "+e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean updateTreatmentSessionDTO(TreatmentSessionDoctorDTO dto) {
        try {
            TreatmentSession treatmentSession = treatmentSessionRepository.findById(dto.getIdTreatmentTimes()).orElse(null);
            long id = treatmentSession.getTreatmentCycle().getIdTreatmentCycle();
            TreatmentCycle treatmentCycle = treatmentCycleRepository.findById(id).orElse(null);
            if (treatmentSession == null) {
                return false;
            }
            treatmentSession.setTreatmentDay(dto.getTreatmentDay());
            treatmentSession.setNote(dto.getNote());
            treatmentSession.setTreatmentStatus(dto.getTreatmentStatus());
            treatmentSessionRepository.save(treatmentSession);
            List<TreatmentSession> treatmentSessions = treatmentSessionRepository.findByTreatmentCycle_idTreatmentCycle(id);
            boolean check = true;
            for(TreatmentSession treatmentSession2 : treatmentSessions) {
                if(treatmentSession2.getTreatmentStatus().equals("Chưa thực hiện")){
                    check = false;
                }
            }
            if(check) {
                treatmentCycle.setExecutionStatus("Đã thực hiện");
            }
            else {
                treatmentCycle.setExecutionStatus("Chưa thực hiện");
            }
            treatmentCycleRepository.save(treatmentCycle);
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi khi update buổi điều trị"+e);
            return false;
        }
    }
}
