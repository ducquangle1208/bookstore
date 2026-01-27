package app.services;

import app.dto.LoginRequest;
import app.dto.RegisterRequest;
import app.entities.Role;
import app.entities.User;
import app.repositories.RoleRepo;
import app.repositories.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public User registerUser(RegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username is already in use");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
//        Role userRole = roleRepo.findByName("ROLE_USER");
//        user.setRoles(Set.of(userRole));

        return userRepo.save(user);
    }

    public void loginUser(LoginRequest request) {
        User user = userRepo.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("Bad Credentials");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Bad Credentials");
        }



    }
}
