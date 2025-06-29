package uth.edu.dieutrihiemmuon.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.FeedbackDTO;
import uth.edu.dieutrihiemmuon.models.Feedback;
import uth.edu.dieutrihiemmuon.repositories.IFeedbackRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class FeedbackService implements IFeedbackService {
    @Autowired
    private IFeedbackRepository feedbackRepository;

    @Override
    public List<FeedbackDTO> getFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<FeedbackDTO> feedbackDTOS = new ArrayList<FeedbackDTO>();
        for (Feedback feedback : feedbacks) {
            feedbackDTOS.add(new FeedbackDTO(feedback));
        }
        return feedbackDTOS;
    }

    @Override
    public FeedbackDTO getFeedback(long id) {
        try {
            Feedback feedback = feedbackRepository.findById(id);
            if (feedback != null) {
                FeedbackDTO feedbackDTO = new FeedbackDTO(feedback);
                return feedbackDTO;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm đánh giá");
            return null;
        }
    }

    @Override
    public boolean addFeedback(FeedbackDTO feedbackDTO) {
        try {
            Feedback feedback = new Feedback();
            feedback.setReviewText(feedbackDTO.getReviewText());
            feedback.setReviewDate(feedbackDTO.getReviewDate());
            feedback.setRating(feedbackDTO.getRating());

            feedbackRepository.save(feedback);

            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Lỗi khi thêm đánh giá: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean updateFeedback(FeedbackDTO feedbackDTO) {
        try {
            Feedback feedback = feedbackRepository.findById(feedbackDTO.getIdFeedback());
            if (feedback == null) {
                return false;
            }
            feedback.setReviewText(feedbackDTO.getReviewText());
            feedback.setReviewDate(feedbackDTO.getReviewDate());
            feedback.setRating(feedbackDTO.getRating());

            if (feedbackRepository.save(feedback) != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật đánh giá" + e);
        }
    }

    @Override
    public boolean deleteFeedback(long id) {
        try {
            if (feedbackRepository.existsById(id)) {
                feedbackRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa đánh giá", e);
        }
    }

    @Override
    public List<FeedbackDTO> findByRating(Integer rating) {
        List<Feedback> feedbacks = feedbackRepository.findByRating(rating);
        List<FeedbackDTO> feedbackDTOS = new ArrayList<FeedbackDTO>();
        for (Feedback feedback : feedbacks) {
            feedbackDTOS.add(new FeedbackDTO(feedback));
        }
        return feedbackDTOS;
    }

    @Override
    public List<FeedbackDTO> findByReviewDate(LocalDate reviewDate) {
        List<Feedback> feedbacks = feedbackRepository.findByReviewDate(reviewDate);
        List<FeedbackDTO> feedbackDTOS = new ArrayList<FeedbackDTO>();
        for (Feedback feedback : feedbacks) {
            feedbackDTOS.add(new FeedbackDTO(feedback));
        }
        return feedbackDTOS;
    }

    @Override
    public List<FeedbackDTO> findByReviewDateAndRating(LocalDate reviewDate, Integer rating) {
        List<Feedback> feedbacks = feedbackRepository.findByReviewDateAndRating(reviewDate, rating);
        List<FeedbackDTO> feedbacksDTOS = new ArrayList<FeedbackDTO>();
        for (Feedback feedback : feedbacks) {
            feedbacksDTOS.add(new FeedbackDTO(feedback));
        }
        return feedbacksDTOS;
    }
}

