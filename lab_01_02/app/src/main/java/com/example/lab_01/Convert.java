package com.example.lab_01;

public class Convert {

    public static String arabicToRoman(int number) {
        if (number > 3999) {
            return "MAX 3999!";
        }
        if (number < 1) {
            return "";
        }
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds  = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens      = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones      = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return thousands[number / 1000] +
                hundreds[(number % 1000) / 100] +
                tens[(number % 100) / 10] +
                ones[number % 10];
    }

    public static String romanToArabic(String roman) {
        // Предполагается, что строка уже валидирована
        // Zakładamy, że ciąg znaków został już zweryfikowany.
        if (roman == null || roman.isEmpty()) {
            return "";
        }
        int sum = 0;
        int prev = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = charToValue(roman.charAt(i));
            if (value < prev) {
                sum -= value;
            } else {
                sum += value;
            }
            prev = value;
        }
        if (sum > 3999) {
            return "MAX 3999!";
        }
        return String.valueOf(sum);
    }

    private static int charToValue(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default:  return -1;
        }
    }
}
