package uth.edu.dieutrihiemmuon.CustomerControllerUnitTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import uth.edu.dieutrihiemmuon.controllers.Customer.GlobalController;
import uth.edu.dieutrihiemmuon.dto.UserDTO;
import uth.edu.dieutrihiemmuon.services.IUserService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GlobalControllerTest {

    @InjectMocks
    private GlobalController globalController;

    @Mock
    private IUserService userService;

    @Mock
    private Model model;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Test
    void addFullnameToModel_UserLoggedIn_AddsUserDTO() {
        // Mock user đã đăng nhập
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("testUser");
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.setContext(securityContext);

        UserDTO mockUserDTO = new UserDTO();
        when(userService.getUserByUserName("testUser")).thenReturn(mockUserDTO);

        // Call method
        globalController.addFullnameToModel(model);

        // Verify
        verify(model).addAttribute("userDTO", mockUserDTO);
    }

    @Test
    void addFullnameToModel_AnonymousUser_DoesNotAddUserDTO() {
        // Mock anonymousUser
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");
        SecurityContextHolder.setContext(securityContext);

        // Call method
        globalController.addFullnameToModel(model);

        // Verify model.addAttribute không được gọi
        verify(model, never()).addAttribute(eq("userDTO"), any());
    }

    @Test
    void addFullnameToModel_NoAuthentication_DoesNotAddUserDTO() {
        // Mock null authentication
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Call method
        globalController.addFullnameToModel(model);

        // Verify model.addAttribute không được gọi
        verify(model, never()).addAttribute(eq("userDTO"), any());
    }
}
