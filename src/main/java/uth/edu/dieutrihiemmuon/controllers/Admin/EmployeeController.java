package uth.edu.dieutrihiemmuon.controllers.Admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.IEmployeeService;

@Controller
public class EmployeeController {
    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Thêm danh sách nhân viên vào model
    @ModelAttribute("employees")
    public Iterable<User> getEmployees() {
        return employeeService.getAllEmployees();
    }

    //Trang index của nhân viêns
    @GetMapping("/admin/employee/index")
    public String adminEmployeeIndex() {
        // Lấy danh sách nhân viên từ service
        return "admin/employee/index"; // Trả về trang index của nhân viên
    }

    // Trang tạo mới nhân viên
    @GetMapping("/admin/employee/create")
    public String adminEmployeeCreate(Model model) {
        model.addAttribute("employee", new User());
        return "admin/employee/create"; // Trả về trang tạo mới nhân viên
    }
  

    // Trang xem chi tiết nhân viên
    

    //Sửa nhân viên
    @GetMapping("/admin/employee/edit/{id}")
    public String adminEmployeeEdit(@PathVariable("id") Long id, Model model) {
        User employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return "redirect:/admin/employee/index"; // Nếu không tìm thấy nhân viên, chuyển hướng về trang index
        }
        model.addAttribute("employee", employee);
        return "admin/employee/edit"; // Trả về trang chỉnh sửa nhân viên
    }

    @PostMapping("/admin/employee/edit/{id}")
    public String updateEmployee(
            @PathVariable("id") Long id,
            @ModelAttribute("employee") @Valid User updatedEmployee,
            BindingResult result,
            Model model) {

        User existingEmployee = employeeService.getEmployeeById(id);

        // Nếu không tồn tại thì trả về
        if (existingEmployee == null) {
            model.addAttribute("error", "Không tìm thấy người dùng");
            return "admin/employee/edit";
        }

        // Kiểm tra trùng username (loại trừ bản ghi hiện tại)
        if (!updatedEmployee.getUserName().equals(existingEmployee.getUserName()) &&
                employeeService.isUsernameExists(updatedEmployee.getUserName())) {
            result.rejectValue("userName", "error.employee", "Tên đăng nhập đã tồn tại");
        }

        // Kiểm tra trùng email
        if (!updatedEmployee.getEmail().equals(existingEmployee.getEmail()) &&
                employeeService.isEmailExists(updatedEmployee.getEmail())) {
            result.rejectValue("email", "error.employee", "Email đã tồn tại");
        }

        // Kiểm tra trùng SĐT
        if (!updatedEmployee.getPhoneNumber().equals(existingEmployee.getPhoneNumber()) &&
                employeeService.isPhoneNumberExists(updatedEmployee.getPhoneNumber())) {
            result.rejectValue("phoneNumber", "error.employee", "Số điện thoại đã tồn tại");
        }

        // Nếu có lỗi thì quay lại form
        if (result.hasErrors()) {
            return "admin/employee/edit";
        }

        // Gán lại role và id cũ
        updatedEmployee.setRole(existingEmployee.getRole());
        updatedEmployee.setIdUser(existingEmployee.getIdUser());

        // Cập nhật thông tin
        employeeService.updateEmployee(id, updatedEmployee);

        return "redirect:/admin/employee/index";
    }



@PostMapping("/admin/employee/create")
    public String adminemployeeAdd(
            @ModelAttribute("employee") @Valid User employee,
            BindingResult result,
            Model model) {

        // Kiểm tra trùng username
        if (employeeService.isUsernameExists(employee.getUserName())) {
            result.rejectValue("userName", "error.employee", "Tên đăng nhập đã tồn tại");
        }
        // Kiểm tra trùng email
        if (employeeService.isEmailExists(employee.getEmail())) {
            result.rejectValue("email", "error.employee", "Email đã tồn tại");
        }
        // Kiểm tra trùng SĐT
        if (employeeService.isPhoneNumberExists(employee.getPhoneNumber())) {
            result.rejectValue("phoneNumber", "error.employee", "Số điện thoại đã tồn tại");
        }

        // Nếu có lỗi thì quay lại form
        if (result.hasErrors()) {
            return "admin/employee/create";
        }

        // Không có lỗi => Lưu
        employee.setRole("employee");
        employeeService.addEmployee(employee);
        return "redirect:/admin/employee/index";
    }

    // Trang xem chi tiết nhân viên
        @GetMapping("/admin/employee/detail/{id}")
        public String adminEmployeeDetail(@PathVariable("id") Long id, Model model) {
            User employee = employeeService.getEmployeeById(id);
            model.addAttribute("employee", employee);
            return "admin/employee/detail";
        }

         // xóa cus theo id
    @GetMapping("/admin/employee/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/admin/employee/index";
    }

}
