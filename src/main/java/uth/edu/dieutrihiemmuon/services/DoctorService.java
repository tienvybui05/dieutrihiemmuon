package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IDoctorRepository;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class DoctorService implements IDoctorService{
    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<DoctorDTO> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorDTO> doctorDTOs = new ArrayList<DoctorDTO>();
        for(Doctor doctor : doctors)
        {
            doctorDTOs.add(new DoctorDTO(doctor));
        }
        return doctorDTOs;
    }

    @Override
    public DoctorDTO getDoctor(long id) {
       try {
           Doctor doctor =  doctorRepository.findById(id);
           if(doctor != null)
           {
               DoctorDTO doctorDTO = new DoctorDTO(doctor);
               return doctorDTO;
           }else {
               return null;
           }
       }catch (Exception e) {
           System.out.println("Lỗi khi tìm kiếm doctor");
           return null;

       }
    }

    @Override
    public boolean addDoctor(DoctorDTO doctorDTO) {

        try {
            if(!doctorDTO.getImage().isEmpty())
            {
                MultipartFile file = doctorDTO.getImageFile();
                Date date = new Date();
                String urlFile = date.getTime()+"_"+file.getOriginalFilename();
                doctorDTO.setImage(urlFile);
                try{
                    String staticDir = new File("dieutrihiemmuon/src/main/resources/static").getAbsolutePath();
                    String uploadDir =  staticDir + "/admin/images/faces";
                    Path path = Paths.get(uploadDir);
                    if(!Files.exists(path))
                    {

                        Files.createDirectories(path);
                    }
                    try( InputStream inputStream = file.getInputStream() ) {
                        Files.copy(inputStream, Paths.get(uploadDir , urlFile), StandardCopyOption.REPLACE_EXISTING);

                    }

                } catch (IOException e) {
                    // Bắt đúng IOException
                    e.printStackTrace();
                    throw new RuntimeException("Lỗi khi lưu ảnh", e);
                }
            }

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
    public Doctor deleteDoctor(long id) {
        return null;
    }

    @Override
    public List<Doctor> searchDoctor(String keyword) {
        return List.of();
    }
}
