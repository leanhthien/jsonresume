package com.example.demo;

//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquationTest {

    private static final String VO_NGHIEM = "Phuong trinh vo nghiem";
    private static final String CO_NGHIEM = "Phuong trinh co nghiem";
    private static final String VO_SO_NGHIEM = "Phuong trinh co vo so nghiem";

    public String solveEquation(int first, int second, int third) {

        if (first == 0) {
            if (second == 0 && third != 0) {
                System.out.print(VO_NGHIEM);
                return VO_NGHIEM;
            }
            else if (second == 0) {
                System.out.print(VO_SO_NGHIEM);
                return VO_SO_NGHIEM;
            }
            else {
                double root = - third / (double) second;
                System.out.print(CO_NGHIEM + " la: " + root);
                return CO_NGHIEM;
            }
        }
        else {
            float delta = second * second - 4 * first * third;

            if (delta < 0) {
                System.out.print(VO_NGHIEM);
                return VO_NGHIEM;
            }
            else if (delta == 0) {
                System.out.print(CO_NGHIEM + " la: " + - second / 2 * first);
                return CO_NGHIEM;
            }
            else {
                double first_root = (- second + Math.sqrt(delta)) / 2 * first;
                double second_root = (- second - Math.sqrt(delta)) / 2 * first;
                System.out.print(CO_NGHIEM + " la: " + first_root + " va " + second_root);
                return CO_NGHIEM;
            }
        }
    }

//    @BeforeAll
//    public static void setup() {
//        System.out.println("Start testing...");
//    }

    @Test
    public void voSoNghiemBacNhat () {
        assertEquals(VO_SO_NGHIEM, solveEquation(0,0,0), "Phuong trinh nay co vo so nghiem");
    }

    @Test
    public void voNghiemBacNhat () {
        assertEquals(VO_NGHIEM, solveEquation(0,0,1), "Phuong trinh nay vo nghiem");
    }

    @Test
    public void coNghiemBacNhat () {
        assertEquals(CO_NGHIEM, solveEquation(0,1,5), "Phuong trinh nay co nghiem");
    }

    @Test
    public void voNghiemBacHai () {
        assertEquals(VO_NGHIEM, solveEquation(1,2,3), "Phuong trinh nay vo nghiem");
    }

    @Test
    public void coNghiemBacHaiVoiDeltaBangKhong () {
        assertEquals(CO_NGHIEM, solveEquation(1,2,1), "Phuong trinh nay co nghiem");
    }

    @Test
    public void coNghiemBacHaiVoiDeltaBangLonHonKhong () {
        assertEquals(CO_NGHIEM, solveEquation(1,2,-3), "Phuong trinh nay co nghiem");
    }

//    @AfterAll
//    public static void finish() {
//        System.out.println("End testing...");
//    }

}
