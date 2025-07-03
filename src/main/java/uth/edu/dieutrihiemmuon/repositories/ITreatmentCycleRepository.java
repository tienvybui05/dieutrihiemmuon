package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import uth.edu.dieutrihiemmuon.models.TreatmentCycle;
import uth.edu.dieutrihiemmuon.models.TreatmentSession;

import java.util.List;

public interface ITreatmentCycleRepository extends JpaRepository<TreatmentCycle,Long> {
    public TreatmentCycle findById(long id);
    List<TreatmentCycle> findByDoctorTreatmentCycle_IdDoctor(Long idDoctor);
    List<TreatmentCycle> findByUserTreatmentCycle_idUser(long idUser);
}
