package uth.edu.dieutrihiemmuon.AdminControllerUnitTest;

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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import uth.edu.dieutrihiemmuon.controllers.Admin.CustomerController;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.CustomerService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    // Test @ModelAttribute getCustomers()
    @Test
    void testGetCustomers_ReturnsCustomerList() {
        List<User> customers = List.of(new User(), new User());
        when(customerService.getAllCustomers()).thenReturn(customers);

        List<User> result = customerController.getCustomers();

        assertEquals(2, result.size());
        verify(customerService).getAllCustomers();
    }

    // Test GET /admin/customer/index
    @Test
    void testAdminCustomerIndex_ReturnsView() {
        String view = customerController.admincustomerindex();
        assertEquals("admin/customer/index", view);
    }

    // Test GET /admin/customer/delete/{id}
    @Test
    void testDeleteCustomer_RedirectsToIndex() {
        Long customerId = 1L;

        String view = customerController.deleteCustomer(customerId);

        assertEquals("redirect:/admin/customer/index", view);
        verify(customerService).deleteCustomer(customerId);
    }

    // Test GET /admin/customer/create
    @Test
    void testAdminCustomerCreateForm_ReturnsViewWithNewUser() {
        String view = customerController.adminCustomerCreateForm(model);

        assertEquals("admin/customer/create", view);
        verify(model).addAttribute(eq("customer"), any(User.class));
    }

    // Test POST /admin/customer/create thành công
    @Test
    void testAdminCustomerAdd_Success() {
        User customer = new User();
        customer.setUserName("newcustomer");
        customer.setEmail("test@example.com");
        customer.setPhoneNumber("123456789");

        when(customerService.isUsernameExists(customer.getUserName())).thenReturn(false);
        when(customerService.isEmailExists(customer.getEmail())).thenReturn(false);
        when(customerService.isPhoneNumberExists(customer.getPhoneNumber())).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = customerController.adminCustomerAdd(customer, bindingResult, model);

        assertEquals("redirect:/admin/customer/index", view);
        verify(customerService).addCustomer(customer);
    }

    // Test POST /admin/customer/create thất bại do trùng username
    @Test
    void testAdminCustomerAdd_Fail_DuplicateUsername() {
        User customer = new User();
        customer.setUserName("existingusername");

        when(customerService.isUsernameExists(customer.getUserName())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = customerController.adminCustomerAdd(customer, bindingResult, model);

        assertEquals("admin/customer/create", view);
        verify(bindingResult).rejectValue("userName", "error.customer", "Tên đăng nhập đã tồn tại");
    }

    // Test POST /admin/customer/create thất bại do trùng email
    @Test
    void testAdminCustomerAdd_Fail_DuplicateEmail() {
        User customer = new User();
        customer.setEmail("existingemail");

        when(customerService.isEmailExists(customer.getEmail())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = customerController.adminCustomerAdd(customer, bindingResult, model);

        assertEquals("admin/customer/create", view);
        verify(bindingResult).rejectValue("email", "error.customer", "Email đã tồn tại");
    }

    // Test POST /admin/customer/create thất bại do trùng số điện thoại
    @Test
    void testAdminCustomerAdd_Fail_DuplicatePhoneNumber() {
        User customer = new User();
        customer.setPhoneNumber("existingphonenumber");

        when(customerService.isPhoneNumberExists(customer.getPhoneNumber())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = customerController.adminCustomerAdd(customer, bindingResult, model);

        assertEquals("admin/customer/create", view);
        verify(bindingResult).rejectValue("phoneNumber", "error.customer", "Số điện thoại đã tồn tại");
    }

    // Test GET /admin/customer/edit/{id}
    @Test
    void testAdminCustomerEdit_ReturnsViewWithCustomer() {
        Long customerId = 1L;
        User customer = new User();
        when(customerService.getCustomerById(customerId)).thenReturn(customer);

        String view = customerController.adminCustomerEdit(customerId, model);

        assertEquals("admin/customer/edit", view);
        verify(model).addAttribute("customer", customer);
    }

    // Test POST /admin/customer/edit/{id} thành công
    @Test
    void testUpdateCustomer_Success() {
        Long customerId = 1L;
        User existingCustomer = new User();
        existingCustomer.setIdUser(customerId);
        existingCustomer.setRole("CUSTOMER");
        existingCustomer.setUserName("olduser");
        existingCustomer.setEmail("old@example.com");
        existingCustomer.setPhoneNumber("123456789");

        User updatedCustomer = new User();
        updatedCustomer.setUserName("newuser");
        updatedCustomer.setEmail("new@example.com");
        updatedCustomer.setPhoneNumber("1234567");

        when(customerService.getCustomerById(customerId)).thenReturn(existingCustomer);
        when(customerService.isUsernameExists(updatedCustomer.getUserName())).thenReturn(false);
        when(customerService.isEmailExists(updatedCustomer.getEmail())).thenReturn(false);
        when(customerService.isPhoneNumberExists(updatedCustomer.getPhoneNumber())).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = customerController.updateCustomer(customerId, updatedCustomer, bindingResult, model);

        assertEquals("redirect:/admin/customer/index", view);
        verify(customerService).updateCustomer(customerId, updatedCustomer);
    }

    // Test POST /admin/customer/edit/{id} - Customer không tồn tại
    @Test
    void testUpdateCustomer_Fail_CustomerNotFound() {
        Long customerId = 1L;
        User updatedCustomer = new User();

        when(customerService.getCustomerById(customerId)).thenReturn(null);

        String view = customerController.updateCustomer(customerId, updatedCustomer, bindingResult, model);

        // Assert trả về đúng view
        assertEquals("admin/customer/edit", view);

        // Verify gắn attribute error vào model
        verify(model).addAttribute("error", "Không tìm thấy người dùng");
    }

    // Test POST /admin/customer/edit/{id} - Trùng username
    @Test
    void testUpdateCustomer_Fail_DuplicateUsername() {
        Long customerId = 1L;

        User existingCustomer = new User();
        existingCustomer.setUserName("olduser");
        existingCustomer.setEmail("old@example.com");
        existingCustomer.setPhoneNumber("123456789");

        User updatedCustomer = new User();
        updatedCustomer.setUserName("existingusername"); // đổi sang username khác
        updatedCustomer.setEmail("old@example.com");
        updatedCustomer.setPhoneNumber("123456789");

        when(customerService.getCustomerById(customerId)).thenReturn(existingCustomer);
        when(customerService.isUsernameExists(updatedCustomer.getUserName())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true); // giả lập có lỗi

        String view = customerController.updateCustomer(customerId, updatedCustomer, bindingResult, model);

        // Assert trả về đúng view
        assertEquals("admin/customer/edit", view);

        // Verify gọi rejectValue với lỗi username
        verify(bindingResult).rejectValue("userName", "error.customer", "Tên đăng nhập đã tồn tại");
    }


    // Test POST /admin/customer/edit/{id} thất bại do duplicate email
    @Test
    void testUpdateCustomer_Fail_DuplicateEmail() {
        Long customerId = 1L;
        User existingCustomer = new User();
        existingCustomer.setUserName("user1");
        existingCustomer.setEmail("old@example.com");
        existingCustomer.setPhoneNumber("123456789");

        User updatedCustomer = new User();
        updatedCustomer.setUserName("user1"); // không đổi username
        updatedCustomer.setEmail("existing@example.com"); // đổi email
        updatedCustomer.setPhoneNumber("123456789"); // không đổi phone

        when(customerService.getCustomerById(customerId)).thenReturn(existingCustomer);
        when(customerService.isEmailExists(updatedCustomer.getEmail())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = customerController.updateCustomer(customerId, updatedCustomer, bindingResult, model);

        assertEquals("admin/customer/edit", view);
        verify(bindingResult).rejectValue("email", "error.customer", "Email đã tồn tại");
    }

    // Test POST /admin/customer/edit/{id} thất bại do duplicate số điện thoại
    @Test
    void testUpdateCustomer_Fail_DuplicatePhoneNumber() {
        Long customerId = 1L;
        User existingCustomer = new User();
        existingCustomer.setUserName("user1");
        existingCustomer.setEmail("old@example.com");
        existingCustomer.setPhoneNumber("123456789");

        User updatedCustomer = new User();
        updatedCustomer.setUserName("user1"); // không đổi username
        updatedCustomer.setEmail("old@example.com"); // không đổi email
        updatedCustomer.setPhoneNumber("123456"); // đổi số điện thoại nhưng trùng

        when(customerService.getCustomerById(customerId)).thenReturn(existingCustomer);
        when(customerService.isPhoneNumberExists(updatedCustomer.getPhoneNumber())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = customerController.updateCustomer(customerId, updatedCustomer, bindingResult, model);

        assertEquals("admin/customer/edit", view);
        verify(bindingResult).rejectValue("phoneNumber", "error.customer", "Số điện thoại đã tồn tại");
    }



    // Test GET /admin/customer/detail/{id}
    @Test
    void testAdminCustomerDetail_ReturnsViewWithCustomer() {
        Long customerId = 1L;
        User customer = new User();
        when(customerService.getCustomerById(customerId)).thenReturn(customer);

        String view = customerController.admincustomerdetail(customerId, model);

        assertEquals("admin/customer/detail", view);
        verify(model).addAttribute("customer", customer);
    }
}
