package uth.edu.webdieutrihiemmuon.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name ="feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFeedback;

    @Column(nullable = false)
    private String reviewText;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 10)
    private LocalDate reviewDate;

    public Feedback() {
    }

    public Feedback(Long idFeedback, Integer rating, String reviewText, LocalDate reviewDate) {
        this.idFeedback = idFeedback;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

    public Long getIdFeedback() {
        return idFeedback;
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

    public void setIdFeedback(Long idFeedback) {
        this.idFeedback = idFeedback;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
