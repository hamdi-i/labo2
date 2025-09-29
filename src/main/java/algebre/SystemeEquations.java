package algebre;

import java.util.ArrayList;
import java.util.List;

public class SystemeEquations {
    private final List<Equation> equations = new ArrayList<>();

    public void lire(String source) {
        if (source == null) throw new IllegalArgumentException("Source vide");
        String[] lines = source.split("\\r?\\n");
        equations.clear();
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            Equation eq = new Equation();
            eq.lire(trimmed);
            equations.add(eq);
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
        Matrice m = new Matrice(n, 4); // 3 variables + constante
        for (int i = 0; i < n; i++) {
            double[] c = equations.get(i).getCoeffs();
            m.set(i, 0, c[0]);
            m.set(i, 1, c[1]);
            m.set(i, 2, c[2]);
            m.set(i, 3, equations.get(i).getConstant());
        }
        return m;
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