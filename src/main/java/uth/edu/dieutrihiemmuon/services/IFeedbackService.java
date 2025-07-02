package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.models.Feedback;
import uth.edu.dieutrihiemmuon.dto.FeedbackDTO;
import uth.edu.dieutrihiemmuon.models.ServicePackage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IFeedbackService {
    public List<FeedbackDTO> getFeedbacks();
    public  FeedbackDTO getFeedback(long id);
    public  boolean addFeedback(Long serviceId,Long userId, Long treatmentCycleId, String reviewText,Integer rating);
    public  boolean updateFeedback(FeedbackDTO feedbackDTO);
    public  boolean deleteFeedback(long id);
    public  List<FeedbackDTO> findByReviewDate(LocalDate reviewDate);
    public  List<FeedbackDTO> findByRating(Integer rating);
    public  List<FeedbackDTO> findByReviewDateAndRating(LocalDate reviewDate, Integer rating);
}
