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
    private int idTreatmentCycle;

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

    @OneToMany(mappedBy = "treatmentCycle",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TreatmentSession> treatmentSessions = new HashSet<TreatmentSession>();

    @OneToMany(mappedBy = "treatmentCycle" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Feedback> feedbacks = new HashSet<Feedback>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_service")
    private ServicePackage servicePackage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;
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

    public int getIdTreatmentCycle() {
        return idTreatmentCycle;
    }

    public void setIdTreatmentCycle(int idTreatmentCycle) {
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
    public Set<TreatmentSession> getTreatmentSessions() {
        return treatmentSessions;
    }

    public void setTreatmentSessions(Set<TreatmentSession> treatmentSessions) {
        this.treatmentSessions = treatmentSessions;
    }
    public void addTreatmentSession(TreatmentSession treatmentSession) {
        this.treatmentSessions.add(treatmentSession);
        treatmentSession.setTreatmentCycle(this);
    }
    public void removeTreatmentSession(TreatmentSession treatmentSession) {
        this.treatmentSessions.remove(treatmentSession);
        treatmentSession.setTreatmentCycle(null);
    }
    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
        feedback.setTreatmentCycle(this);
    }
    public void removeFeedback(Feedback feedback) {
        this.feedbacks.remove(feedback);
        feedback.setTreatmentCycle(null);
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
