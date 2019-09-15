package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByName(String name) {
        return null;
    }

    @Override
    public User saveOrUpdateUser(User user) {

        if (user != null){
            userRepository.save(user);
            return user;
        } else {
            throw new RuntimeException("User can't be null");
        }
    }
}
