package uth.edu.dieutrihiemmuon.models;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    @Column(unique = true, nullable = false, length = 50)
    private String fullName;
    @Column(unique = true, nullable = false, length = 50)
    private String userName;
    @Column(nullable = false, length = 100)
    private String passWord;
    @Column(nullable = false, length = 10)
    private String dateOfBirth;
    @Column(unique = true, nullable = false, length = 50)
    private String email;
    @Column(unique = true, nullable = false, length = 15)
    private String phoneNumber;
    @Column(nullable = false, length = 100)
    private String address;
    @Column(nullable = false, length = 10)
    private String gender;
    @Column(nullable = false, length = 20)
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Doctor> doctors = new HashSet<Doctor>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<TreatmentCycle> treatmentCycles = new HashSet<TreatmentCycle>();

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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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

    public Set<Doctor> getDoctor() {
        return doctors;
    }
    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }
    public void addDoctor(Doctor doctor){
        this.doctors.add(doctor);
        doctor.setUser(this);
    }
    public void removeDoctor(Doctor doctor){
        this.doctors.remove(doctor);
        doctor.setUser(null);
    }
    public void addTreatmentCycle(TreatmentCycle treatmentCycle){
        this.treatmentCycles.add(treatmentCycle);
        treatmentCycle.setUser(this);
    }
    public void removeTreatmentCycle(TreatmentCycle treatmentCycle){
        this.treatmentCycles.remove(treatmentCycle);
        treatmentCycle.setUser(null);
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }


    public Set<TreatmentCycle> getTreatmentCycles() {
        return treatmentCycles;
    }

    public void setTreatmentCycles(Set<TreatmentCycle> treatmentCycles) {
        this.treatmentCycles = treatmentCycles;
    }
}
