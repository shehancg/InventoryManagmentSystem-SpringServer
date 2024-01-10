package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Exception.InvalidPasswordException;
import com.exe.inventorymsystemserver.Exception.InvalidUserNameException;
import com.exe.inventorymsystemserver.Exception.UserNotFoundException;
import com.exe.inventorymsystemserver.Model.User;
import com.exe.inventorymsystemserver.ResponseHandler.Response;
import com.exe.inventorymsystemserver.Service.IUserService;
import com.exe.inventorymsystemserver.Service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            user.setUserId(userId);
            User updatedUser = userService.saveUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        User existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public Response loginUser(@RequestBody User user){
        try {
            return Response.success(userService.Login(user));
        } catch (InvalidUserNameException invalidUserNameException) {
            return Response.fail(invalidUserNameException.getMessage());
        } catch (UserNotFoundException userNotFoundException) {
            return Response.fail(userNotFoundException.getMessage());
        } catch (InvalidPasswordException invalidPasswordException) {
            return Response.invalidLogin(invalidPasswordException.getMessage());
        }
    }

    // Check JWT Validity
    @GetMapping("/check")
    public Response checkTokenValidity(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Remove the "Bearer " prefix
            String jwtToken = extractJwtToken(authorizationHeader);

            if (jwtToken != null && userService.isTokenValid(jwtToken)) {
                return Response.success("VALID");
            } else {
                return Response.fail("INVALID");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("INTERNAL_SERVER_ERROR");
        }
    }

    private String extractJwtToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
