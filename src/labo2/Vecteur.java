package labo2;

/*
 * Classe représentant un vecteur.
 */
public class Vecteur {

	/* Représentation interne */
	private double valeurs[];

	/* Constructeur à partir d'un tableau */
	public Vecteur(double[] valeurs) {
		this.valeurs = new double[valeurs.length];
		for (int i = 0; i < valeurs.length; i++) {
			this.valeurs[i] = valeurs[i];
		}
	}

	/* Constructeur d'un vecteur nul de taille donnée */
	public Vecteur(int taille) {
		if (taille < 0) {
			throw new IllegalArgumentException("Dimensions inadmissibles");
		}
		this.valeurs = new double[taille];
	}

	/* Représentation textuelle: [a b c] */
	public String toString() {
		String res = "[";
		for (double v : valeurs) {
			res += v + " ";
		}
		return res.substring(0, res.length() - 1) + "]";
	}

	/* Taille du vecteur */
	public int taille() {
		return valeurs.length;
	}

	/* Accès aux valeurs */
	public double getValeur(int pos) {
		return valeurs[pos];
	}

	public void setValeur(int pos, double val) {
		valeurs[pos] = val;
	}

	/* Méthodes pratiques pour compatibilité */
	public double get(int pos) {
		return getValeur(pos);
	}

	public void set(int pos, double val) {
		setValeur(pos, val);
	}

	/* Sous-vecteur des 'taille' premiers éléments */
	public Vecteur sousVecteur(int taille) {
		if (taille < 0 || taille > valeurs.length) {
			throw new IllegalArgumentException("Dimensions inadmissibles");
		}
		double[] sub = new double[taille];
		for (int i = 0; i < taille; i++) {
			sub[i] = valeurs[i];
		}
		return new Vecteur(sub);
	}

	/* Vecteur nul de taille donnée */
	public static Vecteur creerVecteurNul(int taille) {
		return new Vecteur(taille);
	}

	/* Comparaison avec tolérance EPSILON définie dans UtilitairesAlgebre */
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Vecteur)) return false;
		Vecteur o = (Vecteur) other;
		if (o.valeurs.length != this.valeurs.length) return false;
		for (int i = 0; i < valeurs.length; i++) {
			if (!UtilitairesAlgebre.egaliteDoublePrecision(this.valeurs[i], o.valeurs[i], UtilitairesAlgebre.EPSILON)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 1;
		for (double v : valeurs) {
			long bits = Double.doubleToLongBits(v);
			result = 31 * result + (int) (bits ^ (bits >> 32));
		}
		return result;
	}
}
