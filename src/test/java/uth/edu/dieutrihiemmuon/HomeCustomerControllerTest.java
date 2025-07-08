package uth.edu.dieutrihiemmuon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import uth.edu.dieutrihiemmuon.controllers.Customer.HomeCustomerController;
import uth.edu.dieutrihiemmuon.dto.FeedbackInformationDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.services.FeedbackService;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.IServicePackageService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeCustomerControllerTest {

    @InjectMocks
    private HomeCustomerController controller;

    @Mock
    private ICustomerService customerService;

    @Mock
    private IServicePackageService servicePackageService;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private Model model;

    // ✅ Test GET /
    @Test
    void testAdminServicePackageIndex_ReturnsCustomerIndex() {
        when(servicePackageService.getServicePackages()).thenReturn(Collections.singletonList(new ServicePackageDTO()));
        when(feedbackService.getFeedbackInformationList()).thenReturn(Collections.singletonList(new FeedbackInformationDTO()));

        String view = controller.adminservicepackageindex(model);

        assertEquals("customer/index", view);
        verify(model).addAttribute(eq("ServicePackageDTOs"), anyList());
        verify(model).addAttribute(eq("FeedbackInformationDTOs"), anyList());
        verify(model).addAttribute("activePage", "index");
    }

    // ✅ Test GET /about
    @Test
    void testAbout_ReturnsAboutView() {
        String view = controller.about(model);

        assertEquals("customer/about", view);
        verify(model).addAttribute("activePage", "about");
    }

    // ✅ Test GET /blog
    @Test
    void testBlog_ReturnsBlogView() {
        String view = controller.blog(model);

        assertEquals("customer/blog", view);
        verify(model).addAttribute("activePage", "blog");
    }

    // ✅ Test GET /blog-detail-1
    @Test
    void testBlogDetail1_ReturnsBlogDetail1View() {
        String view = controller.blog1(model);

        assertEquals("customer/blog-detail-1", view);
        verify(model).addAttribute("activePage", "blog-detail-1");
    }

    // ✅ Test GET /blog-detail-2
    @Test
    void testBlogDetail2_ReturnsBlogDetail2View() {
        String view = controller.blogdetail2(model);

        assertEquals("customer/blog-detail-2", view);
        verify(model).addAttribute("activePage", "blog-detail-2");
    }

    // ✅ Test GET /blog-detail-3
    @Test
    void testBlogDetail3_ReturnsBlogDetail3View() {
        String view = controller.blogdetail3(model);

        assertEquals("customer/blog-detail-3", view);
        verify(model).addAttribute("activePage", "blog-detail-3");
    }

    // ✅ Test GET /contact
    @Test
    void testContact_ReturnsContactView() {
        String view = controller.contact(model);

        assertEquals("customer/contact", view);
        verify(model).addAttribute("activePage", "contact");
    }

    // ✅ Test GET /services
    @Test
    void testServices_ReturnsServicesView() {
        when(servicePackageService.getServicePackages()).thenReturn(Collections.singletonList(new ServicePackageDTO()));
        when(feedbackService.getFeedbackInformationList()).thenReturn(Collections.singletonList(new FeedbackInformationDTO()));

        String view = controller.services(model);

        assertEquals("customer/services/index", view);
        verify(model).addAttribute(eq("ServicePackageDTOs"), anyList());
        verify(model).addAttribute(eq("FeedbackInformationDTOs"), anyList());
        verify(model).addAttribute("activePage", "services");
    }
}
