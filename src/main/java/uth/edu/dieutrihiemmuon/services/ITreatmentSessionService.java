package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.TreatmentSessionDoctorDTO;
import uth.edu.dieutrihiemmuon.models.TreatmentSession;

import java.util.List;

public interface ITreatmentSessionService {
    public List<TreatmentSessionDoctorDTO> getTreatmentSessions();
}
