package se.sti.employee_registry.view;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.sti.employee_registry.publisher.EmailPublisher;
import se.sti.employee_registry.user.CustomUser;
import se.sti.employee_registry.user.CustomUserRepository;
import se.sti.employee_registry.user.authority.UserRole;
import se.sti.employee_registry.user.dto.AdminCommandDTO;
import se.sti.employee_registry.user.dto.CustomUserCreationDTO;
import se.sti.employee_registry.user.dto.CustomUserResponseDTO;
import se.sti.employee_registry.user.mapper.CustomUserMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CustomViewController {

    private final CustomUserRepository customUserRepository;
    private final CustomUserMapper customUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailPublisher emailPublisher;

    public CustomViewController(
            CustomUserRepository customUserRepository,
            CustomUserMapper customUserMapper,
            PasswordEncoder passwordEncoder,
            EmailPublisher emailPublisher
    ) {
        this.customUserRepository = customUserRepository;
        this.customUserMapper = customUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailPublisher = emailPublisher;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout( HttpServletResponse response) {

        Cookie cookie = new Cookie("authToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // ✅ ändra till true i produktion
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CustomUserResponseDTO>> getAll() {
        List<CustomUserResponseDTO> users = customUserRepository.findAll()
                .stream()
                .map(customUserMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(users);
    }


    //ADMIN COMMANDS
    @PostMapping("/admin/register")
    public ResponseEntity<Void> registerUser(
            @Valid @RequestBody CustomUserCreationDTO dto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        CustomUser user = customUserMapper.toEntity(dto);
        user.setPassword(user.getPassword(), passwordEncoder);
        user.setRoles(Set.of(UserRole.EMPLOYEE));
        customUserRepository.save(user);

        String username = user.getFirstName() + " " + user.getLastName();
        emailPublisher.sendUserCreated(user.getEmail(), username);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<Void> adminDelete(@RequestBody @Valid AdminCommandDTO dto) {
        Optional<CustomUser> optionalUser = customUserRepository.findById(dto.id());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CustomUser user = optionalUser.get();
        customUserRepository.deleteById(dto.id());

        // Skicka event till RabbitMQ
        String username = user.getFirstName() + " " + user.getLastName();
        emailPublisher.sendUserDeleted(user.getEmail(), username);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/update-status")
    public ResponseEntity<Void> updateUserStatus(@RequestBody @Valid AdminCommandDTO dto) {
        Optional<CustomUser> optionalUser = customUserRepository.findById(dto.id());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CustomUser user = optionalUser.get();

        user.setAccountNonExistent(dto.isAccountNonExistent());
        user.setAccountNonLocked(dto.isAccountNonLocked());
        user.setCredentialsNonExpired(dto.isCredentialsNonExpired());
        user.setEnabled(dto.isEnabled());

        customUserRepository.save(user);
        return ResponseEntity.noContent().build();
    }


}
