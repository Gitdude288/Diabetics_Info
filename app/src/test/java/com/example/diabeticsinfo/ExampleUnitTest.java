package com.example.diabeticsinfo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    int bls = 150;
    int pillSupply = 90;
    int sBP = 120;
    int dBP = 70;
    @Test
        public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void reasonableBloodSugar(){
        assertFalse(bls > 500 || bls <50);
    }
    @Test
    public void pillCountTest(){
        assertFalse(pillSupply > 540 || pillSupply < 0);
    }
    @Test
    public void bpNumberTest(){
        assertFalse(dBP > sBP);
    }
}
