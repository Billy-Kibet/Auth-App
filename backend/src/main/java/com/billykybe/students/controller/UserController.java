package com.billykybe.students.controller;

import com.billykybe.students.model.PasswordModel;
import com.billykybe.students.model.UserModel;
import com.billykybe.students.payload.MessageResponse;
import com.billykybe.students.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class UserController {

    @Autowired
    private UserService userService;


    // create account
    @PostMapping("/createAccount")
    public ResponseEntity<MessageResponse> createAccount(@RequestBody UserModel userModel, final HttpServletRequest request){
     return  userService.createAccount(userModel, request);
    }

    // login
    @PostMapping("/loginAccount")
    public ResponseEntity<MessageResponse> login(@RequestBody UserModel userModel, final HttpServletRequest request){
        return userService.login(userModel, request);
    }

    // forgot password
    @PostMapping("/forgotPassword")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestBody PasswordModel passwordModel, final HttpServletRequest request){

        return userService.forgotPassword(passwordModel, request);
    }

    // change password
    @PostMapping("/changePassword")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody PasswordModel passwordModel, final HttpServletRequest request){
        return userService.changePassword(passwordModel, request);
    }
    // change forgot password
    @PostMapping("/changeForgotPassword")
    public ResponseEntity<MessageResponse> changeForgotPassword(@RequestParam String token, @RequestBody PasswordModel passwordModel, final HttpServletRequest request){
        return userService.changeForgotPassword(token,passwordModel, request);
    }
    // verify account
    @PostMapping("/verifyAccount")
    public ResponseEntity<MessageResponse> verifyAccount(@RequestParam String token, final HttpServletRequest request){
        return userService.verifyAccount(token, request);
    }
    // Logout account
    @PostMapping("/logoutAccount")
    public ResponseEntity<MessageResponse> logoutAccount(final HttpServletRequest request){
        return userService.logoutAccount(request);
    }
}
