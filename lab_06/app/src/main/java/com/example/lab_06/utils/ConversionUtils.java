package com.example.lab_06.utils;

public class ConversionUtils {

    // ===== NUMBERS =====
    public static String decimalToBinary(int dec) {
        return Integer.toBinaryString(dec);
    }

    public static String decimalToOctal(int dec) {
        return Integer.toOctalString(dec);
    }

    public static String decimalToHex(int dec) {
        return Integer.toHexString(dec).toUpperCase();
    }

    public static String decimalToBase(int dec, int base) {
        if (dec == 0) return "0";
        StringBuilder sb = new StringBuilder();
        int temp = Math.abs(dec);
        while (temp > 0) {
            int remainder = temp % base;
            sb.insert(0, remainder);
            temp /= base;
        }
        return (dec < 0 ? "-" : "") + sb.toString();
    }

    /**
     * Konwertuje liczbę zapisaną jako String w systemie o podstawie określonej przez fromSystem
     * na reprezentację w systemie o podstawie określonej przez toSystem.
     *
     * @param input Liczba jako String
     * @param fromSystem Nazwa systemu wejściowego (np. "dziesiętny", "dwókowy")
     * @param toSystem Nazwa systemu wyjściowego
     * @return Wynik konwersji jako String
     * @throws NumberFormatException jeśli input nie jest poprawną liczbą w danym systemie
     */
    public static String convertNumber(String input, String fromSystem, String toSystem) throws NumberFormatException {
        int fromBase = getBaseForSystem(fromSystem);
        int toBase = getBaseForSystem(toSystem);
        int number = Integer.parseInt(input, fromBase);
        if (toBase == 10) {
            return Integer.toString(number);
        } else {
            return Integer.toString(number, toBase).toUpperCase();
        }
    }

    /**
     * Mapuje nazwę systemu liczbowego na jego podstawę.
     *
     * @param systemName Nazwa systemu: "dwókowy", "czwórkowy", "ósemkowy", "dziesiętny", "szesnastkowy"
     * @return Podstawa systemu (2, 4, 8, 10 lub 16)
     */
    private static int getBaseForSystem(String systemName) {
        switch (systemName.toLowerCase()) {
            case "dwókowy":
                return 2;
            case "czwórkowy":
                return 4;
            case "ósemkowy":
                return 8;
            case "dziesiętny":
                return 10;
            case "szesnastkowy":
                return 16;
            default:
                return 10;
        }
    }

    // ===== CURRENCY =====

    public static double convertCurrency(double amount, String from, String to) {
        double inPLN = toPLN(amount, from);
        return fromPLN(inPLN, to);
    }

    private static double toPLN(double amount, String currency) {
        switch (currency) {
            case "zł":
                return amount;
            case "$":
                return amount * 3.9;
            case "€":
                return amount * 4.2;
            case "₴":
                return amount * 0.094;
            case "¥":
                return amount * 0.026;
            default:
                return amount;
        }
    }

    private static double fromPLN(double amount, String currency) {
        switch (currency) {
            case "zł":
                return amount;
            case "$":
                return amount / 3.9;
            case "€":
                return amount / 4.2;
            case "₴":
                return amount / 0.094;
            case "¥":
                return amount /0.026;
            default:
                return amount;
        }
    }

    // ===== LENGTH =====
    public static String convertLength(double value, String fromUnit, String toUnit) {
        double valueInMeters = convertLengthToMeters(value, fromUnit);
        double result = convertMetersToUnit(valueInMeters, toUnit);
        return String.format("%.4f", result);

    }

    private static double convertLengthToMeters(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "mm": return value / 1000.0;
            case "cm": return value / 100.0;
            case "m": return value;
            case "km": return value * 1000.0;
            case "cale": return value * 0.0254;
            case "stopy": return value * 0.3048;
            case "yardy": return value * 0.9144;
            default: return value;
        }
    }

    private static double convertMetersToUnit(double meters, String unit) {
        switch (unit.toLowerCase()) {
            case "mm": return meters * 1000.0;
            case "cm": return meters * 100.0;
            case "m": return meters;
            case "km": return meters / 1000.0;
            case "cale": return meters / 0.0254;
            case "stopy": return meters / 0.3048;
            case "yardy": return meters / 0.9144;
            default: return meters;
        }
    }


    // Stare metody konwersji długości – wyświetlają wszystkie jednostki
    public static String convertLengthAll(double mm) {
        double cm = mm / 10.0;
        double dm = mm / 100.0;
        double inch = mm / 25.4;
        double m = mm / 1000.0;
        double km = mm / 1_000_000.0;
        return String.format("mm: %.2f\ncm: %.2f\ndm: %.2f\ncal (inch): %.2f\nm: %.2f\nkm: %.6f",
                mm, cm, dm, inch, m, km);
    }

    // ===== AREA =====
    public static String convertArea(double value, String fromUnit, String toUnit) {
        double valueInM2 = convertAreaToM2(value, fromUnit);
        double result = convertM2ToUnit(valueInM2, toUnit);
        return String.format("%.4f", result);
    }

    private static double convertAreaToM2(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "mm²": return value / 1_000_000.0;
            case "cm²": return value / 10_000.0;
            case "m²": return value;
            case "km²": return value * 1_000_000.0;
            case "ary": return value * 100.0;
            case "hektary": return value * 10_000.0;
            default: return value;
        }
    }

    private static double convertM2ToUnit(double m2, String unit) {
        switch (unit.toLowerCase()) {
            case "mm²": return m2 * 1_000_000.0;
            case "cm²": return m2 * 10_000.0;
            case "m²": return m2;
            case "km²": return m2 / 1_000_000.0;
            case "ary": return m2 / 100.0;
            case "hektary": return m2 / 10_000.0;
            default: return m2;
        }
    }


    // Stara metoda konwersji powierzchni – wyświetla wszystkie jednostki
    public static String convertAreaAll(double mm2) {
        double cm2 = mm2 / 100.0;
        double dm2 = mm2 / 10_000.0;
        double m2 = mm2 / 1_000_000.0;
        double km2 = mm2 / 1e12;
        double ary = m2 / 100.0;
        double hektar = m2 / 10_000.0;
        return String.format("mm²: %.2f\ncm²: %.2f\ndm²: %.2f\nm²: %.2f\nkm²: %.9f\nary: %.4f\nhektary: %.4f",
                mm2, cm2, dm2, m2, km2, ary, hektar);
    }
}
