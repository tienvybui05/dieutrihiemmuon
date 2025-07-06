package uth.edu.dieutrihiemmuon.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.FeedbackDTO;
import uth.edu.dieutrihiemmuon.dto.FeedbackInformationDTO;
import uth.edu.dieutrihiemmuon.dto.TreatmentCycleDTO;
import uth.edu.dieutrihiemmuon.dto.WorkscheduledoctorDTO;
import uth.edu.dieutrihiemmuon.models.*;
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
    public boolean addFeedback(Long serviceId,Long userId, Long treatmentCycleId, String reviewText,Integer rating) {

            // Gan data vao DTO
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            feedbackDTO.setServiceId(serviceId);
            feedbackDTO.setUserId(userId);
            feedbackDTO.setTreatmentCycleId(treatmentCycleId);
            feedbackDTO.setReviewText(reviewText);
            feedbackDTO.setReviewDate(LocalDate.now());
            feedbackDTO.setRating(rating);

            // Chuyen data tu DTO -> Entity
            Feedback feedback = new Feedback();

            feedback.setReviewText(feedbackDTO.getReviewText());
            feedback.setRating(feedbackDTO.getRating());
            feedback.setReviewDate(feedbackDTO.getReviewDate());

            ServicePackage service = new ServicePackage();
            service.setIdService(feedbackDTO.getServiceId());
            feedback.setServiceFeedback(service);

            User user = new User();
            user.setIdUser(feedbackDTO.getUserId());
            feedback.setUserFeedback(user);

        if (feedbackDTO.getTreatmentCycleId() != null) {
            TreatmentCycle treatmentCycle = new TreatmentCycle();
            treatmentCycle.setIdTreatmentCycle(feedbackDTO.getTreatmentCycleId());
            feedback.setTreatmentCycleFeedback(treatmentCycle);
        }


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

//    public List<FeedbackInformationDTO> getFeedbackCustomer(long id){
//            List<Feedback>  feedbacks= feedbackRepository.findByUserFeedback_idUser(id);
//            List<FeedbackInformationDTO> feedbackInformationDTOS = new ArrayList<>();
//
//        for (Feedback feedback : feedbacks) {
//            feedbackInformationDTOS.add(new FeedbackInformationDTO(feedback));
//            }
//        return feedbackRepository.findByUserFeedback_idUser(id).stream()
//                .map(FeedbackInformationDTO::new)
//                .collect(Collectors.toList());    }

//    public List<WorkscheduledoctorDTO> getTreatmentScheduleCustomer(long id){
//        try{
//            List<TreatmentCycle>  treatmentCycles= treatmentCycleRepository.findByUserTreatmentCycle_idUser(id);
//            List<WorkscheduledoctorDTO> workscheduledoctorDTOS = new ArrayList<WorkscheduledoctorDTO>();
//            LocalDate today = LocalDate.now();
//            for(TreatmentCycle treatmentCycle : treatmentCycles) {
//                boolean check = false;
//                List<TreatmentSession> treatmentSessions = sessionRepository.findByTreatmentCycle_idTreatmentCycle(treatmentCycle.getIdTreatmentCycle());
//                for(TreatmentSession treatmentSession : treatmentSessions) {
//                    if(treatmentSession.getTreatmentDay()!=null && treatmentSession.getTreatmentDay().equals(today)) {
//                        check = true;
//                        break;
//                    }
//                }
//                WorkscheduledoctorDTO wsd = new WorkscheduledoctorDTO(treatmentCycle,check);
//                workscheduledoctorDTOS.add(wsd);
//            }
//
//            return workscheduledoctorDTOS;
//        } catch (Exception e) {
//            throw new RuntimeException("Lỗi khi lấy danh sách đi khám" + e);
//        }
//    }

}

