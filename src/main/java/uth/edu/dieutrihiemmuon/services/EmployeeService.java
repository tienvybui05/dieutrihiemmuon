package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService{
    @Autowired
    private IUserRepository userRepository;



    @Override
    public List<User> getAllEmployees() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().equals("EMPLOYEE"))
                .collect(Collectors.toList());
    }

    @Override
    public User getEmployeeById(long id) {
        return userRepository.findById(id)
                .filter(user -> user.getRole().equals("EMPLOYEE"))
                .orElse(null);
    }

    @Override
    public void addEmployee(User employee) {
        if (employee != null ) {
            // Kiểm tra xem nhân viên đã tồn tại hay chưa
            if (userRepository.findByUserName(employee.getUserName()) != null) {
                throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
            }
            if (userRepository.findByEmail(employee.getEmail()) != null) {
                throw new IllegalArgumentException("Email đã tồn tại");
            }
            if (userRepository.findByPhoneNumber(employee.getPhoneNumber()) != null) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại");
            }
            // Thiết lập role là EMPLOYEE
            employee.setRole("EMPLOYEE");
            userRepository.save(employee);
        } else {
            throw new IllegalArgumentException("Dữ liệu nhân viên không hợp lệ");
        }

    }

    @Override
    public void updateEmployee(long id, User employee) {
        User existingEmployee = userRepository.findById(id)
                .filter(user -> user.getRole().equals("EMPLOYEE"))
                .orElseThrow(() -> new IllegalArgumentException("Nhân viên không tồn tại"));

        // Kiểm tra trùng username
        User u1 = userRepository.findByUserName(employee.getUserName());
        if (u1 != null && u1.getIdUser() != id) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại!");
        }

        // Kiểm tra trùng email
        User u2 = userRepository.findByEmail(employee.getEmail());
        if (u2 != null && u2.getIdUser() != id) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }

        // Kiểm tra trùng số điện thoại
        User u3 = userRepository.findByPhoneNumber(employee.getPhoneNumber());
        if (u3 != null && u3.getIdUser() != id) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
        }

        // Cập nhật thông tin nhân viên
        existingEmployee.setUserName(employee.getUserName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhoneNumber(employee.getPhoneNumber());
        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setAddress(employee.getAddress());


        // Đảm bảo giữ nguyên role là EMPLOYEE
        existingEmployee.setRole("EMPLOYEE");

        userRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        User existingEmployee = userRepository.findById(id)
                .filter(user -> user.getRole().equals("EMPLOYEE"))
                .orElseThrow(() -> new IllegalArgumentException("Nhân viên không tồn tại"));

        // Xóa nhân viên
        userRepository.delete(existingEmployee);

    }
}
