package com.frankit.product_api.domain.user;

import com.frankit.product_api.application.controller.AuthController;
import com.frankit.product_api.application.dto.request.LoginRequest;
import com.frankit.product_api.application.dto.response.ApiResponse;
import com.frankit.product_api.application.dto.response.LoginResponse;
import com.frankit.product_api.infrastructure.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private User user;
    private LoginRequest loginRequest;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("encodedPassword");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("pass123");
    }

    @Test
    void login_success() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user@example.com");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateToken("user@example.com")).thenReturn("jwt-token");

        ApiResponse<LoginResponse> response = authController.login(loginRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertTrue(response.getSuccess());
        assertNotNull(response.getData());
        assertEquals("jwt-token", response.getData().getToken());

        verify(authenticationManager).authenticate(argThat(authToken ->
                authToken.getPrincipal().equals("user@example.com") &&
                        authToken.getCredentials().equals("pass123")));
        verify(jwtUtil).generateToken("user@example.com");
    }

    @Test
    void register_success() {
        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.register("newuser@example.com", "pass123");

        verify(userRepository).findByEmail("newuser@example.com");
        verify(passwordEncoder).encode("pass123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_emailExists_throwsException() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.register("user@example.com", "pass123"));

        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
        verify(userRepository).findByEmail("user@example.com");
        verifyNoMoreInteractions(passwordEncoder, userRepository);
    }

    @Test
    void loadUserByUsername_success() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("user@example.com");

        assertNotNull(userDetails);
        assertEquals("user@example.com", userDetails.getUsername());
        verify(userRepository).findByEmail("user@example.com");
    }

    @Test
    void loadUserByUsername_notFound_throwsException() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername("unknown@example.com"));

        assertEquals("사용자를 찾을 수 없습니다: unknown@example.com", exception.getMessage());
        verify(userRepository).findByEmail("unknown@example.com");
    }
}
