package uth.edu.dieutrihiemmuon.models;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    @NotBlank(message = "Vui lòng nhập tên")
    @Column(nullable = false, length = 50)
    private String fullName;

    @NotBlank(message = "Vui lòng nhập username")
    @Pattern(regexp = "^[^\\s]+$", message = "Vui lòng nhập username")
    @Column(unique = true, nullable = false, length = 50)
    private String userName;

    @NotBlank(message = "Vui lòng nhập passWord")
    @Column(nullable = false, length = 100)
    private String passWord;

    @NotBlank(message = "Vui lòng nhập ngày sinh")
    @Column(nullable = false, length = 10)
    private String dateOfBirth;

    @NotBlank(message = "Vui lòng nhập email")
    @Email(message = "Email không đúng định dạng")
    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = true, length = 250)
    private String image;

    @Pattern(regexp = "\\d{10,11}", message = "Số điện thoại không hợp lệ")
    @NotBlank(message = "Vui lòng nhập số điện thoại")
    @Column(unique = true, nullable = false, length = 15)
    private String phoneNumber;

    @NotBlank(message = "Vui lòng nhập địa chỉ")
    @Column(nullable = false, length = 100)
    private String address;

    @NotBlank(message = "Vui lòng nhập giới tính")
    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false, length = 20)
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Doctor doctor;

    @OneToMany(mappedBy = "userTreatmentCycle",cascade = CascadeType.ALL)
    Set<TreatmentCycle> treatmentCycles = new HashSet<TreatmentCycle>();
    public User() {

    }

    public User( String fullName, String userName, String passWord, String dateOfBirth, String email, String phoneNumber, String address){
        this.fullName = fullName;
        this.userName = userName;
        this.passWord = passWord;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Set<TreatmentCycle> getTreatmentCycles() {
        return treatmentCycles;
    }

    public void setTreatmentCycles(Set<TreatmentCycle> treatmentCycles) {
        this.treatmentCycles = treatmentCycles;
    }
}
