public class Ex4 {

    public String evaluatePasswordStrength(String password) {

        if (password == null || password.length() < 8) {
            return "Yếu";
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUpper = true;
            else if (Character.isLowerCase(c))
                hasLower = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else
                hasSpecial = true;
        }

        if (hasUpper && hasLower && hasDigit && hasSpecial) {
            return "Mạnh";
        }

        if ((hasUpper || hasLower) && (hasDigit || hasSpecial)) {
            return "Trung bình";
        }

        return "Yếu";
    }
}