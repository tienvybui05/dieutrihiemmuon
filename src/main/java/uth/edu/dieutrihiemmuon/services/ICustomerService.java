package uth.edu.dieutrihiemmuon.services;


import uth.edu.dieutrihiemmuon.dto.RegisterDTO;
import uth.edu.dieutrihiemmuon.models.User;

import java.util.List;

public interface ICustomerService {
    // Define methods for employee-related operations
    // For example:
    List<User> getAllCustomers();
    User getCustomerById(Long id);
    void addCustomer(User customer);
    void updateCustomer(Long id, User customer);
    void deleteCustomer(Long id);
    boolean isUsernameExists(String username);
    boolean isEmailExists(String email);
    boolean isPhoneNumberExists(String phoneNumber);
    public boolean addAccount(RegisterDTO registerDTO);
    public User findByUsername(String username);
}
