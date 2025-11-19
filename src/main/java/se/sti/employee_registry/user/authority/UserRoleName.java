package se.sti.employee_registry.user.authority;

public enum UserRoleName {

    EMPLOYEE("ROLE_EMPLOYEE"),
    ADMIN("ROLE_ADMIN");

    private final String roleName;

    UserRoleName(String roleName) {
    this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}

