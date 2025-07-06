package uth.edu.dieutrihiemmuon.dto;

import jakarta.persistence.Column;
import uth.edu.dieutrihiemmuon.models.*;

import java.time.LocalDate;

public class FeedbackInformationDTO {
    private long idFeedback;
    private long idCustomer;
    private long idDoctor;
    private long idService;
    private String NameDoctor;
    private String NameCustomer;
    private String NameService;
    private String reviewText;
    private Integer rating;
    private LocalDate reviewDate;

    public FeedbackInformationDTO() {
    }

    public FeedbackInformationDTO(Feedback feedback) {
//        Doctor doctor=  treatmentCycle.getDoctorTreatmentCycle();
//        User doctorUser = doctor.getUser();
//        ServicePackage servicePackage =  treatmentCycle.getServiceTreatmentCycle();
//        User user =  treatmentCycle.getUserTreatmentCycle();
        User user = feedback.getUserFeedback();
        Doctor userDoctor = user.getDoctor();
        ServicePackage servicePackage = feedback.getServiceFeedback();
        this.idFeedback = feedback.getIdFeedback();
        this.idCustomer = user.getIdUser();
//        this.idDoctor = doctor.getIdDoctor();
//        this.idSchedule = treatmentCycle.getIdTreatmentCycle();
//        this.NameDoctor = doctorUser.getFullName();
        this.NameCustomer = user.getFullName();
        this.NameService = servicePackage.getServiceName();
        this.reviewText = feedback.getReviewText();
        this.reviewDate = feedback.getReviewDate();
        this.rating = feedback.getRating();
    }

    public long getIdFeedback() {
        return idFeedback;
    }

    public long getIdCustomer() {
        return idCustomer;
    }

    public long getIdDoctor() {
        return idDoctor;
    }

    public long getIdService() {
        return idService;
    }

    public String getNameDoctor() {
        return NameDoctor;
    }

    public String getNameCustomer() {
        return NameCustomer;
    }

    public String getNameService() {
        return NameService;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Integer getRating() {
        return rating;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setIdFeedback(long idFeedback) {
        this.idFeedback = idFeedback;
    }

    public void setIdCustomer(long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setIdDoctor(long idDoctor) {
        this.idDoctor = idDoctor;
    }

    public void setIdService(long idService) {
        this.idService = idService;
    }

    public void setNameDoctor(String nameDoctor) {
        NameDoctor = nameDoctor;
    }

    public void setNameCustomer(String nameCustomer) {
        NameCustomer = nameCustomer;
    }

    public void setNameService(String nameService) {
        NameService = nameService;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}