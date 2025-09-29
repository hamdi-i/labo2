package algebre;

import java.util.Arrays;

public class Matrice {
    private final int lignes;
    private final int colonnes;
    private final double[][] data;

    public Matrice(int lignes, int colonnes) {
        if (lignes &lt; 0 || colonnes &lt; 0) {
            throw new IllegalArgumentException("Dimensions inadmissibles");
        }
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.data = new double[lignes][colonnes];
    }

    public Matrice(double[][] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Dimensions inadmissibles");
        }
        int cols = values[0].length;
        for (double[] row : values) {
            if (row.length != cols) {
                throw new IllegalArgumentException("Dimensions inadmissibles");
            }
        }
        this.lignes = values.length;
        this.colonnes = cols;
        this.data = new double[lignes][colonnes];
        for (int i = 0; i &lt; lignes; i++) {
            this.data[i] = Arrays.copyOf(values[i], colonnes);
        }
    }

    public static Matrice creerMatriceNulle(int lignes, int colonnes) {
        return new Matrice(lignes, colonnes);
    }

    public static Matrice creerMatriceIdentite(int taille) {
        Matrice m = new Matrice(taille, taille);
        for (int i = 0; i &lt; taille; i++) {
            m.data[i][i] = 1.0;
        }
        return m;
    }

    public int lignes() {
        return lignes;
    }

    public int colonnes() {
        return colonnes;
    }

    public double get(int i, int j) {
        return data[i][j];
    }

    public void set(int i, int j, double value) {
        data[i][j] = value;
    }

    public Matrice sousMatrice(int lignes, int colonnes) {
        if (lignes &lt; 0 || colonnes &lt; 0 || lignes &gt; this.lignes || colonnes &gt; this.colonnes) {
            throw new IllegalArgumentException("Dimensions inadmissibles");
        }
        Matrice m = new Matrice(lignes, colonnes);
        for (int i = 0; i &lt; lignes; i++) {
            System.arraycopy(this.data[i], 0, m.data[i], 0, colonnes);
        }
        return m;
    }

    public double[][] coefficientsEnTableau() {
        double[][] out = new double[lignes][colonnes];
        for (int i = 0; i &lt; lignes; i++) {
            out[i] = Arrays.copyOf(data[i], colonnes);
        }
        return out;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i &lt; lignes; i++) {
            sb.append("[");
            for (int j = 0; j &lt; colonnes; j++) {
                sb.append(Double.toString(data[i][j]));
                if (j &lt; colonnes - 1) sb.append(" ");
            }
            sb.append("]");
            if (i &lt; lignes - 1) sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Matrice)) return false;
        Matrice o = (Matrice) other;
        if (o.lignes != this.lignes || o.colonnes != this.colonnes) return false;
        for (int i = 0; i &lt; lignes; i++) {
            for (int j = 0; j &lt; colonnes; j++) {
                if (!UtilitairesAlgebre.egaliteDoublePrecision(this.data[i][j], o.data[i][j], UtilitairesAlgebre.EPSILON)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i &lt; lignes; i++) {
            for (int j = 0; j &lt; colonnes; j++) {
                long bits = Double.doubleToLongBits(data[i][j]);
                result = 31 * result + (int)(bits ^ (bits &gt;&gt; 32));
            }
        }
        result = 31 * result + lignes;
        result = 31 * result + colonnes;
        return result;
    }
}