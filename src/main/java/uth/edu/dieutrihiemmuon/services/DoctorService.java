package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IDoctorRepository;
import uth.edu.dieutrihiemmuon.repositories.IServicePackageRepository;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

import javax.print.Doc;
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
import java.util.stream.Collectors;

@Service
public class DoctorService implements IDoctorService{
    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IServicePackageRepository servicePackageRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
            if(!doctorDTO.getImageFile().isEmpty())
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
            ServicePackage servicePackage = servicePackageRepository.findById(doctorDTO.getIdService());
            user.setFullName(doctorDTO.getFullName());
            user.setEmail(doctorDTO.getEmail());
            user.setAddress(doctorDTO.getAddress());
            user.setDateOfBirth(doctorDTO.getDateOfBirth());
            user.setGender(doctorDTO.getGender());
            user.setPhoneNumber(doctorDTO.getPhoneNumber());
            user.setUserName(doctorDTO.getUserName());
            user.setPassWord(passwordEncoder.encode(doctorDTO.getPassWord()));
            user.setRole(doctorDTO.getRole());
            user.setImage(doctorDTO.getImage());
            if(userRepository.save(user)!=null) {
                doctor.setDegree(doctorDTO.getDegree());
                doctor.setExperience(doctorDTO.getExperience());
                doctor.setExpertise(doctorDTO.getExpertise());
                doctor.setUser(user);
                doctor.setServicePackage(servicePackage);
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
            System.out.println("Lỗi khi thêm doctor"+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateDoctor(DoctorDTO doctorDTO) {
        try{

            try {
                MultipartFile file = doctorDTO.getImageFile();
                DoctorDTO doctorFromDB = new DoctorDTO(doctorRepository.findById(doctorDTO.getId_doctor()));
                String oldFileName = doctorFromDB.getImage(); // có thể là "default.jpg" hoặc ảnh cũ

                String staticDir = new File("dieutrihiemmuon/src/main/resources/static").getAbsolutePath();
                String uploadDir = staticDir + "/admin/images/faces";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                if (file != null && !file.isEmpty()) {
                    // Xóa ảnh cũ nếu khác "default.jpg"
                    if (oldFileName != null && !oldFileName.equals("default.jpg")) {
                        Path oldFilePath = uploadPath.resolve(oldFileName);
                        if (Files.exists(oldFilePath)) {
                            Files.delete(oldFilePath);
                        }
                    }

                    // Lưu ảnh mới
                    String originalFileName = file.getOriginalFilename();
                    if (originalFileName == null || originalFileName.isBlank()) {
                        originalFileName = "image.jpg";
                    }
                    String newFileName = System.currentTimeMillis() + "_" + originalFileName;
                    try (InputStream inputStream = file.getInputStream()) {
                        Files.copy(inputStream, uploadPath.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
                    }

                    doctorDTO.setImage(newFileName); // cập nhật tên ảnh mới
                } else {
                    // Không có ảnh mới => giữ nguyên ảnh cũ
                    doctorDTO.setImage(oldFileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi xử lý ảnh", e);
            }
            Doctor doctor =  doctorRepository.findById(doctorDTO.getId_doctor());
            ServicePackage servicePackage = servicePackageRepository.findById(doctorDTO.getIdService());
            if(doctor == null)
            {
                return false;
            }
            User user = doctor.getUser();
            user.setFullName(doctorDTO.getFullName());
            user.setEmail(doctorDTO.getEmail());
            user.setAddress(doctorDTO.getAddress());
            user.setDateOfBirth(doctorDTO.getDateOfBirth());
            user.setGender(doctorDTO.getGender());
            user.setPhoneNumber(doctorDTO.getPhoneNumber());
            user.setUserName(doctorDTO.getUserName());
            user.setPassWord(passwordEncoder.encode(doctorDTO.getPassWord()));
            user.setImage(doctorDTO.getImage());
            doctor.setDegree(doctorDTO.getDegree());
            doctor.setExperience(doctorDTO.getExperience());
            doctor.setExpertise(doctorDTO.getExpertise());
            doctor.setUser(user);
            doctor.setServicePackage(servicePackage);
            if(doctorRepository.save(doctor)!=null) {
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật thông in doctor"+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteDoctor(long id) {
        try {
            Doctor doctor = doctorRepository.findById(id);
            if (doctor != null) {
                doctor = doctorRepository.findById(id);
                DoctorDTO doctorDTO = new DoctorDTO(doctor);
                if (doctor != null) {
                    // Xóa ảnh nếu cần
                    String imageFileName = doctorDTO.getImage(); // ảnh trong DB
                    if (imageFileName != null && !imageFileName.equals("default.jpg")) {
                        String staticDir = new File("dieutrihiemmuon/src/main/resources/static").getAbsolutePath();
                        String uploadDir = staticDir + "/admin/images/faces";
                        Path imagePath = Paths.get(uploadDir, imageFileName);
                        if (Files.exists(imagePath)) {
                            Files.delete(imagePath); // xóa file ảnh thật
                        }
                    }
                    // Xóa khỏi DB
                    User user = doctor.getUser();
                    userRepository.delete(user);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa ảnh bác sĩ");
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi xóa bác sĩ khỏi hệ thống"+e.getMessage());
            return false;
        }
    }

    @Override
    public List<Doctor> searchDoctor(String keyword) {
        return List.of();
    }

    @Override
    public DoctorDTO findByUsername(String username) {
        try{
            User user = userRepository.findByUserName(username);
            if(user==null)
            {
                return null;
            }
            DoctorDTO doctorDTO = new DoctorDTO(user.getDoctor());
            return doctorDTO;
        }catch (RuntimeException e) {
           System.out.println("Lỗi khi tìm doctor"+e.getMessage());
           return null;
        }

    }

    @Override
    public DoctorDTO findByEmail(String email) {
        try{
            User user = userRepository.findByEmail(email);
            if(user==null)
            {
                return null;
            }
            DoctorDTO doctorDTO = new DoctorDTO(user.getDoctor());
            return doctorDTO;
        }
        catch (RuntimeException e) {
            System.out.println("Lỗi khi tìm kiếm bằng email"+e.getMessage());
            return null;
        }

    }

    @Override
    public DoctorDTO findByPhoneNumber(String phoneNumber) {
        try{
            User user = userRepository.findByPhoneNumber(phoneNumber);
            if(user==null)
            {
                return null;
            }
            DoctorDTO doctorDTO = new DoctorDTO(user.getDoctor());
            return doctorDTO;
        }
        catch (RuntimeException e) {
            System.out.println("Lỗi khi tìm kiếm bằng PhoneNumber"+e.getMessage());
            return null;
        }
    }

    @Override
    public List<DoctorDTO> getDoctorsByServiceId(long id) {
        List<Doctor> doctors = doctorRepository.findByServicePackage_IdService(id);
        return doctors.stream().map(DoctorDTO::new).collect(Collectors.toList());
    }

    @Override
    public long countDoctors() {
        long count = doctorRepository.count();
        return count;
    }
}
