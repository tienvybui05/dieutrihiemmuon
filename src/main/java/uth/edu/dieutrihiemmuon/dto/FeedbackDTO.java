package uth.edu.dieutrihiemmuon.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;
import uth.edu.dieutrihiemmuon.models.Feedback;
import uth.edu.dieutrihiemmuon.models.TreatmentCycle;

import java.time.LocalDate;

public class FeedbackDTO {
    private long idFeedback;

    // Các thông tin được truyền từ form
    @NotBlank(message = "Vui lòng nhập nội dung")
    @Size(max = 1000, message = "Nội dung đánh giá không được vượt quá 1000 ký tự")
    private String reviewText;
    @NotNull(message = "Vui lòng đánh giá số sao")
    @Min(1)
    @Max(5)
    private int rating;

    // Gán tự động trong controller/service
    private LocalDate reviewDate;
    private Long treatmentCycleId;


    public FeedbackDTO() {}

    public FeedbackDTO(Feedback feedback) {
        this.idFeedback = feedback.getIdFeedback();
        this.treatmentCycleId = feedback.getTreatmentCycleFeedback().getIdTreatmentCycle();
        this.reviewText = feedback.getReviewText();
        this.rating = feedback.getRating();
        this.reviewDate = feedback.getReviewDate();
    }
    public FeedbackDTO(Long idFeedback,Long treatmentCycleId, String reviewText,Integer rating,LocalDate reviewDate) {
        this.idFeedback = idFeedback;
        this.treatmentCycleId = treatmentCycleId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public long getIdFeedback() {
        return idFeedback;
    }

    public Long getTreatmentCycleId() {return treatmentCycleId; }

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

    public void setTreatmentCycleId(Long treatmentCycleId) { this.treatmentCycleId = treatmentCycleId; }

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
