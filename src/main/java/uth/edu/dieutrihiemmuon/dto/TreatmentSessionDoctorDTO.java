package uth.edu.dieutrihiemmuon.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class TreatmentSessionDoctorDTO {

    private long idTreatmentTimes;

    private int treatmentTime;

    private LocalDate treatmentDay;

    private String note;

    private String treatmentStatus;


    public long getIdTreatmentTimes() {
        return idTreatmentTimes;
    }

    public void setIdTreatmentTimes(long idTreatmentTimes) {
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
}
