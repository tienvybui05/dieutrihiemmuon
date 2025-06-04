package uth.edu.webdieutrihiemmuon.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name ="treatment_cycle")
public class TreatmentCycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTreatmentCycle;

    @Column(nullable = false)
    private LocalDate serviceBookingDate;

    @Column(nullable = false)
    private String executionStatus;

    @Column(nullable = false)
    private String paymentStatus;

    @Column(nullable = false)
    private String confirmationStatus;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(length = 200)
    private String generalNotes;

    public TreatmentCycle() {
    }

    public TreatmentCycle(String generalNotes, LocalDate startDate, String confirmationStatus, String paymentStatus, String executionStatus, LocalDate serviceBookingDate) {
        this.generalNotes = generalNotes;
        this.startDate = startDate;
        this.confirmationStatus = confirmationStatus;
        this.paymentStatus = paymentStatus;
        this.executionStatus = executionStatus;
        this.serviceBookingDate = serviceBookingDate;
    }

    public Long getIdTreatmentCycle() {
        return idTreatmentCycle;
    }

    public void setIdTreatmentCycle(Long idTreatmentCycle) {
        this.idTreatmentCycle = idTreatmentCycle;
    }

    public LocalDate getServiceBookingDate() {
        return serviceBookingDate;
    }

    public void setServiceBookingDate(LocalDate serviceBookingDate) {
        this.serviceBookingDate = serviceBookingDate;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(String generalNotes) {
        this.generalNotes = generalNotes;
    }


}
