package uth.edu.dieutrihiemmuon.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;
import uth.edu.dieutrihiemmuon.models.Feedback;

import java.time.LocalDate;

public class FeedbackDTO {
    private long idFeedback;

    // Các thông tin được truyền từ form
    private Long serviceId;
    @NotBlank
    private String reviewText;
    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    // Gán tự động trong controller/service
    private Long userId;
    private LocalDate reviewDate;


    public FeedbackDTO() {}

    public FeedbackDTO(Feedback feedback) {
        this.idFeedback = feedback.getIdFeedback();
        this.serviceId = feedback.getServiceFeedback().getIdService();
        this.userId = feedback.getUserFeedback().getIdUser();
        this.reviewText = feedback.getReviewText();
        this.rating = feedback.getRating();
        this.reviewDate = feedback.getReviewDate();
    }
    public FeedbackDTO(Long idFeedback,Long serviceId,Long userID, String reviewText,Integer rating,LocalDate reviewDate) {
        this.idFeedback = idFeedback;
        this.serviceId = serviceId;
        this.userId = userID;
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public long getIdFeedback() {
        return idFeedback;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setIdFeedback(long idFeedback) {
        this.idFeedback = idFeedback;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }


}
