package com.example.demo.validation;

import com.example.demo.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;

 @Configuration
 @EnableWebSecurity
 public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

//     @Autowired
//     private MyUserDetailsService userDetailsService;
//
//     @Override
//     protected void configure(AuthenticationManagerBuilder auth)
//             throws Exception {
//         auth.userDetailsService(userDetailsService);
//     }

     @Override
     protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
         auth.inMemoryAuthentication()
           .withUser("user1").password(passwordEncoder().encode("123456")).roles("USER")
           .and()
           .withUser("user2").password(passwordEncoder().encode("123456")).roles("USER")
           .and()
           .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN");
     }

     @Override
     protected void configure(HttpSecurity http) throws Exception {
         http.authorizeRequests()
                 .antMatchers("/demo/**").permitAll()
                 .antMatchers("/view/**").permitAll()
                 .antMatchers("/products/**").authenticated()

           .and().formLogin();
         //   .loginPage("/").permitAll();
     }

     @Bean
     public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
     }
 }
