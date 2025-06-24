package uth.edu.dieutrihiemmuon.services;


import uth.edu.dieutrihiemmuon.models.User;

import java.util.List;

public interface IEmployeeService {
    // Define methods for employee-related operations
    // For example:
    List<User> getAllEmployees();
    User getEmployeeById(int id);
    void addEmployee(User employee);
    void updateEmployee(int id, User employee);
    void deleteEmployee(int id);
}
