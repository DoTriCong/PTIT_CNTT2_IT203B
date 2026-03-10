import java.util.List;

class User {
    String username;
    String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", email=" + email + "]";
    }
}

public class Ex2 {
    public static void main(String[] args) {
        User user1 = new User("alice", "alice@gmail.com");
        User user2 = new User("bob", "bob@yahoo.com");
        User user3 = new User("charlie", "charlice@gmail.com");

        List<User> users = List.of(user1, user2, user3);

        users.stream().filter((u) -> u.email.contains("@gmail.com")).forEach(System.out::println);
    }
}