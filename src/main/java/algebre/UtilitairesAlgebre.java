package labo2;

public class UtilitairesAlgebre {

    public static final double EPSILON = 1e-9;

    public static boolean egaliteDoublePrecision(double a, double b, double epsilon) {
        return Math.abs(a - b) <= epsilon;
    }

    /**
     * Retourne la forme réduite par lignes (RREF) de la matrice augmentée.
     * Suppose une matrice augmentée de taille n x (n+1).
     */
    public static Matrice rref(Matrice aug) {
        int n = aug.lignes();
        int m = aug.colonnes();
        Matrice res = new Matrice(aug.coefficientsEnTableau());

        for (int i = 0; i < n; i++) {
            // Trouver le pivot pour la colonne i
            int pivotRow = i;
            double maxAbs = Math.abs(res.get(pivotRow, i));
            for (int r = i + 1; r < n; r++) {
                double val = Math.abs(res.get(r, i));
                if (val > maxAbs) {
                    maxAbs = val;
                    pivotRow = r;
                }
            }
            if (Math.abs(res.get(pivotRow, i)) < EPSILON) {
                throw new IllegalArgumentException("Système non résoluble (pivot nul)");
            }

            // Échanger lignes i et pivotRow
            if (pivotRow != i) {
                for (int c = 0; c < m; c++) {
                    double tmp = res.get(i, c);
                    res.set(i, c, res.get(pivotRow, c));
                    res.set(pivotRow, c, tmp);
                }
            }

            // Normaliser la ligne i
            double pivot = res.get(i, i);
            for (int c = 0; c < m; c++) {
                res.set(i, c, res.get(i, c) / pivot);
            }

            // Éliminer la colonne i pour toutes les autres lignes
            for (int r = 0; r < n; r++) {
                if (r == i) continue;
                double factor = res.get(r, i);
                if (Math.abs(factor) > EPSILON) {
                    for (int c = 0; c < m; c++) {
                        res.set(r, c, res.get(r, c) - factor * res.get(i, c));
                    }
                }
            }
        }
        return res;
    }

    /**
     * Résout un système linéaire exprimé sous forme de matrice augmentée (n x (n+1)).
     * Retourne le vecteur solution.
     */
    public static Vecteur solveAugmentedSystem(Matrice aug) {
        Matrice rref = rref(aug);
        int n = rref.lignes();
        int m = rref.colonnes();
        if (m != n + 1) {
            throw new IllegalArgumentException("La matrice doit être augmentée (n x (n+1)).");
        }
        Vecteur solution = new Vecteur(n);
        for (int i = 0; i < n; i++) {
            // Vérifier que la partie gauche est identité
            for (int j = 0; j < n; j++) {
                double expected = (i == j) ? 1.0 : 0.0;
                if (!egaliteDoublePrecision(rref.get(i, j), expected, EPSILON)) {
                    throw new IllegalArgumentException("Système non résoluble (pas de forme identité).");
                }
            }
            solution.set(i, rref.get(i, m - 1));
        }
        return solution;
    }

    public static void main(String[] args) {
        // Exemple: système 3x + 5y - 3z = 15 ; 7x + 10y + 1z = 2 ; -3x + 2y - 5z = 6
        double[][] tableau = new double[][]{
                {3, 5, -3, 15},
                {7, 10, 1, 2},
                {-3, 2, -5, 6}
        };
        Matrice aug = new Matrice(tableau);
        Matrice rref = rref(aug);
        System.out.println("RREF:");
        System.out.println(rref.toString());
        Vecteur sol = solveAugmentedSystem(aug);
        System.out.println("Solution:");
        System.out.println("x = " + sol.get(0));
        System.out.println("y = " + sol.get(1));
        System.out.println("z = " + sol.get(2));
    }
}