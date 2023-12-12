package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUsers();
    User getUserById(Long userId);
    User saveUser(User user);
    void deleteUser(Long userId);

}
