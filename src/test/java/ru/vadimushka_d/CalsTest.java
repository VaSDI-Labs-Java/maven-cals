package ru.vadimushka_d;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class CalsTest
        extends TestCase
{

    public CalsTest(String name) {
        super(name);
    }

    public void testSum() throws Exception {
        double result = Cals.Sum(3,3.2);

        assertEquals(6.2, result);
    }

    public void testSubtraction() throws Exception{
        double result = Cals.Subtraction(8,3.2);

        assertEquals(4.8, result);
    }

    public void testMultiplication() throws Exception{
        double result = Cals.Multiplication(2,5.2);

        assertEquals(10.4, result);
    }

    public void testDivisionFloat() throws Exception{
        double result = Cals.Division(21.3,3.0);

        assertEquals(7.1, result);
    }
    
    public void testRemainder() throws Exception{
        double result = Cals.Remainder(21,6);

        assertEquals(3.0, result);
    }

    public void testPow() throws Exception{
        double result = Cals.Pow(4.0,6.0);

        assertEquals(4096.0, result);
    }
}
