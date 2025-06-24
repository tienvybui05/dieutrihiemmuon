package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements ICustomerService{

    @Autowired
    private IUserRepository userRepository;



    @Override
    public List<User> getAllCustomers() {
        return userRepository.findByRole("CUSTOMER");
    }

    @Override
    public User getCustomerById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void addCustomer(User customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Dữ liệu nhân viên không hợp lệ");
        }

        List<String> errors = new ArrayList<>();

        if (userRepository.findByUserName(customer.getUserName()) != null) {
            errors.add("Tên đăng nhập đã tồn tại");
        }
        if (userRepository.findByEmail(customer.getEmail()) != null) {
            errors.add("Email đã tồn tại");
        }
        if (userRepository.findByPhoneNumber(customer.getPhoneNumber()) != null) {
            errors.add("Số điện thoại đã tồn tại");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }

        customer.setRole("CUSTOMER");
        userRepository.save(customer);
    }

    @Override
    public void updateCustomer(Long id, User customer) {
        if (userRepository.existsById(id)) {
            customer.setIdUser(id); // đảm bảo ID đúng
            userRepository.save(customer);
        }

    }

    @Override
    public void deleteCustomer(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }
}
