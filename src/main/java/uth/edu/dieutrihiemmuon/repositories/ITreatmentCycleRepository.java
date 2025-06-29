package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.dieutrihiemmuon.models.TreatmentCycle;
import uth.edu.dieutrihiemmuon.models.TreatmentSession;

import java.util.List;

public interface ITreatmentCycleRepository extends JpaRepository<TreatmentCycle,Long> {
    List<TreatmentCycle> findByDoctorTreatmentCycle_IdDoctor(Long idDoctor);
}
