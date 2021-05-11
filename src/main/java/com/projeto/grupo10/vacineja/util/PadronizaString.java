package com.projeto.grupo10.vacineja.util;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class PadronizaString {

    /**
     * Altera o valor de uma string, retirando acentuação e letras maiusculas.
     *
     * @param str eh a string que se deseja padronizar.
     * @return a string padronizada
     */
    public static String padronizaString(String str) {
        String string = str.toLowerCase();
        String StringNormalizadaNFD = Normalizer.normalize(string, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(StringNormalizadaNFD).replaceAll("");
    }

    /**
     * Altera o valor de mais de uma string, retirando acentuação e letras maiusculas.
     *
     * @param strings eh o conjunto de strings que se deseja padronizar.
     * @return o conjunto de strings padronizadas.
     */
    public static Set<String> padronizaSetsDeString (Set<String> strings) {
        Set<String> setDeStringPadronizadas = new HashSet<String>();
        for (String string : strings) { setDeStringPadronizadas.add(padronizaString(string));}
        return setDeStringPadronizadas;
    }

    public static boolean validaStringNull(String parametro) {
        if (parametro == null) {
            return true;
        }
        return false;
    }

    public static boolean validaStringVazia(String parametro) {
        if (parametro.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean validaTamanhoString(String parametro, int tamanho ) {
        if (parametro.length() == tamanho){
            return true;
        }
        return false;
    }

    public static boolean validaApenasNumeroString(String parametro) {
        if (parametro.matches("[0-9]+")) {
            return true;
        }
        return false;
    }

    public static boolean validaApenasLetrasString(String parametro) {
        if (parametro.matches("[a-zA-Z_]+")) {
            return true;
        }
        return false;
    }
}
