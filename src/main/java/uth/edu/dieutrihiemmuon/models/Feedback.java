package uth.edu.dieutrihiemmuon.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name ="feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFeedback;

    @Column(nullable = false, length = 1000)
    private String reviewText;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 10)
    private LocalDate reviewDate;

    @ManyToOne
    @JoinColumn(name = "id_treatmentCycle",nullable = true)
    private TreatmentCycle treatmentCycleFeedback;


    public Feedback() {
    }

    public Feedback( int rating, String reviewText, LocalDate reviewDate) {
        this.rating = rating;
        this.reviewText = reviewText;
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


    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public TreatmentCycle getTreatmentCycleFeedback() {
        return treatmentCycleFeedback;
    }

    public void setTreatmentCycleFeedback(TreatmentCycle treatmentCycleFeedback) { this.treatmentCycleFeedback = treatmentCycleFeedback; }
}

