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

import uth.edu.dieutrihiemmuon.controllers.Admin.EmployeeController;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.IEmployeeService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private IEmployeeService employeeService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    // Test @ModelAttribute getEmployees()
    @Test
    void testGetEmployees_ReturnsEmployeeList() {
        List<User> employees = List.of(new User(), new User());
        when(employeeService.getAllEmployees()).thenReturn(employees);

        Iterable<User> result = employeeController.getEmployees();

        assertEquals(2, ((List<User>) result).size());
        verify(employeeService).getAllEmployees();
    }

    // Test GET /admin/employee/index
    @Test
    void testAdminEmployeeIndex_ReturnsView() {
        String view = employeeController.adminEmployeeIndex();
        assertEquals("admin/employee/index", view);
    }

    // Test GET /admin/employee/create
    @Test
    void testAdminEmployeeCreate_ReturnsViewWithNewEmployee() {
        String view = employeeController.adminEmployeeCreate(model);

        assertEquals("admin/employee/create", view);
        verify(model).addAttribute(eq("employee"), any(User.class));
    }

    // Test POST /admin/employee/create - Success
    @Test
    void testAdminEmployeeAdd_Success() {
        User employee = new User();
        employee.setUserName("newemployee");
        employee.setEmail("employee@example.com");
        employee.setPhoneNumber("123456789");

        when(employeeService.isUsernameExists(employee.getUserName())).thenReturn(false);
        when(employeeService.isEmailExists(employee.getEmail())).thenReturn(false);
        when(employeeService.isPhoneNumberExists(employee.getPhoneNumber())).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = employeeController.adminemployeeAdd(employee, bindingResult, model);

        assertEquals("redirect:/admin/employee/index", view);
        verify(employeeService).addEmployee(employee);
    }

    // Test POST /admin/employee/create - Duplicate Username
    @Test
    void testAdminEmployeeAdd_Fail_DuplicateUsername() {
        User employee = new User();
        employee.setUserName("existingemployee");

        when(employeeService.isUsernameExists(employee.getUserName())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = employeeController.adminemployeeAdd(employee, bindingResult, model);

        assertEquals("admin/employee/create", view);
        verify(bindingResult).rejectValue("userName", "error.employee", "Tên đăng nhập đã tồn tại");
    }

    // Test POST /admin/employee/create - Duplicate Email
    @Test
    void testAdminEmployeeAdd_Fail_DuplicateEmail() {
        User employee = new User();
        employee.setEmail("existingemployee@example.com");

        when(employeeService.isEmailExists(employee.getEmail())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = employeeController.adminemployeeAdd(employee, bindingResult, model);

        assertEquals("admin/employee/create", view);
        verify(bindingResult).rejectValue("email", "error.employee", "Email đã tồn tại");
    }

    // Test POST /admin/employee/create - Duplicate Phone Number
    @Test
    void testAdminEmployeeAdd_Fail_DuplicatePhoneNumber() {
        User employee = new User();
        employee.setPhoneNumber("123456");

        when(employeeService.isPhoneNumberExists(employee.getPhoneNumber())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = employeeController.adminemployeeAdd(employee, bindingResult, model);

        assertEquals("admin/employee/create", view);
        verify(bindingResult).rejectValue("phoneNumber", "error.employee", "Số điện thoại đã tồn tại");
    }

    // Test GET /admin/employee/edit/{id}
    @Test
    void testAdminEmployeeEdit_ReturnsViewWithEmployee() {
        Long employeeId = 1L;
        User employee = new User();
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        String view = employeeController.adminEmployeeEdit(employeeId, model);

        assertEquals("admin/employee/edit", view);
        verify(model).addAttribute("employee", employee);
    }

    // Test GET /admin/employee/edit/{id} - Employee not found
    @Test
    void testAdminEmployeeEdit_RedirectsToIndexWhenNotFound() {
        Long employeeId = 1L;
        when(employeeService.getEmployeeById(employeeId)).thenReturn(null);

        String view = employeeController.adminEmployeeEdit(employeeId, model);

        assertEquals("redirect:/admin/employee/index", view);
    }

    // Test POST /admin/employee/edit/{id} - Success
    @Test
    void testUpdateEmployee_Success() {
        Long employeeId = 1L;
        User existingEmployee = new User();
        existingEmployee.setIdUser(employeeId);
        existingEmployee.setRole("EMPLOYEE");
        existingEmployee.setUserName("olduser");
        existingEmployee.setEmail("old@example.com");
        existingEmployee.setPhoneNumber("123");

        User updatedEmployee = new User();
        updatedEmployee.setUserName("newuser");
        updatedEmployee.setEmail("new@example.com");
        updatedEmployee.setPhoneNumber("456");

        when(employeeService.getEmployeeById(employeeId)).thenReturn(existingEmployee);
        when(employeeService.isUsernameExists(updatedEmployee.getUserName())).thenReturn(false);
        when(employeeService.isEmailExists(updatedEmployee.getEmail())).thenReturn(false);
        when(employeeService.isPhoneNumberExists(updatedEmployee.getPhoneNumber())).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = employeeController.updateEmployee(employeeId, updatedEmployee, bindingResult, model);

        assertEquals("redirect:/admin/employee/index", view);
        verify(employeeService).updateEmployee(employeeId, updatedEmployee);
    }

    // Test POST /admin/employee/edit/{id} - Duplicate Username
    @Test
    void testUpdateEmployee_Fail_DuplicateUsername() {
        Long employeeId = 1L;
        User existingEmployee = new User();
        existingEmployee.setUserName("user1");
        existingEmployee.setEmail("old@example.com");
        existingEmployee.setPhoneNumber("123456");

        User updatedEmployee = new User();
        updatedEmployee.setUserName("existingusername");
        updatedEmployee.setEmail("old@example.com");
        updatedEmployee.setPhoneNumber("123");

        when(employeeService.getEmployeeById(employeeId)).thenReturn(existingEmployee);
        when(employeeService.isUsernameExists(updatedEmployee.getUserName())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = employeeController.updateEmployee(employeeId, updatedEmployee, bindingResult, model);

        assertEquals("admin/employee/edit", view);
        verify(bindingResult).rejectValue("userName", "error.employee", "Tên đăng nhập đã tồn tại");
    }


    // Test POST /admin/employee/edit/{id} - Duplicate Email
    @Test
    void testUpdateEmployee_Fail_DuplicateEmail() {
        Long employeeId = 1L;
        User existingEmployee = new User();
        existingEmployee.setUserName("user1");
        existingEmployee.setEmail("old@example.com");
        existingEmployee.setPhoneNumber("123");

        User updatedEmployee = new User();
        updatedEmployee.setUserName("user1"); // same username
        updatedEmployee.setEmail("duplicate@example.com"); // change email
        updatedEmployee.setPhoneNumber("123"); // same phone

        when(employeeService.getEmployeeById(employeeId)).thenReturn(existingEmployee);
        when(employeeService.isEmailExists(updatedEmployee.getEmail())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = employeeController.updateEmployee(employeeId, updatedEmployee, bindingResult, model);

        assertEquals("admin/employee/edit", view);
        verify(bindingResult).rejectValue("email", "error.employee", "Email đã tồn tại");
    }

    // Test POST /admin/employee/edit/{id} - Duplicate Phone Number
    @Test
    void testUpdateEmployee_Fail_DuplicatePhoneNumber() {
        Long employeeId = 1L;
        User existingEmployee = new User();
        existingEmployee.setUserName("user1");
        existingEmployee.setEmail("old@example.com");
        existingEmployee.setPhoneNumber("123456");

        User updatedEmployee = new User();
        updatedEmployee.setUserName("user1");
        updatedEmployee.setEmail("old@example.com");
        updatedEmployee.setPhoneNumber("123456789");

        when(employeeService.getEmployeeById(employeeId)).thenReturn(existingEmployee);
        when(employeeService.isPhoneNumberExists(updatedEmployee.getPhoneNumber())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = employeeController.updateEmployee(employeeId, updatedEmployee, bindingResult, model);

        assertEquals("admin/employee/edit", view);
        verify(bindingResult).rejectValue("phoneNumber", "error.employee", "Số điện thoại đã tồn tại");
    }


    // Test GET /admin/employee/detail/{id}
    @Test
    void testAdminEmployeeDetail_ReturnsViewWithEmployee() {
        Long employeeId = 1L;
        User employee = new User();
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        String view = employeeController.adminEmployeeDetail(employeeId, model);

        assertEquals("admin/employee/detail", view);
        verify(model).addAttribute("employee", employee);
    }

    // Test GET /admin/employee/delete/{id}
    @Test
    void testDeleteEmployee_RedirectsToIndex() {
        Long employeeId = 1L;

        String view = employeeController.deleteEmployee(employeeId);

        assertEquals("redirect:/admin/employee/index", view);
        verify(employeeService).deleteEmployee(employeeId);
    }
}
