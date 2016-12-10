package com.iyzico.service;

import com.iyzico.model.User;
import com.iyzico.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
/**
 * Date: 10/12/2016 Time: 13:00
 *
 * @author dogukan.ozturkan (https://github.com/dogukanozturkan)
 */

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void register(User userAccountData) {

        com.iyzico.dao.User user = new com.iyzico.dao.User();
        user.setFirstName(userAccountData.getFirstName());
        user.setLastName(userAccountData.getLastName());
        user.setEmail(userAccountData.getEmail());
        user.setPasswordSalt(UUID.randomUUID().toString());
        user.setPasswordHash(passwordEncoder.encode(userAccountData.getPassword() + user.getPasswordSalt()));

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findOneByEmail(email);
    }

    public com.iyzico.dao.User findByEmail(String email){
        com.iyzico.dao.User user = userRepository.findOneByEmail(email);

        return user;
    }

    public com.iyzico.dao.User findAndAuthenticateUser(String email, String providedPassword) {
        com.iyzico.dao.User user = userRepository.findOneByEmail(email);
        if (user == null) {
            return null;
        }

        String saltedPassword = providedPassword + user.getPasswordSalt();
        if (passwordEncoder.matches(saltedPassword, user.getPassword())) {
            return user;
        }

        return null;
    }

}
