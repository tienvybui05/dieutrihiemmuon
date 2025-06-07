package uth.edu.webdieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.webdieutrihiemmuon.models.Feedback;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Optional<Feedback> findByIdFeedback(Integer idFeedback);
}
