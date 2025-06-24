package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.dieutrihiemmuon.models.Doctor;
@Repository
public interface IDoctorRepository  extends JpaRepository<Doctor, Integer> {
}
