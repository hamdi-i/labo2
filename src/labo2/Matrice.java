package labo2;

/*
 * Classe représentant une matrice. Composée d'une liste
 * de vecteurs. Chaque rangée de la matrice est un vecteur.
 */
public class Matrice {

	private Vecteur[] lignes;

	/* Constructeur par défaut (protégé) pour permettre l'héritage */
	protected Matrice() {
		this.lignes = new Vecteur[0];
	}

	public Matrice(double[][] dat) {
		assign(dat);
	}

	/* Méthode protégée pour initialiser/assigner la matrice depuis un tableau */
	protected void assign(double[][] dat) {
		if (dat == null || dat.length == 0) {
			throw new IllegalArgumentException("Dimensions inadmissibles");
		}
		int cols = dat[0].length;
		for (int i = 1; i < dat.length; i++) {
			if (dat[i].length != cols) {
				throw new IllegalArgumentException("Dimensions inadmissibles");
			}
		}
		lignes = new Vecteur[dat.length];
		for (int i = 0; i < dat.length; i++) {
			lignes[i] = new Vecteur(dat[i]);
		}
	}

	/* Nombre de lignes */
	public int lignes() {
		return lignes.length;
	}

	/* Nombre de colonnes */
	public int colonnes() {
		return lignes.length == 0 ? 0 : lignes[0].taille();
	}

	/* Accès à un coefficient */
	public double get(int i, int j) {
		return lignes[i].get(j);
	}

	public void set(int i, int j, double value) {
		lignes[i].set(j, value);
	}

	/* Sous-matrice des 'l' premières lignes et 'c' premières colonnes */
	public Matrice sousMatrice(int l, int c) {
		if (l < 0 || c < 0 || l > lignes() || c > colonnes()) {
			throw new IllegalArgumentException("Dimensions inadmissibles");
		}
		double[][] dat = new double[l][c];
		for (int i = 0; i < l; i++) {
			for (int j = 0; j < c; j++) {
				dat[i][j] = get(i, j);
			}
		}
		return new Matrice(dat);
	}

	/* Copie vers tableau java */
	public double[][] coefficientsEnTableau() {
		double[][] out = new double[lignes()][colonnes()];
		for (int i = 0; i < lignes(); i++) {
			for (int j = 0; j < colonnes(); j++) {
				out[i][j] = get(i, j);
			}
		}
		return out;
	}

	/* Fabrique matrice nulle */
	public static Matrice creerMatriceNulle(int l, int c) {
		if (l < 0 || c < 0) {
			throw new IllegalArgumentException("Dimensions inadmissibles");
		}
		double[][] dat = new double[l][c];
		return new Matrice(dat);
	}

	/* Fabrique matrice identité */
	public static Matrice creerMatriceIdentite(int taille) {
		if (taille < 0) {
			throw new IllegalArgumentException("Dimensions inadmissibles");
		}
		double[][] dat = new double[taille][taille];
		for (int i = 0; i < taille; i++) {
			dat[i][i] = 1.0;
		}
		return new Matrice(dat);
	}

	/*
	 * Élimination Gaussienne. Implémentation suivant le pseudo-code classique.
	 * Modifie la matrice en place.
	 */
	public void Gauss() {
		int noLigne = 0;
		for (Vecteur ligne : lignes) {
			double pivot = ligne.get(noLigne);
			if (pivot != 0) {
				double pivotInverse = 1.0 / pivot;
				for (int i = 0; i < ligne.taille(); i++) {
					ligne.set(i, ligne.get(i) * pivotInverse);
				}
			}

			for (Vecteur ligneElim : lignes) {
				if (ligneElim != ligne) {
					double f = ligneElim.get(noLigne);
					for (int i = 0; i < ligneElim.taille(); ++i) {
						ligneElim.set(i, ligneElim.get(i) - f * ligne.get(i));
					}
				}
			}
			noLigne++;
		}
	}

	public String toString() {
		String res = "";
		for (int i = 0; i < lignes.length; i++) {
			res += lignes[i].toString();
			if (i < lignes.length - 1) {
				res += "\n";
			}
		}
		return res;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Matrice)) return false;
		Matrice o = (Matrice) other;
		if (o.lignes() != this.lignes() || o.colonnes() != this.colonnes()) return false;
		for (int i = 0; i < lignes(); i++) {
			for (int j = 0; j < colonnes(); j++) {
				if (!UtilitairesAlgebre.egaliteDoublePrecision(this.get(i, j), o.get(i, j), UtilitairesAlgebre.EPSILON)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 1;
		for (int i = 0; i < lignes(); i++) {
			for (int j = 0; j < colonnes(); j++) {
				long bits = Double.doubleToLongBits(get(i, j));
				result = 31 * result + (int) (bits ^ (bits >> 32));
			}
		}
		result = 31 * result + lignes();
		result = 31 * result + colonnes();
		return result;
	}

}
