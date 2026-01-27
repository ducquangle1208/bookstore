package app.services;

import app.dto.LoginRequest;
import app.dto.RegisterRequest;
import app.entities.Role;
import app.entities.User;
import app.repositories.RoleRepo;
import app.repositories.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User registerUser(RegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username is already in use");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getName());
//        Role userRole = roleRepo.findByName("ROLE_USER");
//        user.setRoles(Set.of(userRole));

        return userRepo.save(user);
    }

    public String loginUser(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.getUsername());
        }

        return "failure";

    }
}
