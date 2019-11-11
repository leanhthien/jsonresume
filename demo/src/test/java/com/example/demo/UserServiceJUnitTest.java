package com.example.demo;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Token;
import com.example.demo.model.Response;
import com.example.demo.services.*;
import com.example.demo.utils.EncrytedPasswordUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.*;

import javax.crypto.SecretKey;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;

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
        String text = "AuJHSxcx69z4arc3kHVFzFRNXv2srf7y";

        Token token = userService.setToken(appUser);

        System.out.println("Token is: " + token.getToken());
    }

    @Test
    public void validateToken_fromValidateToken() {

        String token = "WGovbNDjd62PuUZaijs84rhCVDgGEG8A";

        Response<String> response = userService.validateToken(token);

        System.out.println("Status: " + response.getMessage());
    }

    @Test
    public void deleteToken_fromDeleteToken() {

        String token = "gucYCCha7wniNjrAQkB31dxe6fk18BjU";

        String status = userService.deleteToken(token);

        System.out.println("Status: " + status);
    }

    @Test
    public void testGenerateKeyFromJwt() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        byte[] keyBytes = key.getEncoded();

        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();

        SecretKey secretekey = Keys.hmacShaKeyFor(keyBytes);

        Jws<Claims> claim = Jwts.parser().setSigningKey(secretekey).parseClaimsJws(jws);

        //OK, we can trust this JWT

        String body = claim.getBody().getSubject();
        System.out.println("Encode key: " + Arrays.toString(keyBytes));
        System.out.println("Key: " + jws);
        System.out.println("data: " + body);
    }

    @Test
    public void testDescryptKeyFromJwt() {

//        byte[] keyBytes = [42, -94, 102, -48, 75, -84, -98, 122, 103, 44, -1, -93, -80, 54, -47, 96, -73, -78, -8, -11, 70, -98, 97, 21, -32, 11, -69, 25, -87, -43, -53, 98];
        String compactJws = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.s7IRre5Q7moNJM9hVYL2XhH98Y0WU36Cz_04aVtk5bQ";

        try {

            Jws<Claims> claim = Jwts.parser().setSigningKey("").parseClaimsJws(compactJws);

            //OK, we can trust this JWT

            String body = claim.getBody().getSubject();
            System.out.println("data: " + body);

        } catch (JwtException e) {

            //don't trust the JWT!
            System.out.println("Cannot parse token!");
        }

    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("End testing ProductService ...");
    }
}
