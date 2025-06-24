package uth.edu.dieutrihiemmuon.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="treatment_cycle")
public class TreatmentCycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTreatmentCycle;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User userTreatmentCycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doctor")
    private Doctor doctorTreatmentCycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_service")
    private ServicePackage serviceTreatmentCycle;

    @OneToMany(mappedBy = "treatmentCycle",cascade = CascadeType.ALL)
    Set<TreatmentSession> treatmentSessions = new HashSet<TreatmentSession>();

    @OneToOne(mappedBy = "fb_treatmentCycle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Feedback feedback;

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

    public long getIdTreatmentCycle() {
        return idTreatmentCycle;
    }

    public void setIdTreatmentCycle(long idTreatmentCycle) {
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

    public User getUserTreatmentCycle() {
        return userTreatmentCycle;
    }

    public void setUserTreatmentCycle(User userTreatmentCycle) {
        this.userTreatmentCycle = userTreatmentCycle;
    }

    public Doctor getDoctorTreatmentCycle() {
        return doctorTreatmentCycle;
    }

    public void setDoctorTreatmentCycle(Doctor doctorTreatmentCycle) {
        this.doctorTreatmentCycle = doctorTreatmentCycle;
    }

    public ServicePackage getServiceTreatmentCycle() {
        return serviceTreatmentCycle;
    }

    public void setServiceTreatmentCycle(ServicePackage serviceTreatmentCycle) {
        this.serviceTreatmentCycle = serviceTreatmentCycle;
    }

    public Set<TreatmentSession> getTreatmentSessions() {
        return treatmentSessions;
    }

    public void setTreatmentSessions(Set<TreatmentSession> treatmentSessions) {
        this.treatmentSessions = treatmentSessions;
    }
}
