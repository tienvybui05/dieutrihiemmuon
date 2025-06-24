package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.dieutrihiemmuon.models.User;

public interface IUserRepository extends JpaRepository<User, Integer> {


}
