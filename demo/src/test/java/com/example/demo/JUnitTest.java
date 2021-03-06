package com.example.demo;


import com.example.demo.utils.RandomString;
import org.junit.jupiter.api.*;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitTest {

    public boolean isNumberEven(Integer number) {
        return number % 2 == 0;
    }

    @BeforeAll
    public static void setup() {
        System.out.println("Start testing...");
    }

    @Test
    void firstTest() {
        boolean result = isNumberEven(8);

        Assertions.assertTrue(result);
    }

    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        String test = null;
        assertThrows(NullPointerException.class, () -> {
            test.length();
        });
    }

    @Test
    public void whenAssertingNotNull_thenTrue() {
        Object dog = new Object();

        assertNotNull(dog, () -> "The dog should not be null");
    }

    @Test
    public void whenAssertingSameObject_thenSuccessfull() {
        String language = "Java";
        Optional<String> optional = Optional.of(language);

        assertSame(language, optional.get());
    }

    @Disabled
    @Test
    public void whenFailingATest_thenFailed() {
        // Test not completed
        fail("FAIL - test not completed");
    }

    @Test
    public void givenMultipleAssertion_whenAssertingAll_thenOK() {
        assertAll(
                "heading",
                () -> assertEquals(4, 2 * 2, "4 is 2 times 2"),
                () -> assertEquals("java", "JAVA".toLowerCase()),
                () -> assertEquals(null, null, "null is equal to null")
        );
    }

    @Test
    public void whenAssertingEqualityListOfStrings_thenEqual() {
        List<String> expected = Arrays.asList("Java", "\\d+", "JUnit");
        List<String> actual = Arrays.asList("Java", "11", "JUnit");
        assertLinesMatch(expected, actual);
    }

    @AfterAll
    public static void finish() {
        System.out.println("End testing...");
    }

    @Test
    public void testdate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
    }
}
