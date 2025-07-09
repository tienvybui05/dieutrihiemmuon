package uth.edu.dieutrihiemmuon.AdminControllerUnitTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.ui.Model;

import java.util.List;

import uth.edu.dieutrihiemmuon.controllers.Admin.FeedbackController;
import uth.edu.dieutrihiemmuon.dto.FeedbackInformationDTO;
import uth.edu.dieutrihiemmuon.services.FeedbackService;
import uth.edu.dieutrihiemmuon.services.ServicePackageService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FeedbackControllerTest {

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private ServicePackageService servicePackageService;

    @Mock
    private Model model;

    // Test GET /admin/feedback/index
    @Test
    void testAdminFeedbackIndex_ReturnsViewWithFeedbackList() {
        List<FeedbackInformationDTO> feedbackList = List.of(
                new FeedbackInformationDTO(),
                new FeedbackInformationDTO()
        );
        when(feedbackService.getFeedbackInformationList()).thenReturn(feedbackList);

        String view = feedbackController.adminfeedbackindex(model);

        assertEquals("admin/feedback/index", view);
        verify(feedbackService).getFeedbackInformationList();
        verify(model).addAttribute("feedbackInformationDTOs", feedbackList);
    }

    // Test GET /admin/feedback/detail/{id}
    @Test
    void testAdminFeedbackDetail_ReturnsViewWithFeedback() {
        long feedbackId = 1L;
        FeedbackInformationDTO feedbackInfo = new FeedbackInformationDTO();
        when(feedbackService.getFeedbackInformation(feedbackId)).thenReturn(feedbackInfo);

        String view = feedbackController.adminfeedbackdetail(feedbackId, model);

        assertEquals("admin/feedback/detail", view);
        verify(feedbackService).getFeedbackInformation(feedbackId);
        verify(model).addAttribute("feedbackInformationDTO", feedbackInfo);
    }

    // Test GET /admin/feedback/delete/{id}
    @Test
    void testAdminFeedbackDelete_RedirectsToIndex() {
        long feedbackId = 1L;

        String view = feedbackController.adminfeedbackdelete(feedbackId, model);

        assertEquals("redirect:/admin/feedback/index", view);
        verify(feedbackService).deleteFeedback(feedbackId);
    }
}
