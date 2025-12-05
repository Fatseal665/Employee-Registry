package se.sti.employee_registry.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import se.sti.employee_registry.security.rules.*;
import se.sti.employee_registry.user.authority.UserRole;

import java.util.Arrays;
import java.util.stream.Collectors;


public class CustomUserCreationDTO {

        @Valid
        private FirstnameRules firstName;

        @Valid
        private LastnameRules lastName;

        @Valid
        private EmailRules email;

        @Valid
        private PasswordRules password;

        @NotNull
        private boolean isAccountNonExistent;

        @NotNull
        private boolean isAccountNonLocked;

        @NotNull
        private boolean isCredentialsNonExpired;

        @NotNull
        private boolean isEnabled;

        @Valid
        private RolesRules roles;

        // ---------------- Constructors ----------------

        // Jackson will use this constructor to create the DTO from JSON strings
        @JsonCreator
        public CustomUserCreationDTO(
                String firstName,
                String lastName,
                String email,
                String password,
                boolean isAccountNonExistent,
                boolean isAccountNonLocked,
                boolean isCredentialsNonExpired,
                boolean isEnabled,
                String[] roles
        ) {
                this.firstName = new FirstnameRules(firstName);
                this.lastName = new LastnameRules(lastName);
                this.email = new EmailRules(email);
                this.password = new PasswordRules(password);
                this.isAccountNonExistent = isAccountNonExistent;
                this.isAccountNonLocked = isAccountNonLocked;
                this.isCredentialsNonExpired = isCredentialsNonExpired;
                this.isEnabled = isEnabled;
                this.roles = new RolesRules( Arrays.stream(roles)
                        .map(UserRole::valueOf) // kan kasta IllegalArgumentException om strängen inte matchar
                        .collect(Collectors.toSet()));
        }

        // ---------------- Getters/Setters ----------------
        public FirstnameRules getFirstName() {
                return firstName;
        }

        public void setFirstName(FirstnameRules firstName) {
                this.firstName = firstName;
        }

        public LastnameRules getLastName() {
                return lastName;
        }

        public void setLastName(LastnameRules lastName) {
                this.lastName = lastName;
        }

        public EmailRules getEmail() {
                return email;
        }

        public void setEmail(EmailRules email) {
                this.email = email;
        }

        public PasswordRules getPassword() {
                return password;
        }

        public void setPassword(PasswordRules password) {
                this.password = password;
        }

        public boolean isAccountNonExistent() {
                return isAccountNonExistent;
        }

        public void setAccountNonExistent(boolean accountNonExistent) {
                isAccountNonExistent = accountNonExistent;
        }

        public boolean isAccountNonLocked() {
                return isAccountNonLocked;
        }

        public void setAccountNonLocked(boolean accountNonLocked) {
                isAccountNonLocked = accountNonLocked;
        }

        public boolean isCredentialsNonExpired() {
                return isCredentialsNonExpired;
        }

        public void setCredentialsNonExpired(boolean credentialsNonExpired) {
                isCredentialsNonExpired = credentialsNonExpired;
        }

        public boolean isEnabled() {
                return isEnabled;
        }

        public void setEnabled(boolean enabled) {
                isEnabled = enabled;
        }

        public RolesRules getRoles() {
                return roles;
        }

        public void setRoles(RolesRules roles) {
                this.roles = roles;
        }


}