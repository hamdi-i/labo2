package algebre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMatrice {

    private Matrice aug;

    @Before
    public void setUp() {
        double[][] tableau = new double[][]{
                {3, 5, -3, 15},
                {7, 10, 1, 2},
                {-3, 2, -5, 6}
        };
        aug = new Matrice(tableau);
    }

    @Test
    public void testToString() {
        String expected = "[3.0 5.0 -3.0 15.0]\n[7.0 10.0 1.0 2.0]\n[-3.0 2.0 -5.0 6.0]";
        assertEquals(expected, aug.toString());
    }

    @Test
    public void testSousMatrice() {
        Matrice sub = aug.sousMatrice(2, 3);
        String expected = "[3.0 5.0 -3.0]\n[7.0 10.0 1.0]";
        assertEquals(expected, sub.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSousMatriceException() {
        aug.sousMatrice(4, 5);
    }

    @Test
    public void testCreerMatriceNulleEtIdentite() {
        Matrice nul = Matrice.creerMatriceNulle(2, 3);
        assertEquals("[0.0 0.0 0.0]\n[0.0 0.0 0.0]", nul.toString());

        Matrice id = Matrice.creerMatriceIdentite(3);
        assertEquals("[1.0 0.0 0.0]\n[0.0 1.0 0.0]\n[0.0 0.0 1.0]", id.toString());
    }

    @Test
    public void testEquals() {
        Matrice a = new Matrice(new double[][]{{1, 2}, {3, 4}});
        Matrice b = new Matrice(new double[][]{{1, 2 + 1e-10}, {3, 4}});
        assertTrue(a.equals(b));
        assertEquals(a, b);

        Matrice c = new Matrice(new double[][]{{1, 2 + 1e-3}, {3, 4}});
        assertFalse(a.equals(c));
        assertNotEquals(a, c);
    }

    @Test
    public void testRREFGauss() {
        Matrice rref = UtilitairesAlgebre.rref(aug);
        String expected =
                "[1.0 0.0 0.0 4.15625]\n" +
                "[0.0 1.0 0.0 -2.25]\n" +
                "[0.0 0.0 1.0 -4.59375]";
        assertEquals(expected, rref.toString());
    }
}