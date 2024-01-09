package group6.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import group6.ecommerce.Repository.CartRepository;
import group6.ecommerce.model.Cart;
import group6.ecommerce.payload.request.ChangePasswordRequest;
import group6.ecommerce.payload.request.UserInfoRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import group6.ecommerce.Repository.RoleRepository;
import group6.ecommerce.Repository.UserRepository;
import group6.ecommerce.exception.NotFoundException;
import group6.ecommerce.model.Role;
import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.LoginRequest;
import group6.ecommerce.payload.request.UserRequest;
import group6.ecommerce.payload.response.JwtResponse;
import group6.ecommerce.service.JwtService;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CartRepository cartRepository;

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
        Role role = roleRepository.findByRoleName("ROLE_USER");
        Users users = mapUserRequestToUser(userRequest, role, encryptedPassword);
        userRepository.save(users);
        Cart cart = new Cart();
        cart.setUserCart(users);
        cartRepository.save(cart);
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
            throw new NotFoundException("No user with this email");
        return optionalUser.get();
    }

    @Override
    public Users findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = userDetails.getUsername();
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        String confirmPassword = changePasswordRequest.getConfirmPassword();

        Users user = findByEmail(email);
        if(passwordEncoder.matches(oldPassword, user.getPassword()) && newPassword.equals(confirmPassword)){
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void update(int id, UserInfoRequest userInfoRequest) {
        Users user = userRepository.findById(id).get();
        user.setAddress(userInfoRequest.getAddress());
        user.setPhone(userInfoRequest.getPhone());
        user.setCity(userInfoRequest.getCity());
        user.setDistrict(userInfoRequest.getDistrict());
        user.setWard(userInfoRequest.getWard());
        userRepository.save(user);
    }
}