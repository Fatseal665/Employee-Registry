package se.sti.employee_registry.user.authority;

public enum UserPermission {

    READ("READ"),
    ADD("ADD"),
    DELETE("DELETE");

    private final String userPermission;

    UserPermission(String userPermission) {
        this.userPermission = userPermission;
    }

    public String getUserPermission() {
        return userPermission;
    }
}
