package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.models.Doctor;

import java.util.List;

public interface IDoctorService {
    public  List<DoctorDTO> getDoctors();
    public  DoctorDTO getDoctor(long id);
    public  boolean addDoctor(DoctorDTO doctorDTO);
    public  boolean updateDoctor(DoctorDTO doctorDTO);
    public  boolean deleteDoctor(long id);
    public  List<Doctor> searchDoctor(String keyword);
    public  DoctorDTO findByUsername(String username);
    public  DoctorDTO findByEmail(String email);
    public DoctorDTO  findByPhoneNumber(String phoneNumber);
    public List<DoctorDTO> getDoctorsByServiceId(long id);
}
