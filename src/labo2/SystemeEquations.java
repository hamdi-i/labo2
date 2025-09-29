package labo2;

import java.util.ArrayList;
import java.util.List;

/*
 * SystemeEquations qui hérite de Matrice :
 * - Stocke la liste des équations pour affichage/égalité logique.
 * - Met à jour la matrice (via assign) lors de la lecture.
 */
public class SystemeEquations extends Matrice {
    private final List<Equation> equations = new ArrayList<>();

    public SystemeEquations() {
        super();
    }

    public void lire(String source) {
        if (source == null) throw new IllegalArgumentException("Source vide");
        String[] lines = source.split("\\r?\\n");
        equations.clear();
        List<double[]> rows = new ArrayList<>();
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            Equation eq = new Equation();
            eq.lire(trimmed);
            equations.add(eq);
            double[] c = eq.getCoeffs();
            rows.add(new double[]{c[0], c[1], c[2], eq.getConstant()});
        }
        if (!rows.isEmpty()) {
            double[][] dat = rows.toArray(new double[0][]);
            assign(dat); // met à jour la matrice héritée
        }
    }

    public int taille() {
        return equations.size();
    }

    public Equation get(int i) {
        return equations.get(i);
    }

    public Matrice matriceAugmentee() {
        int n = equations.size();
        double[][] dat = new double[n][4]; // 3 variables + constante
        for (int i = 0; i < n; i++) {
            double[] c = equations.get(i).getCoeffs();
            dat[i][0] = c[0];
            dat[i][1] = c[1];
            dat[i][2] = c[2];
            dat[i][3] = equations.get(i).getConstant();
        }
        return new Matrice(dat);
    }

    public Vecteur resoudre() {
        Matrice aug = matriceAugmentee();
        return UtilitairesAlgebre.solveAugmentedSystem(aug);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < equations.size(); i++) {
            sb.append(equations.get(i).toString());
            if (i < equations.size() - 1) sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof SystemeEquations)) return false;
        SystemeEquations o = (SystemeEquations) other;
        if (o.equations.size() != this.equations.size()) return false;
        for (int i = 0; i < equations.size(); i++) {
            if (!this.equations.get(i).equals(o.equations.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (Equation eq : equations) {
            result = 31 * result + eq.hashCode();
        }
        return result;
    }
}