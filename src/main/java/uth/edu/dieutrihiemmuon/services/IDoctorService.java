package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.models.Doctor;

import java.util.List;

public interface IDoctorService {
    public  List<DoctorDTO> getDoctors();
    public  DoctorDTO getDoctor(long id);
    public  boolean addDoctor(DoctorDTO doctorDTO);
    public  Doctor updateDoctor(Doctor doctor);
    public  Doctor deleteDoctor(long id);
    public  List<Doctor> searchDoctor(String keyword);
}
