package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.RegisterDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements ICustomerService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        customer.setPassWord(passwordEncoder.encode(customer.getPassWord()));
        customer.setImage("default.jpg");
        customer.setRole("CUSTOMER");
        userRepository.save(customer);
    }

    @Override
    public boolean isPhoneNumberExists(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber) != null;
    }

    @Override
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userRepository.findByUserName(username) != null;
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
    @Override
    public boolean addAccount(RegisterDTO registerDTO) {
        try {
            User user = new User();
            user.setFullName(registerDTO.getFullName());
            user.setAddress(registerDTO.getAddress());
            user.setPhoneNumber(registerDTO.getPhoneNumber());
            user.setPassWord(passwordEncoder.encode(registerDTO.getPassWord()));
            user.setUserName(registerDTO.getUserName());
            user.setEmail(registerDTO.getEmail());
            user.setDateOfBirth(registerDTO.getDateOfBirth());
            user.setGender(registerDTO.getGender());
            user.setImage("default.jpg");
            user.setRole("CUSTOMER");
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm tài khoản: "+ e);
            return false;
        }
    }
}
