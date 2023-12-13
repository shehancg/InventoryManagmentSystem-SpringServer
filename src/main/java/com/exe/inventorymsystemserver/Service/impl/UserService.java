package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Exception.InvalidPasswordException;
import com.exe.inventorymsystemserver.Exception.InvalidUserNameException;
import com.exe.inventorymsystemserver.Exception.UserNotFoundException;
import com.exe.inventorymsystemserver.Model.User;
import com.exe.inventorymsystemserver.Repository.IUserRepository;
import com.exe.inventorymsystemserver.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {


    private final IUserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.DeleteUser(userId);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    @Override
    public User Login(User user)
        throws InvalidUserNameException, UserNotFoundException, InvalidPasswordException{

        if (user.getUserName() == null){
            throw new InvalidUserNameException("Please Enter User Name");
        }
        if (user.getPassword() == null){
            throw new InvalidUserNameException("Please Enter Password");
        }

        User dbUser = findByUserName(user.getUserName());

        if(dbUser == null){
            throw  new UserNotFoundException("User Not Found");
        }

        if(!dbUser.getPassword().equals(user.getPassword())){
            throw new InvalidPasswordException("Invalid Password");
        }

        // Set the fields before returning the user
        user.setUserId(dbUser.getUserId());
        user.setUserTypeId(dbUser.getUserTypeId());

        return user;
    }
}
