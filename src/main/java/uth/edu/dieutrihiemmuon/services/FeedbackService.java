package uth.edu.dieutrihiemmuon.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.FeedbackDTO;
import uth.edu.dieutrihiemmuon.dto.FeedbackInformationDTO;
import uth.edu.dieutrihiemmuon.models.*;
import uth.edu.dieutrihiemmuon.repositories.IFeedbackRepository;
import uth.edu.dieutrihiemmuon.repositories.ITreatmentCycleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
public class FeedbackService implements IFeedbackService {
    @Autowired
    private IFeedbackRepository feedbackRepository;
    @Autowired
    private ITreatmentCycleRepository treatmentCycleRepository;

    @Override
    public List<FeedbackInformationDTO> getFeedbackInformationList() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<FeedbackInformationDTO> feedbackInformationDTOS = new ArrayList<FeedbackInformationDTO>();
        for (Feedback feedback : feedbacks) {
            feedbackInformationDTOS.add(new FeedbackInformationDTO(feedback));
        }
        return feedbackInformationDTOS;
    }

    @Override
    public List<FeedbackDTO> getFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<FeedbackDTO> feedbackDTOS = new ArrayList<FeedbackDTO>();
        for (Feedback feedback : feedbacks) {
            feedbackDTOS.add(new FeedbackDTO(feedback));
        }
        return feedbackDTOS;
    }

    public FeedbackInformationDTO getFeedbackInformation(long id) {
        try {
            Feedback feedback = feedbackRepository.findById(id);
            if (feedback != null) {
                FeedbackInformationDTO feedbackInformationDTO = new FeedbackInformationDTO(feedback);
                return feedbackInformationDTO;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm đánh giá");
            return null;
        }
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
    public boolean addFeedback(Long treatmentCycleId, String reviewText,Integer rating) {

            TreatmentCycle treatmentCycle = treatmentCycleRepository.findById(treatmentCycleId).orElse(null);

            if(treatmentCycle == null) {
                return false;
            }
            // Chuyen data tu DTO -> Entity
            Feedback feedback = new Feedback();
            LocalDate localDate = LocalDate.now();

            feedback.setReviewText(reviewText);
            feedback.setRating(rating);
            feedback.setReviewDate(localDate);
            feedback.setTreatmentCycleFeedback(treatmentCycle);
            feedbackRepository.save(feedback);

                return true;

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

    @Override
    public List<FeedbackInformationDTO> getTop4FeedbackInformation() {
        List<Feedback> feedbacks = feedbackRepository.findTop4ByOrderByRatingDesc();
        List<FeedbackInformationDTO> feedbackInformationDTOS = new ArrayList<FeedbackInformationDTO>();
        for (Feedback feedback : feedbacks) {
            FeedbackInformationDTO feedbackInformationDTO = new FeedbackInformationDTO(feedback);
            feedbackInformationDTOS.add(feedbackInformationDTO);
        }
        return feedbackInformationDTOS;
    }

}

