package com.billykybe.students.config;

import com.billykybe.students.entity.UserAccount;
import com.billykybe.students.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<UserAccount> userAccount = userRepository.findByEmail(username);
      return userAccount.map(UserDetailsImpl::new).orElseThrow(()->new UsernameNotFoundException("User " + username + " not found"));
    }
}
