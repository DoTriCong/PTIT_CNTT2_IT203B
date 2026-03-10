import java.util.List;

// Đối tượng Post
class Post {
    private List<String> tags;

    public Post(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }
}

public class Ex6 {
    public static void main(String[] args) {

        // Danh sách bài viết, mỗi bài có nhiều tag
        List<Post> posts = List.of(
                new Post(List.of("java", "backend")),
                new Post(List.of("python", "data")));

        // Làm phẳng danh sách tag
        List<String> allTags = posts.stream()
                .flatMap(post -> post.getTags().stream())
                .toList();

        // In kết quả
        System.out.println(allTags);
    }
}