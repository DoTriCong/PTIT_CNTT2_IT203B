import java.time.LocalDate;
import java.util.List;

public class Ex6 {

    static class User {
        private String email;
        private LocalDate birthDate;

        public User(String email, LocalDate birthDate) {
            this.email = email;
            this.birthDate = birthDate;
        }

        public String getEmail() {
            return email;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }
    }

    static class UserProfile {
        private String email;
        private LocalDate birthDate;

        public UserProfile(String email, LocalDate birthDate) {
            this.email = email;
            this.birthDate = birthDate;
        }

        public String getEmail() {
            return email;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }
    }

    public User updateProfile(User existingUser, UserProfile newProfile, List<User> allUsers) {

        if (existingUser == null || newProfile == null) {
            return null;
        }

        if (newProfile.getBirthDate() != null &&
                newProfile.getBirthDate().isAfter(LocalDate.now())) {
            return null;
        }

        if (newProfile.getEmail() != null && allUsers != null) {
            for (User user : allUsers) {
                if (user != existingUser &&
                        newProfile.getEmail().equals(user.getEmail())) {
                    return null;
                }
            }
        }

        if (newProfile.getEmail() != null) {
            existingUser.setEmail(newProfile.getEmail());
        }

        if (newProfile.getBirthDate() != null) {
            existingUser.setBirthDate(newProfile.getBirthDate());
        }

        return existingUser;
    }
}