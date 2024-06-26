package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Exception.InvalidPasswordException;
import com.exe.inventorymsystemserver.Exception.InvalidUserNameException;
import com.exe.inventorymsystemserver.Exception.UserNotFoundException;
import com.exe.inventorymsystemserver.Model.User;
import com.exe.inventorymsystemserver.Model.UserLogs;
import com.exe.inventorymsystemserver.Repository.IUserLogsRepository;
import com.exe.inventorymsystemserver.Repository.IUserRepository;
import com.exe.inventorymsystemserver.Service.IUserService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private final IUserLogsRepository iUserLogsRepository;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(IUserRepository userRepository, IUserLogsRepository userLogsRepository, JwtUtil jwtUtil){

        this.userRepository = userRepository;
        this.iUserLogsRepository = userLogsRepository;
        this.jwtUtil = jwtUtil;
    }

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

    public void saveUserLogs(UserLogs userLogs) {
        // Save the UserLogs entity using the JpaRepository's save method
        iUserLogsRepository.save(userLogs);
    }

    /*@Override
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
            throw new UserNotFoundException("User Not Found");
        }

        if(!dbUser.getPassword().equals(user.getPassword())){
            throw new InvalidPasswordException("Invalid Password");
        }

        // Set the fields before returning the user
        user.setUserId(dbUser.getUserId());
        user.setUserTypeId(dbUser.getUserTypeId());

        // Generate JWT
        String jwt = jwtUtil.generateJwt(dbUser.getUserId(), dbUser.getUserName());

        // Check if USER_LOGS entry already exists
        Optional<UserLogs> existingUserLogs = iUserLogsRepository.findByUser_UserId(dbUser.getUserId());

        if (existingUserLogs.isPresent()) {
            // Update existing record
            UserLogs userLogsToUpdate = existingUserLogs.get();
            userLogsToUpdate.setLoginDateTime(LocalDateTime.now());
            userLogsToUpdate.setActive(true);
            userLogsToUpdate.setJwToken(jwt);
            iUserLogsRepository.save(userLogsToUpdate);

            // Set userLogsId in the User entity
            user.setUserLogsId(userLogsToUpdate);
        } else {
            // Insert new record
            UserLogs userLogs = new UserLogs();
            userLogs.setUser(dbUser);
            userLogs.setLoginDateTime(LocalDateTime.now());
            userLogs.setActive(true);
            userLogs.setJwToken(jwt);
            iUserLogsRepository.save(userLogs);

            // Set userLogsId in the User entity
            user.setUserLogsId(userLogs);
        }

        return user;
    }*/

    @Override
    public Map<String, Object> Login(User user)
            throws InvalidUserNameException, UserNotFoundException, InvalidPasswordException {
        if (user.getUserName() == null) {
            throw new InvalidUserNameException("Please Enter User Name");
        }
        if (user.getPassword() == null) {
            throw new InvalidPasswordException("Please Enter Password");
        }

        User dbUser = findByUserName(user.getUserName());

        if (dbUser == null) {
            throw new UserNotFoundException("User Not Found");
        }

        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new InvalidPasswordException("Invalid Password");
        }

        // Set the fields before returning the user
        user.setUserId(dbUser.getUserId());
        user.setUserTypeId(dbUser.getUserTypeId());

        // Generate JWT
        String jwt = jwtUtil.generateJwt(dbUser.getUserId(), dbUser.getUserName());

        // Check if USER_LOGS entry already exists
        Optional<UserLogs> existingUserLogs = iUserLogsRepository.findByUser_UserId(dbUser.getUserId());

        if (existingUserLogs.isPresent()) {
            // Update existing record
            UserLogs userLogsToUpdate = existingUserLogs.get();
            userLogsToUpdate.setLoginDateTime(LocalDateTime.now());
            userLogsToUpdate.setActive(true);
            userLogsToUpdate.setJwToken(jwt);
            iUserLogsRepository.save(userLogsToUpdate);

            // Set userLogsId in the User entity
            user.setUserLogsId(userLogsToUpdate);
        } else {
            // Insert new record
            UserLogs userLogs = new UserLogs();
            userLogs.setUser(dbUser);
            userLogs.setLoginDateTime(LocalDateTime.now());
            userLogs.setActive(true);
            userLogs.setJwToken(jwt);
            iUserLogsRepository.save(userLogs);

            // Set userLogsId in the User entity
            user.setUserLogsId(userLogs);
        }

        // Return only necessary fields
        Map<String, Object> result = new HashMap<>();
        result.put("userId", user.getUserId());
        result.put("userName", user.getUserName());
        result.put("jwToken", jwt);

        return result;
    }

    // Method to check validity of jwtoken
    public boolean isTokenValid(String jwtToken) {
        try {
            String username = jwtUtil.extractUsername(jwtToken);

            Long userId = getUserIdByUsername(username);

            Optional<UserLogs> userLogsOptional = iUserLogsRepository.findByUser_UserId(userId);

            return userLogsOptional.map(userLogs -> userLogs.getJwToken().equals(jwtToken)).orElse(false);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return false;
        }
    }

    public Long getUserIdByUsername(String username) {
        // Query your database or user management system to get the user ID based on the username
        User user = userRepository.findByUserName(username);

        if (user != null) {
            return user.getUserId();
        } else {
            // Handle the case where the user with the given username is not found
            return null;
        }
    }
}