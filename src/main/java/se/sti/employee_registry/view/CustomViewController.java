package se.sti.employee_registry.view;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import se.sti.employee_registry.user.CustomUser;
import se.sti.employee_registry.user.CustomUserRepository;
import se.sti.employee_registry.user.authority.UserRole;
import se.sti.employee_registry.user.dto.CustomUserCreationDTO;
import se.sti.employee_registry.user.dto.CustomUserResponseDTO;
import se.sti.employee_registry.user.mapper.CustomUserMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CustomViewController {
    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserMapper customUserMapper;

    @Autowired
    CustomViewController(CustomUserRepository customUserRepository, PasswordEncoder passwordEncoder, CustomUserMapper customUserMapper) {
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserMapper = customUserMapper;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid CustomUserCreationDTO customUserCreationDTO, BindingResult bindingResult
            ) {
        if (bindingResult.hasErrors()) {
            return "registerpage";
        }
        CustomUser customUser = customUserMapper.toEntity(customUserCreationDTO);

        customUser.setPassword(
                customUser.getPassword(),
                passwordEncoder
        );

        customUser.setRoles(
                Set.of(UserRole.EMPLOYEE)
        );

        System.out.println("Saving user... ");
        customUserRepository.save(customUser);

        return "redirect:/user";
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CustomUserResponseDTO>> getAll() {
        List<CustomUser> employees = customUserRepository.findAll();
        List<CustomUserResponseDTO> employeesDTO = employees.stream()
                .map(employee -> customUserMapper.toResponseDTO(employee)

                ).collect(Collectors.toList());
        return ResponseEntity.ok(employeesDTO);
    }



}
