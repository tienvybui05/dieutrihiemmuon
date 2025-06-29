package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.dieutrihiemmuon.models.TreatmentSession;

import java.util.List;

public interface ITreatmentSessionRepository extends JpaRepository<TreatmentSession, Long> {
    List<TreatmentSession> findByTreatmentCycle_idTreatmentCycle(long treatmentCycleId);
}
