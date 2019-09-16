package com.example.demo.services;

import com.example.demo.entity.AppRole;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AppUser getUserByName(String name) {
        return null;
    }

    @Override
    public AppUser saveOrUpdateUser(AppUser user) {

        if (user != null){

            user.setEncryptedPassword(EncrytedPasswordUtils.encrytePassword(user.getEncryptedPassword()));
            userRepository.save(user);

//            Optional<AppRole> appRole = roleRepository.findById(2L);
//            UserRole userRole = new UserRole();
//            appRole.ifPresent(userRole::setAppRole);
//            userRole.setAppUser(user);
//            userRoleRepository.save(userRole);

            return user;
        } else {
            throw new RuntimeException("User can't be null");
        }
    }
}
