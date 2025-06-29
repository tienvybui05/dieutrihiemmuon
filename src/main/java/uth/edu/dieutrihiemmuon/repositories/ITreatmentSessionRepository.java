package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.dieutrihiemmuon.models.TreatmentSession;

public interface ITreatmentSessionRepository extends JpaRepository<TreatmentSession, Long> {
}
