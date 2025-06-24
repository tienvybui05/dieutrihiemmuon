package uth.edu.dieutrihiemmuon.services;


import uth.edu.dieutrihiemmuon.models.User;

import java.util.List;

public interface IEmployeeService {

    List<User> getAllEmployees();
    User getEmployeeById(long id);
    void addEmployee(User employee);
    void updateEmployee(long id, User employee);
    void deleteEmployee(long id);

}
