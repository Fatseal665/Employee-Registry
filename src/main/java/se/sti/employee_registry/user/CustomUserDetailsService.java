package se.sti.employee_registry.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.sti.employee_registry.user.dto.CustomUserDetailsDTO;
import se.sti.employee_registry.user.mapper.CustomUserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomUserRepository customUserRepository;
    private final CustomUserMapper customUserMapper;

    @Autowired
    public CustomUserDetailsService(CustomUserRepository customUserRepository, CustomUserMapper customUserMapper) {
        this.customUserRepository = customUserRepository;
        this.customUserMapper = customUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUserDetailsDTO customUser = customUserMapper.toUserDetailsDTO( customUserRepository.findUserByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User %s not found", email))
                ));
        return new CustomUserDetails(customUser);
    }
}
