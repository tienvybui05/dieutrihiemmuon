package uth.edu.dieutrihiemmuon.services;


import java.util.List;

import uth.edu.dieutrihiemmuon.models.User;

public interface IEmployeeService {

    List<User> getAllEmployees();
    User getEmployeeById(long id);
    void addEmployee(User employee);
    void updateEmployee(long id, User employee);
    void deleteEmployee(long id);
    boolean isUsernameExists(String username);
    boolean isEmailExists(String email);
    boolean isPhoneNumberExists(String phoneNumber);

}
