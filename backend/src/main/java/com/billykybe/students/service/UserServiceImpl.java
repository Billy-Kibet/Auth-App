package com.billykybe.students.service;

import com.billykybe.students.entity.*;
import com.billykybe.students.event.*;
import com.billykybe.students.model.PasswordModel;
import com.billykybe.students.model.UserModel;
import com.billykybe.students.payload.MessageResponse;
import com.billykybe.students.repository.ForgotPasswordTokenRepository;
import com.billykybe.students.repository.RoleRepository;
import com.billykybe.students.repository.UserRepository;
import com.billykybe.students.repository.VerificationTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private ForgotPasswordTokenRepository forgotPasswordTokenRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<MessageResponse> createAccount(UserModel userModel, HttpServletRequest request) {

        if (userRepository.existsByEmail(userModel.getEmail())){

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("User already exists"));

        }

        UserAccount userAccount = new UserAccount();

        userAccount.setFirstName(userModel.getFirstName());
        userAccount.setLastName(userModel.getLastName());
        userAccount.setEmail(userModel.getEmail());
        userAccount.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userAccount.setVerified(false); // by default the user account is not verified

        // ROLE_USER - user
        // ROLE_ADMIN - admin
        if (userModel.getRole().equals("admin")) {
            Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
            userAccount.setRole(roleAdmin);
        } else {
            Role roleUser = roleRepository.findByName(ERole.ROLE_USER);
            userAccount.setRole(roleUser);
        }

        String verificationUrl = "http://"+request.getServerName()+":"+request.getServerPort()+"/verifyAccount";
publisher.publishEvent(new RegistrationCompleteEvent(userRepository.save(userAccount),verificationUrl));







        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User Account Created"));
    }

    @Override
    public ResponseEntity<MessageResponse> login(UserModel userModel, HttpServletRequest request) {


        return null;
    }

    @Override
    public ResponseEntity<MessageResponse> forgotPassword(PasswordModel passwordModel, HttpServletRequest request) {

        try {

            if (!userRepository.existsByEmail(passwordModel.getEmail())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User does not exist"));
            }
           Optional<UserAccount> userAccount = userRepository.findByEmail(passwordModel.getEmail());

            // TODO: event
            String url = "http://"+request.getServerName()+":"+request.getServerPort()+"/changeForgotPassword";
publisher.publishEvent(new ForgotPasswordEvent(userAccount.get(),url));


            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Password reset email sent successfully to " + passwordModel.getEmail()));

        }catch (Exception e){
            log.error("Error : "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("An error occurred"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> changePassword(PasswordModel passwordModel, HttpServletRequest request) {
       try {
           Optional<UserAccount>userAccount = userRepository.findByEmail(request.getUserPrincipal().getName());

           if (passwordEncoder.matches(passwordModel.getOldPassword(),userAccount.get().getPassword())){
               userAccount.get().setPassword(passwordModel.getNewPassword());
               userRepository.save(userAccount.get());
               return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Password updated successfully"));
           }
           else {
               return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(new MessageResponse("Old password does not match"));
           }

       }catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)   .body(new MessageResponse("An error occurred while changing password"));
       }


    }

    @Override
    public ResponseEntity<MessageResponse> verifyAccount(String token, HttpServletRequest request) {

        if (!verificationTokenRepository.existsByToken(token) ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Token does not exist"));
        }

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if ((verificationToken.getExpirationDate().getTime()- Calendar.getInstance().getTime().getTime()) <=0)  {
            log.info(verificationToken.getExpirationDate().toString());
            log.info(new Date().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Token expired"));
        }

        UserAccount userAccount = verificationToken.getUserAccount();
        userAccount.setVerified(true);
        userRepository.save(userAccount);

       publisher.publishEvent(new VerificationCompleteEvent(verificationToken));

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User account verified successfully"));
    }

    @Override
    public ResponseEntity<MessageResponse> logoutAccount(HttpServletRequest request) {


        try {
            SecurityContextHolder.clearContext();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("Failed to clear context"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User "+request.getUserPrincipal().getName()+" Logged out successfully"));
    }

    @Override
    public void saveTokenToUser(String token, UserAccount userAccount) {

        VerificationToken verificationToken = new VerificationToken(userAccount,token);
        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public void saveTokenToUser(UserAccount account, String token) {


        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken(account,token);
        forgotPasswordTokenRepository.save(forgotPasswordToken);

    }

    @Override
    public ResponseEntity<MessageResponse> changeForgotPassword(String token, PasswordModel passwordModel, HttpServletRequest request) {

        if (!forgotPasswordTokenRepository.existsByToken(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Token does not exist"));

        }



        ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenRepository.findByToken(token);

        if ((forgotPasswordToken.getExpirationDate().getTime()- Calendar.getInstance().getTime().getTime()) <=0)  {

            forgotPasswordTokenRepository.delete(forgotPasswordToken);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Token expired"));
        }


        UserAccount userAccount = forgotPasswordToken.getUserAccount();
        userAccount.setPassword(passwordEncoder.encode(passwordModel.getNewPassword()));
        userRepository.save(userAccount);

        publisher.publishEvent(new ChangeForgotPasswordEvent(forgotPasswordToken));

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Password updated successfully"));
    }
}
