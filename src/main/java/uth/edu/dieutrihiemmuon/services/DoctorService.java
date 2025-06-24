package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IDoctorRepository;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

import java.util.List;
@Service
public class DoctorService implements IDoctorService{
    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<Doctor> getDoctors() {
        return List.of();
    }

    @Override
    public Doctor getDoctor(int id) {
        return null;
    }

    @Override
    public boolean addDoctor(DoctorDTO doctorDTO) {
        try {
            // kiểm tra email
            // kiểm tra username
            Doctor doctor = new Doctor();
            User user = new User();
            user.setFullName(doctorDTO.getFullName());
            user.setEmail(doctorDTO.getEmail());
            user.setAddress(doctorDTO.getAddress());
            user.setDateOfBirth(doctorDTO.getDateOfBirth());
            user.setGender(doctorDTO.getGender());
            user.setPhoneNumber(doctorDTO.getPhoneNumber());
            user.setUserName(doctorDTO.getUserName());
            user.setPassWord(doctorDTO.getPassWord());
            user.setRole(doctorDTO.getRole());
            user.setImage(doctorDTO.getImage());
            if(userRepository.save(user)!=null) {
                doctor.setDegree(doctorDTO.getDegree());
                doctor.setExperience(doctorDTO.getExperience());
                doctor.setExpertise(doctorDTO.getExpertise());
                doctor.setUser(user);
                if(doctorRepository.save(doctor)!=null) {
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        } catch (RuntimeException e) {
           return false;
//            throw new RuntimeException("Lỗi khi thêm bác sĩ" + e);
        }
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        return null;
    }

    @Override
    public Doctor deleteDoctor(int id) {
        return null;
    }

    @Override
    public List<Doctor> searchDoctor(String keyword) {
        return List.of();
    }
}
