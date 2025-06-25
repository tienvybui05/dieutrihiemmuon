package uth.edu.dieutrihiemmuon.dto;

import jakarta.persistence.Column;
import org.springframework.web.multipart.MultipartFile;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.User;

public class DoctorDTO {
    private long id_doctor;
    private String fullName;
    private String userName;
    private String passWord;
    private String dateOfBirth;
    private String email;
    private String image = "default.jpg";
    private String phoneNumber;
    private String address;
    private String gender;
    private String role = "DOCTOR";
    private String degree;
    private String experience;
    private String expertise;
    private MultipartFile imageFile;

    public DoctorDTO() {

    }
    public DoctorDTO(Doctor doctor) {
        User user = doctor.getUser();
        this.id_doctor = doctor.getIdDoctor();
        this.fullName = user.getFullName();
        this.userName = user.getUserName();
        this.passWord = user.getPassWord();
        this.dateOfBirth = user.getDateOfBirth();
        this.email = user.getEmail();
        this.image = user.getImage();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.role = user.getRole();
        this.degree = doctor.getDegree();
        this.experience = doctor.getExperience();
        this.expertise = doctor.getExpertise();
    }
    public DoctorDTO(String fullName, String userName, String passWord, String dateOfBirth, String email, String image, String phoneNumber, String address, String gender, String role, String degree, String experience, String expertise) {
        this.fullName = fullName;
        this.userName = userName;
        this.passWord = passWord;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.role = role;
        this.degree = degree;
        this.experience = experience;
        this.expertise = expertise;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public long getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(long id_doctor) {
        this.id_doctor = id_doctor;
    }
}
