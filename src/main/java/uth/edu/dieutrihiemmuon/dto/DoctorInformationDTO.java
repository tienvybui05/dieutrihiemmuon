package uth.edu.dieutrihiemmuon.dto;


import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import uth.edu.dieutrihiemmuon.models.User;

public class DoctorInformationDTO {

    private String fullName;
    private String experience;
    private String image;
    private String nameService;
    public DoctorInformationDTO() {}
    public DoctorInformationDTO(Doctor doctor) {
        User user = doctor.getUser();
        ServicePackage servicePackage = doctor.getServicePackage();
        this.fullName = user.getFullName();
        this.image = user.getImage();
        this.experience = doctor.getExperience();
        this.nameService = servicePackage.getServiceName();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }
}
