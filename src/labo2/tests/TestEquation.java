package labo2;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestEquation {

    @Test
    public void testLireEtToString() {
        Equation eq = new Equation();
        eq.lire("3x + 5y -2z = 0");
        assertEquals("3.0x + 5.0y - 2.0z = 0.0", eq.toString());

        double[] c = eq.getCoeffs();
        assertEquals(3.0, c[0], 0.0);
        assertEquals(5.0, c[1], 0.0);
        assertEquals(-2.0, c[2], 0.0);
        assertEquals(0.0, eq.getConstant(), 0.0);
    }

    @Test
    public void testEquals() {
        Equation e1 = new Equation(new double[]{3, 5, -2}, 0);
        Equation e2 = new Equation(new double[]{3, 5 + 1e-10, -2}, 0 + 1e-10);
        assertTrue(e1.equals(e2));
        assertEquals(e1, e2);

        Equation e3 = new Equation(new double[]{3, 5 + 1e-3, -2}, 0);
        assertFalse(e1.equals(e3));
        assertNotEquals(e1, e3);
    }
}