import java.util.*;

class User {
    String username;
    String email;
    String status;

    public User(String username, String email, String status) {
        this.username = username;
        this.email = email;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String toString() {
        return username + " - " + email + " - " + status;
    }
}

public class Ex4 {
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alice", "alice@gmail.com", "ACTIVE"),
                new User("bob", "bob@gmail.com", "ACTIVE"),
                new User("alice", "alice2@gmail.com", "INACTIVE"),
                new User("charlie", "charlie@gmail.com", "ACTIVE")
        );

        Set<String> seen = new HashSet<>();
        List<User> uniqueUsers = new ArrayList<>();

        for (User u : users) {
            if (seen.add(u.getUsername())) {
                uniqueUsers.add(u);
            }
        }

        uniqueUsers.forEach(System.out::println);
    }
}