package group6.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import group6.ecommerce.Repository.RoleRepository;
import group6.ecommerce.Repository.UserRepository;
import group6.ecommerce.model.Role;
import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.LoginRequest;
import group6.ecommerce.payload.request.UserRequest;
import group6.ecommerce.payload.response.JwtResponse;
import group6.ecommerce.service.JwtService;
import group6.ecommerce.service.UserService;
import group6.ecommerce.utils.Constant;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authen);
        List<String> listRoles = authentication.getAuthorities()
                .stream().map((authority) -> authority.getAuthority())
                .collect(Collectors.toList());
        return jwtService.generateJwtResponse(loginRequest.getEmail(), listRoles);
    }

    @Override
    public void register(UserRequest userRequest) {
        String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());
        Role role = roleRepository.findByRoleName(Constant.ROLE_USER);
        Users users = mapUserRequestToUser(userRequest, role, encryptedPassword);
        userRepository.save(users);
    }

    private Users mapUserRequestToUser(UserRequest userRequest, Role role, String encryptedPassword) {
        Users user = new Users();
        user.setId(userRequest.getId());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setCity(userRequest.getCity());
        user.setDistrict(userRequest.getDistrict());
        user.setPhone(userRequest.getPhone());
        user.setRole(role);
        user.setPassword(encryptedPassword);
        user.setWard(userRequest.getWard());
        return user;
    }

    @Override
    public Users findByEmail(String email) {
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new RuntimeException("No user with this email");
        return optionalUser.get();
    }

}