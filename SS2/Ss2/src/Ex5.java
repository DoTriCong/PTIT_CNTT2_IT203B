interface UserActions {

    default void logActivity(String activity) {
        System.out.println("User action: " + activity);
    }
}

interface AdminActions {

    default void logActivity(String activity) {
        System.out.println("Admin action: " + activity);
    }
}

class SuperAdmin implements UserActions, AdminActions {
    @Override
    public void logActivity(String activity) {

        // Có thể chọn gọi method của interface nào
        UserActions.super.logActivity(activity);
        AdminActions.super.logActivity(activity);
    }
}

public class Ex5 {
    public static void main(String[] args) {
        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.logActivity("Delete user account");
    }
}
