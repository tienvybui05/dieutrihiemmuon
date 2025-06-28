package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.dieutrihiemmuon.models.Doctor;

import java.util.List;

@Repository
public interface IDoctorRepository  extends JpaRepository<Doctor, Long> {
    public Doctor findById(long id);
    List<Doctor> findByServicePackage_IdService(Long idService);
}
