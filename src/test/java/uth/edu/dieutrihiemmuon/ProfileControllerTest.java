package uth.edu.dieutrihiemmuon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import uth.edu.dieutrihiemmuon.controllers.Customer.ProfileController;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private ICustomerService customerService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    // ✅ Test GET /profile khi tìm thấy user
    @Test
    void testProfile_UserFound_ReturnsProfileView() {
        String username = "testuser";
        when(authentication.getName()).thenReturn(username);

        User user = new User();
        user.setFullName("Test User");
        user.setImage("profile.png");
        when(customerService.findByUsername(username)).thenReturn(user);

        String view = profileController.profile(model, authentication);

        assertEquals("customer/profile", view);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("image", "profile.png");
    }

    // ✅ Test GET /profile khi không tìm thấy user
    @Test
    void testProfile_UserNotFound_ReturnsErrorView() {
        String username = "testuser";
        when(authentication.getName()).thenReturn(username);

        when(customerService.findByUsername(username)).thenReturn(null);

        String view = profileController.profile(model, authentication);

        assertEquals("error", view);
        verify(model).addAttribute("error", "Không tìm thấy người dùng");
    }

    // ✅ Test POST /profile update thành công
    @Test
    void testUpdateProfile_SuccessfulUpdate() {
        String username = "testuser";
        when(authentication.getName()).thenReturn(username);

        User existingUser = new User();
        existingUser.setIdUser(1L);
        existingUser.setFullName("Old Name");
        when(customerService.findByUsername(username)).thenReturn(existingUser);

        User updatedUser = new User();
        updatedUser.setFullName("New Name");
        updatedUser.setDateOfBirth(existingUser.getDateOfBirth());
        updatedUser.setPhoneNumber("123456789");
        updatedUser.setAddress("New Address");
        updatedUser.setGender("Nam");

        String view = profileController.updateProfile(updatedUser, authentication);

        assertEquals("redirect:/profile", view);
        verify(customerService).updateCustomer(existingUser.getIdUser(), existingUser);
    }

    // ✅ Test POST /profile khi user null
    @Test
    void testUpdateProfile_UserNotFound() {
        String username = "testuser";
        when(authentication.getName()).thenReturn(username);
        when(customerService.findByUsername(username)).thenReturn(null);

        User updatedUser = new User();
        updatedUser.setFullName("New Name");

        String view = profileController.updateProfile(updatedUser, authentication);

        assertEquals("redirect:/profile", view);
        verify(customerService, never()).updateCustomer(anyLong(), any(User.class));
    }
}
