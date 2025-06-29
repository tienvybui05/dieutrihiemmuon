package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.dieutrihiemmuon.models.Feedback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback, Long>
{
    public Feedback findById(long id);
    Optional<Feedback> findByReviewDate(LocalDate reviewDate);
    List<Feedback> findByRating(Integer rating);
    List<Feedback> findByReviewDateAndRating(LocalDate reviewDate, Integer rating);
}
