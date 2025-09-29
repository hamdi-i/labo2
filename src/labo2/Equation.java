package labo2;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Equation {
    private final double[] coeffs; // x, y, z
    private double constant;

    public Equation() {
        this.coeffs = new double[3];
        this.constant = 0.0;
    }

    public Equation(double[] coeffs, double constant) {
        if (coeffs == null || coeffs.length != 3) {
            throw new IllegalArgumentException("Dimensions inadmissibles");
        }
        this.coeffs = new double[]{coeffs[0], coeffs[1], coeffs[2]};
        this.constant = constant;
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

        // Réinitialiser
        coeffs[0] = coeffs[1] = coeffs[2] = 0.0;

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
            coeffs[idx] += c;
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
        return new double[]{coeffs[0], coeffs[1], coeffs[2]};
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
            double c = coeffs[i];
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
        for (int i = 0; i < 3; i++) {
            if (!UtilitairesAlgebre.egaliteDoublePrecision(this.coeffs[i], o.coeffs[i], UtilitairesAlgebre.EPSILON)) {
                return false;
            }
        }
        return UtilitairesAlgebre.egaliteDoublePrecision(this.constant, o.constant, UtilitairesAlgebre.EPSILON);
    }

    @Override
    public int hashCode() {
        long b0 = Double.doubleToLongBits(coeffs[0]);
        long b1 = Double.doubleToLongBits(coeffs[1]);
        long b2 = Double.doubleToLongBits(coeffs[2]);
        long bc = Double.doubleToLongBits(constant);
        int result = 1;
        result = 31 * result + (int)(b0 ^ (b0 >> 32));
        result = 31 * result + (int)(b1 ^ (b1 >> 32));
        result = 31 * result + (int)(b2 ^ (b2 >> 32));
        result = 31 * result + (int)(bc ^ (bc >> 32));
        return result;
    }
}