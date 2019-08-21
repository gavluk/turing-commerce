package ua.com.gavluk.turing.ecommerce.utils;

public class CommonUtils {

    /**
     * Masks string to non-disclose (for secret strings printing to log or for other debug reasons).
     * Accept null origin (returns "null").
     *
     * @param origin     string to mask
     * @param firstChars how many first chars may be disclosed
     * @param lastChars  how many last chars may be disclosed
     * @return masked string like "int....tion" for "internationalization" with firstChars=3, lastChars=4
     */
    public static String maskString(String origin, int firstChars, int lastChars) {

        if (origin == null) return "null";

        // if string too short for masking, just first and last symbols
        int len = origin.length();
        if (len <= firstChars + lastChars) {
            return origin;
        }
        return origin.substring(0, firstChars) + "..." + origin.substring(len - lastChars);
    }

}
