package com.aiims.gdms.service;

import com.aiims.gdms.dto.AuthRequest;
import com.aiims.gdms.dto.AuthResponse;
import com.aiims.gdms.entity.DoctorProfile;
import com.aiims.gdms.entity.PatientProfile;
import com.aiims.gdms.entity.User;
import com.aiims.gdms.repository.DoctorProfileRepository;
import com.aiims.gdms.repository.PatientProfileRepository;
import com.aiims.gdms.repository.UserRepository;
import com.aiims.gdms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PatientProfileRepository patientProfileRepository;
    
    @Autowired
    private DoctorProfileRepository doctorProfileRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user;
    }
    
    public AuthResponse register(AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse("Username already exists");
        }
        
        User.Role role = User.Role.valueOf(request.getRole().toUpperCase());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        
        user = userRepository.save(user);
        
        // Create profile based on role
        if (role == User.Role.PATIENT) {
            PatientProfile profile = new PatientProfile(user, request.getFirstName(), request.getLastName(), request.getPhoneNumber(), request.getBirthYear(), request.getGravida(), request.getPara(), request.getLivingChildren(), request.getAbortions(), request.getLastMenstrualPeriod());
            patientProfileRepository.save(profile);
        } else if (role == User.Role.DOCTOR) {
            DoctorProfile profile = new DoctorProfile(user, request.getFirstName(), request.getLastName());
            doctorProfileRepository.save(profile);
        }
        
        return new AuthResponse("You have register successful");
    }
    
    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            return new AuthResponse("Invalid username or password");
        }
        
        UserDetails userDetails = loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        return new AuthResponse("Success", token, user.getUsername(), user.getRole().name());
    }
} 