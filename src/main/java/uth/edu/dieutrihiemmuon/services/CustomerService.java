package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

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
