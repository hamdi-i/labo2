package algebre;

import java.util.Arrays;

public class Vecteur {
    private final double[] data;

    public Vecteur(int taille) {
        if (taille &lt; 0) {
            throw new IllegalArgumentException("Dimensions inadmissibles");
        }
        this.data = new double[taille];
    }

    public Vecteur(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Dimensions inadmissibles");
        }
        this.data = Arrays.copyOf(values, values.length);
    }

    public static Vecteur creerVecteurNul(int taille) {
        return new Vecteur(taille);
    }

    public int taille() {
        return data.length;
    }

    public double get(int i) {
        return data[i];
    }

    public void set(int i, double value) {
        data[i] = value;
    }

    public Vecteur sousVecteur(int taille) {
        if (taille &lt; 0 || taille &gt; data.length) {
            throw new IllegalArgumentException("Dimensions inadmissibles");
        }
        return new Vecteur(Arrays.copyOf(this.data, taille));
    }

    public double[] coefficientsEnTableau() {
        return Arrays.copyOf(data, data.length);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i &lt; data.length; i++) {
            sb.append(Double.toString(data[i]));
            if (i &lt; data.length - 1) {
                sb.append(" ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vecteur)) return false;
        Vecteur o = (Vecteur) other;
        if (o.data.length != this.data.length) return false;
        for (int i = 0; i &lt; data.length; i++) {
            if (!UtilitairesAlgebre.egaliteDoublePrecision(this.data[i], o.data[i], UtilitairesAlgebre.EPSILON)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        // Not strictly consistent with tolerance-based equals; adequate for tests/use here.
        int result = 1;
        for (double v : data) {
            long bits = Double.doubleToLongBits(v);
            result = 31 * result + (int)(bits ^ (bits &gt;&gt; 32));
        }
        return result;
    }
}