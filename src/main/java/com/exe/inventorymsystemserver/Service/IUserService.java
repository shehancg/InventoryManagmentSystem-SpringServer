package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Exception.InvalidPasswordException;
import com.exe.inventorymsystemserver.Exception.InvalidUserNameException;
import com.exe.inventorymsystemserver.Exception.UserNotFoundException;
import com.exe.inventorymsystemserver.Model.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUsers();
    User getUserById(Long userId);
    User saveUser(User user);
    void deleteUser(Long userId);

    User Login(User user)
        throws InvalidUserNameException, UserNotFoundException, InvalidPasswordException;
}
