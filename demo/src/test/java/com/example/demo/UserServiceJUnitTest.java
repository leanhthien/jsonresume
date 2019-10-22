package com.example.demo;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Token;
import com.example.demo.services.*;
import com.example.demo.utils.EncrytedPasswordUtils;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceJUnitTest {

    private UserService userService;
    private final long TOTAL_USER_HAS_PRODUCT = 1L;
    private final long INVALID_ID = -1L;
    private final int MAX_NUMBER = 999999999;
    private final int MIN_NUMBER = 100000000;
    private String userName="dbadmin1";

    @BeforeAll
    public static void initAll() {
        System.out.println("Start testing UserService ...");
    }

    @BeforeEach
    public void init() {

        System.out.println("Init fields ...");

        userService = new UserServiceJdbcImpl();

    }

    @DisplayName("Test get user from username by getUserByName")
    @Test
    public void getUserFromName_fromGetUserByName() {

        AppUser appUser = userService.getUserByName(userName);

        assertEquals(userName, appUser.getUserName());
    }

    @DisplayName("Test get user from username by getUserByName")
    @Test
    public void createNewUser_fromGetUserByName() {

        String randomUsername = String.valueOf( (int) ((Math.random() * ((MAX_NUMBER - MIN_NUMBER) + 1)) + MIN_NUMBER));
        String password = EncrytedPasswordUtils.encryptPassword("123456");
        AppUser appUser = new AppUser(randomUsername, password);

        AppUser newAppUser = userService.saveOrUpdateUser(appUser);

        assertTrue(appUser.getUserName().equals(newAppUser.getUserName())
                && appUser.getEncryptedPassword().equals(newAppUser.getEncryptedPassword()));
    }

    @DisplayName("Test get user from username by getUserByName")
    @Test
    public void deleteUser_fromDeleteUser() {

        String result = userService.deleteUser(5L);

        assertEquals("Success", result);
    }

    @Test
    public void createToken_fromSetToken() {

        AppUser appUser = userService.getUserByName("test");

        Token token = userService.setToken(appUser);

        System.out.println("Token is: " + token.getToken());
    }

    @Test
    public void validateToken_fromValidateToken() {

        String token = "AuJHSxcx69z4arc3kHVFzFRNXv2srf7y";

        String status = userService.validateToken(token, 1L);

        System.out.println("Status: " + status);
    }

    @Test
    public void deleteToken_fromDeleteToken() {

        String token = "gucYCCha7wniNjrAQkB31dxe6fk18BjU";

        String status = userService.deleteToken(token);

        System.out.println("Status: " + status);
    }



    @AfterAll
    public static void tearDownAll() {
        System.out.println("End testing ProductService ...");
    }
}
