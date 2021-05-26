package br.com.congregationreport.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class UtilFormatte {

    public static String getFormateFloat(Float valor) {
        if (valor == null) {
            return "";
        } else {
            String preco = NumberFormat.getCurrencyInstance().format(valor).replace("$", "R$ ");
            if (preco.contains("RR$")) {
                preco = preco.replace("RR$", "R$");
            }
            return preco;
        }
    }

    public static String getFormate(Float valor) {
        String f = "";
        if (valor == null) {
            return "";
        } else {
            DecimalFormat formatter = new DecimalFormat("#.00");
            if (valor < 1) {
                formatter = new DecimalFormat("##0.###");
            }

             if (valor.toString().endsWith(".0") || valor.toString().endsWith(".00")) {
                return valor.toString().replace(".0", "").replace(".00", "");
            }

            f = formatter.format(valor);

            String ultimo = f.substring(f.length() - 1);
            if (ultimo.equals("0")) {
                f = removeLastChar(f);
            }

            return f;
        }
    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

    public static String getFormateRemoveZero(Float valor) {
        if (valor == null) {
            return "";
        } else {
            DecimalFormat formatter = new DecimalFormat("#.0");
            return formatter.format(valor).replace(".0", "");
        }
    }

    public static Integer parseInteger(Float valor) {
        if (valor == null) {
            return 0;
        } else {
            return Integer.parseInt(valor.toString().replace(".0", ""));
        }
    }

    public static String getFormate(Float valor, Integer quantidadeCasasDecimais) {
        if (valor == null) {
            return "";
        } else {
            String casa = "";
            for (int i = 0; i < quantidadeCasasDecimais; i++) {
                casa = casa + "0";
            }
            DecimalFormat formatter = new DecimalFormat("#." + casa);
            return formatter.format(valor);
        }
    }

    public static String getFormateToInteger(Float valor) {
        if (valor == null) {
            return "";
        } else {
            return new Integer(valor.intValue()).toString();
        }
    }

    public static String stringToUpCaseOnlyFirstLetter(String text) {
        String newText = null;
        if (text == null) {
            newText = "";
        } else {
            newText = "";
            for (int i = 0; i < text.length(); i++) {
                String character = text.charAt(i) + "";
                if (i == 0) {
                    newText = newText + character.toUpperCase();
                } else {
                    newText = newText + character.toLowerCase();
                }

            }
        }
        return newText;
    }


}