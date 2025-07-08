package uth.edu.dieutrihiemmuon.AdminControllerUnitTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.Authentication;

import uth.edu.dieutrihiemmuon.controllers.Admin.AdminAuthController;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdminAuthControllerTest {

    @InjectMocks
    private AdminAuthController adminAuthController;

    @Mock
    private Authentication authentication;

    // Test GET /admin/auth/login - Admin chưa đăng nhập
    @Test
    void testLogin_AdminNotLoggedIn_ReturnsLoginView() {
        String view = adminAuthController.login(null);
        assertEquals("admin/auth/login", view);
    }

    // Test GET /admin/auth/login - Admin đã đăng nhập
    @Test
    void testLogin_AdminAlreadyLoggedIn_RedirectsAdminHome() {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new Object()); // Không phải String (anonymous)

        String view = adminAuthController.login(authentication);
        assertEquals("redirect:/admin", view);
    }

    // Test GET /admin/auth/login - Anonymous user (authentication.getPrincipal() instanceof String)
    @Test
    void testLogin_AnonymousUser_ReturnsLoginView() {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        String view = adminAuthController.login(authentication);
        assertEquals("admin/auth/login", view);
    }

    // Test GET /admin/auth/logout
    @Test
    void testLogout_ReturnsLogoutView() {
        String view = adminAuthController.logout();
        assertEquals("/admin/auth/logout", view);
    }

    // Test GET /admin/auth/error403
    @Test
    void testError403_ReturnsError403View() {
        String view = adminAuthController.error403();
        assertEquals("admin/auth/error403", view);
    }
}
