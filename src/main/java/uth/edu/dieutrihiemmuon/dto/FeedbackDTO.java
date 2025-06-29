package uth.edu.dieutrihiemmuon.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;
import uth.edu.dieutrihiemmuon.models.Feedback;

import java.time.LocalDate;

public class FeedbackDTO {
    private long idFeedback;

    @NotBlank
    private String reviewText;
    @NotNull
    @Min(1)
    @Max(5)
    private int rating;
    @NotEmpty
    private LocalDate reviewDate;

    public FeedbackDTO() {}

    public FeedbackDTO(Feedback feedback) {
        this.idFeedback = feedback.getIdFeedback();
        this.reviewText = feedback.getReviewText();
        this.rating = feedback.getRating();
        this.reviewDate = feedback.getReviewDate();
    }

    public FeedbackDTO(long idFeedback, String reviewText, int rating, LocalDate reviewDate) {
        this.idFeedback = idFeedback;
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public long getIdFeedback() {
        return idFeedback;
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
