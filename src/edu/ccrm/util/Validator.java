package edu.ccrm.util; // Package (folder) name

// Utility class for common validation checks
public class Validator {

    // Check if email is valid (must contain '@')
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    // Check if number is positive
    public static boolean isPositiveNumber(int num) {
        return num > 0;
    }

    // Check if string is not null and not empty
    public static boolean isNonEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
}
