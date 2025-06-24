package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService{
    @Autowired
    private IUserRepository userRepository;



    @Override
    public List<User> getAllEmployees() {
        return List.of();
    }

    @Override
    public User getEmployeeById(int id) {
        return null;
    }

    @Override
    public void addEmployee(User employee) {

    }

    @Override
    public void updateEmployee(int id, User employee) {

    }

    @Override
    public void deleteEmployee(int id) {

    }
}
