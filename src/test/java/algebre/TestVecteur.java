package labo2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestVecteur {

    private Vecteur v;

    @Before
    public void setUp() {
        v = new Vecteur(new double[]{3, 5, -3, 15});
    }

    @Test
    public void testToString() {
        assertEquals("[3.0 5.0 -3.0 15.0]", v.toString());
    }

    @Test
    public void testEquals() {
        Vecteur v1 = new Vecteur(new double[]{1.0, 2.0, 3.0});
        Vecteur v2 = new Vecteur(new double[]{1.0, 2.0 + 1e-10, 3.0});
        assertTrue(v1.equals(v2));
        assertEquals(v1, v2);

        Vecteur v3 = new Vecteur(new double[]{1.0, 2.0 + 1e-3, 3.0});
        assertFalse(v1.equals(v3));
        assertNotEquals(v1, v3);
    }

    @Test
    public void testSousVecteur() {
        Vecteur sv = v.sousVecteur(2);
        assertEquals("[3.0 5.0]", sv.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSousVecteurException() {
        v.sousVecteur(5);
    }

    @Test
    public void testCreerVecteurNul() {
        Vecteur nul = Vecteur.creerVecteurNul(3);
        assertEquals("[0.0 0.0 0.0]", nul.toString());
    }
}