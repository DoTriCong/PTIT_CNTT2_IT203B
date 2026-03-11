public class Ex1 {

    public boolean isValidUsername(String username) {

        if (username == null) {
            return false;
        }

        if (username.length() < 6 || username.length() > 20) {
            return false;
        }

        if (username.contains(" ")) {
            return false;
        }

        return true;
    }
}