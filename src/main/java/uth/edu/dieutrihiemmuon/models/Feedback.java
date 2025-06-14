package uth.edu.dieutrihiemmuon.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name ="feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFeedback;

    @Column(nullable = false)
    private String reviewText;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, length = 10)
    private LocalDate reviewDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_treatmentCycle")
    private TreatmentCycle treatmentCycle;
    public Feedback() {
    }

    public Feedback( int rating, String reviewText, LocalDate reviewDate) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

    public int getIdFeedback() {
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

    public void setIdFeedback(int idFeedback) {
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

    public TreatmentCycle getTreatmentCycle() {
        return treatmentCycle;
    }

    public void setTreatmentCycle(TreatmentCycle treatmentCycle) {
        this.treatmentCycle = treatmentCycle;
    }
}
