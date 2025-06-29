package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.dieutrihiemmuon.models.TreatmentCycle;

public interface ITreatmentCycleRepository extends JpaRepository<TreatmentCycle,Long> {
}
