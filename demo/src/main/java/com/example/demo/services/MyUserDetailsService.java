package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

//    public UserDetails loadUserByUsername(String email)
//            throws UsernameNotFoundException {
//
//        User user = userRepository.findById();
//        if (user == null) {
//            throw new UsernameNotFoundException(
//                    "No user found with username: "+ email);
//        }
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//        return  new org.springframework.security.core.userdetails.User
//                (user.getEmail(), user.getPassword().toLowerCase(), enabled);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }


}
