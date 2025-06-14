package uth.edu.dieutrihiemmuon.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "treatment_session")
public class TreatmentSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTreatmentTimes;

    @Column(nullable = false)
    private int treatmentTime;

    private LocalDate treatmentDay;

    @Column(length = 200)
    private String note;

    @Column(nullable = false)
    private String treatmentStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_treatmentcycle")
    private TreatmentCycle treatmentCycle;
    public TreatmentSession() {
    }

    public TreatmentSession(int treatmentTime, LocalDate treatmentDay, String note, String treatmentStatus) {
        this.treatmentTime = treatmentTime;
        this.treatmentDay = treatmentDay;
        this.note = note;
        this.treatmentStatus = treatmentStatus;
    }

    public int getIdTreatmentTimes() {
        return idTreatmentTimes;
    }

    public void setIdTreatmentTimes(int idTreatmentTimes) {
        this.idTreatmentTimes = idTreatmentTimes;
    }

    public int getTreatmentTime() {
        return treatmentTime;
    }

    public void setTreatmentTime(int treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

    public LocalDate getTreatmentDay() {
        return treatmentDay;
    }

    public void setTreatmentDay(LocalDate treatmentDay) {
        this.treatmentDay = treatmentDay;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTreatmentStatus() {
        return treatmentStatus;
    }

    public void setTreatmentStatus(String treatmentStatus) {
        this.treatmentStatus = treatmentStatus;
    }
    public TreatmentCycle getTreatmentCycle() {
        return treatmentCycle;
    }

    public void setTreatmentCycle(TreatmentCycle treatmentCycle) {
        this.treatmentCycle = treatmentCycle;
    }

}
