package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.UserDTO;

public interface IUserService {
  public UserDTO getUserByUserName(String userName);
}
