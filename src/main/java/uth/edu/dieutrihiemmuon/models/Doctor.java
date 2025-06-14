package uth.edu.dieutrihiemmuon.models;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_user")
    private User user;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<TreatmentCycle> treatmentCycles = new HashSet<TreatmentCycle>();

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<TreatmentCycle> getTreatmentCycles() {
        return treatmentCycles;
    }

    public void setTreatmentCycles(Set<TreatmentCycle> treatmentCycles) {
        this.treatmentCycles = treatmentCycles;
    }
    public void addTreatmentCycle(TreatmentCycle treatmentCycle) {
        this.treatmentCycles.add(treatmentCycle);
        treatmentCycle.setDoctor(this);
    }
    public void removeTreatmentCycle(TreatmentCycle treatmentCycle) {
        this.treatmentCycles.remove(treatmentCycle);
        treatmentCycle.setDoctor(null);
    }
}
