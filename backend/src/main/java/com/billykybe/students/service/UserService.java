package com.billykybe.students.service;

import com.billykybe.students.entity.UserAccount;
import com.billykybe.students.model.PasswordModel;
import com.billykybe.students.model.UserModel;
import com.billykybe.students.payload.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<MessageResponse> createAccount(UserModel userModel, HttpServletRequest request);

    ResponseEntity<MessageResponse> login(UserModel userModel, HttpServletRequest request);

    ResponseEntity<MessageResponse> forgotPassword(PasswordModel passwordModel, HttpServletRequest request);

    ResponseEntity<MessageResponse> changePassword(PasswordModel passwordModel, HttpServletRequest request);

    ResponseEntity<MessageResponse> verifyAccount(String token, HttpServletRequest request);

    ResponseEntity<MessageResponse> logoutAccount(HttpServletRequest request);

    void saveTokenToUser(String token, UserAccount userAccount);

    void saveTokenToUser(UserAccount account, String token);

    ResponseEntity<MessageResponse> changeForgotPassword(String token,PasswordModel passwordModel, HttpServletRequest request);
}
