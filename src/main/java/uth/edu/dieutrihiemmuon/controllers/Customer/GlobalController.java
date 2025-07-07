package uth.edu.dieutrihiemmuon.controllers.Customer;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import uth.edu.dieutrihiemmuon.dto.UserDTO;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.services.IUserService;

import java.util.List;

@ControllerAdvice
public class GlobalController {
    @Autowired
    private IUserService userService;
    @ModelAttribute
    public void addFullnameToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            UserDTO userDTO = userService.getUserByUserName(authentication.getName());
            model.addAttribute("userDTO", userDTO);
        }
    }
}
