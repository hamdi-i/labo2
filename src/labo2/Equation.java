package labo2;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Equation qui hérite de Vecteur:
 * - Les 3 premières composantes du vecteur représentent les coefficients (x, y, z).
 * - La constante à droite du signe '=' est stockée séparément.
 */
public class Equation extends Vecteur {
    private double constant;

    public Equation() {
        super(3); // vecteur de 3 coefficients (x,y,z)
        this.constant = 0.0;
    }

    public Equation(double[] coeffs, double constant) {
        super(validCoeffs(coeffs));
        this.constant = constant;
    }

    private static double[] validCoeffs(double[] coeffs) {
        if (coeffs == null || coeffs.length != 3) {
            throw new IllegalArgumentException("Dimensions inadmissibles");
        }
        return coeffs;
    }

    public void lire(String source) {
        if (source == null) throw new IllegalArgumentException("Source vide");
        String s = source.trim().toLowerCase(Locale.ROOT);
        String[] sides = s.split("=");
        if (sides.length != 2) {
            throw new IllegalArgumentException("Equation mal formée, '=' manquant");
        }
        String left = sides[0].replace(" ", "");
        String right = sides[1].replace(" ", "");

        // Réinitialiser coefficients
        set(0, 0.0);
        set(1, 0.0);
        set(2, 0.0);

        // Extraire les termes pour x, y, z
        Pattern p = Pattern.compile("([+-]?\\d*\\.?\\d*)\\s*([xyz])");
        Matcher m = p.matcher(left);
        while (m.find()) {
            String num = m.group(1);
            String var = m.group(2);
            double c;
            if (num == null || num.isEmpty() || num.equals("+") || num.equals("-")) {
                c = (num != null && num.startsWith("-")) ? -1.0 : 1.0;
            } else {
                c = Double.parseDouble(num);
            }
            int idx = indexOfVar(var.charAt(0));
            set(idx, get(idx) + c);
        }

        // Constante du côté droit (suppose un nombre simple)
        constant = Double.parseDouble(right);
    }

    private int indexOfVar(char v) {
        switch (v) {
            case 'x': return 0;
            case 'y': return 1;
            case 'z': return 2;
            default: throw new IllegalArgumentException("Variable inconnue: " + v);
        }
    }

    public double[] getCoeffs() {
        return new double[]{get(0), get(1), get(2)};
    }

    public double getConstant() {
        return constant;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String[] vars = {"x", "y", "z"};
        boolean first = true;
        for (int i = 0; i < 3; i++) {
            double c = get(i);
            if (first) {
                if (c < 0) {
                    sb.append("- ").append(Double.toString(Math.abs(c))).append(vars[i]);
                } else {
                    sb.append(Double.toString(c)).append(vars[i]);
                }
                first = false;
            } else {
                if (c < 0) {
                    sb.append(" - ").append(Double.toString(Math.abs(c))).append(vars[i]);
                } else {
                    sb.append(" + ").append(Double.toString(c)).append(vars[i]);
                }
            }
        }
        sb.append(" = ").append(Double.toString(constant));
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Equation)) return false;
        Equation o = (Equation) other;
        // Compare les coefficients via equals de Vecteur (avec tolérance)
        if (!super.equals(o)) return false;
        // Compare la constante avec tolérance
        return UtilitairesAlgebre.egaliteDoublePrecision(this.constant, o.constant, UtilitairesAlgebre.EPSILON);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long bc = Double.doubleToLongBits(constant);
        result = 31 * result + (int)(bc ^ (bc >> 32));
        return result;
    }
}