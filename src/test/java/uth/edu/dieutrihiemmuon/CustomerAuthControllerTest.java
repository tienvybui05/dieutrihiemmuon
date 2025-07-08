package uth.edu.dieutrihiemmuon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.dieutrihiemmuon.controllers.Customer.CustomerAuthController;
import uth.edu.dieutrihiemmuon.dto.RegisterDTO;
import uth.edu.dieutrihiemmuon.dto.UserDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.ICustomerService;
import uth.edu.dieutrihiemmuon.services.UserService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerAuthControllerTest {

    @InjectMocks
    private CustomerAuthController customerAuthController;

    @Mock
    private ICustomerService customerService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Authentication authentication;

    @Mock
    private RedirectAttributes redirectAttributes;

    // 🔥 Test GET /login - User chưa đăng nhập
    @Test
    void testLogin_UserNotLoggedIn_ReturnsLoginView() {
        String view = customerAuthController.login(null);
        assertEquals("customer/auth/login", view);
    }

    // 🔥 Test GET /login - User đã đăng nhập
    @Test
    void testLogin_UserAlreadyLoggedIn_RedirectsHome() {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new User());

        String view = customerAuthController.login(authentication);
        assertEquals("redirect:/", view);
    }

    // 🔥 Test GET /register
    @Test
    void testRegister_ReturnsRegisterView() {
        String view = customerAuthController.register(model);
        assertEquals("customer/auth/register", view);
        verify(model).addAttribute(eq("RegisterDTO"), any(RegisterDTO.class));
    }

    // 🔥 Test POST /register thành công
    @Test
    void testAddAccount_Success() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUserName("testuser");
        dto.setEmail("test@example.com");
        dto.setPhoneNumber("123456789");

        when(customerService.isUsernameExists(dto.getUserName())).thenReturn(false);
        when(customerService.isEmailExists(dto.getEmail())).thenReturn(false);
        when(customerService.isPhoneNumberExists(dto.getPhoneNumber())).thenReturn(false);

        String view = customerAuthController.addAccount(dto, bindingResult, model);

        assertEquals("redirect:/", view);
        verify(customerService).addAccount(dto);
    }

    // 🔥 Test POST /register thất bại do trùng username
    @Test
    void testAddAccount_Fail_UsernameExists() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUserName("testuser");

        when(customerService.isUsernameExists(dto.getUserName())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = customerAuthController.addAccount(dto, bindingResult, model);

        assertEquals("customer/auth/register", view);
        verify(bindingResult).rejectValue("userName", "error.customer", "Tên đăng nhập đã tồn tại");
    }

    // 🔥 Test GET /changepassword
    @Test
    void testShowChangePasswordForm_ReturnsView() {
        String view = customerAuthController.showChangePasswordForm(model);
        assertEquals("customer/auth/changepassword", view);
    }

    // 🔥 Test POST /changepassword thành công
    @Test
    void testChangePassword_Success() {
        when(authentication.getName()).thenReturn("testuser");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("testuser");
        when(userService.getUserByUserName("testuser")).thenReturn(userDTO);
        when(userService.checkPassword(eq("testuser"), eq("oldpass"))).thenReturn(true);

        String view = customerAuthController.changepassword("oldpass", "newpass", "newpass", authentication, redirectAttributes);

        assertEquals("redirect:/", view);
        verify(userService).updatePassword("testuser", "newpass");
        verify(redirectAttributes).addFlashAttribute("success", "Đổi mật khẩu thành công.");
    }

    // 🔥 Test POST /changepassword thất bại do mật khẩu cũ sai
    @Test
    void testChangePassword_Fail_OldPasswordWrong() {
        when(authentication.getName()).thenReturn("testuser");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("testuser");
        when(userService.getUserByUserName("testuser")).thenReturn(userDTO);
        when(userService.checkPassword(eq("testuser"), eq("oldpass"))).thenReturn(false);

        String view = customerAuthController.changepassword("oldpass", "newpass", "newpass", authentication, redirectAttributes);

        assertEquals("redirect:/customer/auth/changepassword", view);
        verify(redirectAttributes).addFlashAttribute("error", "Mật khẩu cũ không đúng.");
    }

    // 🔥 Test POST /changepassword thất bại do newPassword != confirmPassword
    @Test
    void testChangePassword_Fail_ConfirmMismatch() {
        when(authentication.getName()).thenReturn("testuser");

        String view = customerAuthController.changepassword("oldpass", "newpass", "wrongconfirm", authentication, redirectAttributes);

        assertEquals("redirect:/customer/auth/changepassword", view);
        verify(redirectAttributes).addFlashAttribute("error", "Mật khẩu mới và xác nhận không trùng khớp.");
    }
}
