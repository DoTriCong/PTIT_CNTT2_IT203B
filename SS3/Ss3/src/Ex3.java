import java.util.List;
import java.util.Optional;

enum Status {
    ACTIVE,
    INACTIVE
}

record User(String username, String email, Status status) {
}

// Repository tìm kiếm User
class UserRepository {

    private static final List<User> users = List.of(
            new User("alice", "alice@gmail.com", Status.ACTIVE),
            new User("bob", "bob@yahoo.com", Status.INACTIVE),
            new User("charlie", "charlie@gmail.com", Status.ACTIVE));

    public static Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.username().equals(username))
                .findFirst();
    }
}

public class Ex3 {
    public static void main(String[] args) {

        Optional<User> result = UserRepository.findUserByUsername("alice");

        // Cách 1: ifPresent + orElse
        if (result.isPresent()) {
            System.out.println("Welcome " + result.get().username());
        } else {
            System.out.println("Guest login");
        }

        // Cách 2: Viết gọn 
        result.ifPresentOrElse(
                user -> System.out.println("Welcome " + user.username()),
                () -> System.out.println("Guest login"));
    }
}