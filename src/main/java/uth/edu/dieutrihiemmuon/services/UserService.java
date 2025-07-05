package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.dto.UserDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IUserRepository;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        UserDTO userDTO = new UserDTO(user);
        return userDTO;
    }

    @Override
    public boolean checkPassword(String username, String password) {
        User user = userRepository.findByUserName(username);
        return passwordEncoder.matches(password, user.getPassWord());
    }

    @Override
    public void updatePassword(String username, String password) {
        User user = userRepository.findByUserName(username);
        user.setPassWord(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
