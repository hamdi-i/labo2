package algebre;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestSystemeEquations {

    @Test
    public void testLireEtToStringEtSolve() {
        String src =
                "3x + 5y - 3z = 15\n" +
                "7x + 10y + 1z = 2\n" +
                "-3x + 2y -5z = 6";
        SystemeEquations sys = new SystemeEquations();
        sys.lire(src);

        String expectedToString =
                "3.0x + 5.0y - 3.0z = 15.0\n" +
                "7.0x + 10.0y + 1.0z = 2.0\n" +
                "-3.0x + 2.0y - 5.0z = 6.0";
        assertEquals(expectedToString, sys.toString());

        Matrice rref = UtilitairesAlgebre.rref(sys.matriceAugmentee());
        String expectedRREF =
                "[1.0 0.0 0.0 4.15625]\n" +
                "[0.0 1.0 0.0 -2.25]\n" +
                "[0.0 0.0 1.0 -4.59375]";
        assertEquals(expectedRREF, rref.toString());

        Vecteur sol = sys.resoudre();
        assertEquals(4.15625, sol.get(0), 1e-12);
        assertEquals(-2.25, sol.get(1), 1e-12);
        assertEquals(-4.59375, sol.get(2), 1e-12);
    }

    @Test
    public void testEquals() {
        SystemeEquations a = new SystemeEquations();
        a.lire("3x + 5y -2z = 0\n1x + 0y + 0z = 1");

        SystemeEquations b = new SystemeEquations();
        b.lire("3x + 5y -2z = 0\n1x + 0y + 0z = 1");
        assertEquals(a, b);

        SystemeEquations c = new SystemeEquations();
        c.lire("3x + 5y -2z = 0\n0x + 1y + 0z = 1");
        assertNotEquals(a, c);
    }
}