package br.com.spm.utils;

public class Utils {
    public static boolean regexNumberStringCharactersSpecial(CharSequence password) {
        return password.toString().matches("^(?=.*[0-9])(?=.*[A-z])(?=.*[\\W]).{1,}$");
    }
}
