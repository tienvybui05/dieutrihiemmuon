package uth.edu.dieutrihiemmuon.services;

import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;

import java.util.List;

@Service
public class TreatmentSessionService implements ITreatmentSessionService {

    @Override
    public List<TreatmentSessionDoctorDTO> getTreatmentSessions() {
        return List.of();
    }
}
