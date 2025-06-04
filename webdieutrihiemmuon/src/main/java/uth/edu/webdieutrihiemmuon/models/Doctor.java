package uth.edu.webdieutrihiemmuon.models;
import jakarta.persistence.*;

@Entity
@Table(name = "Doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDoctor;

    @Column(unique = true, nullable = false)
    private String degree;

    @Column(unique = true, nullable = false)
    private String experience;

    @Column(unique = true, nullable = false)
    private String expertise;

    public Doctor() {

    }

    public Doctor(String degree, String experience, String expertise) {
        this.degree = degree;
        this.experience = experience;
        this.expertise = expertise;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
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



}
