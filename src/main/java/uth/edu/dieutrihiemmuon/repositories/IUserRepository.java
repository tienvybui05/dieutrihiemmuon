package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.dieutrihiemmuon.models.User;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(String role);


}
