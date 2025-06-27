package com.aiims.gdms.controller;

import com.aiims.gdms.entity.*;
import com.aiims.gdms.repository.UserRepository;
import com.aiims.gdms.service.DoctorService;
import com.aiims.gdms.dto.ApiResponse;
import com.aiims.gdms.dto.PatientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "*")
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/assign-patient/{patientId}")
    public ResponseEntity<ApiResponse<String>> assignPatient(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long patientId) {
        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        try {
            // Find patient username by ID
            User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
            doctorService.assignPatientToDoctor(doctor.getUsername(), patient.getUsername());
            return ResponseEntity.ok(ApiResponse.success("Patient assigned successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getPatients(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long doctorId = doctor.getId();
        List<PatientResponseDto> patients = doctorService.getDoctorPatientsAsDto(doctorId);
        return ResponseEntity.ok(patients);
    }
    
    @GetMapping("/patients/{patientId}/glucose")
    public ResponseEntity<List<GlucoseLog>> getPatientGlucoseLogs(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @PathVariable Long patientId) {
        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long doctorId = doctor.getId();
        
        List<GlucoseLog> logs = doctorService.getPatientGlucoseLogs(doctorId, patientId);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/patients/{patientId}/meals")
    public ResponseEntity<List<MealLog>> getPatientMealLogs(@AuthenticationPrincipal UserDetails userDetails,
                                                            @PathVariable Long patientId) {
        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long doctorId = doctor.getId();
        
        List<MealLog> logs = doctorService.getPatientMealLogs(doctorId, patientId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/patients/{patientId}/symptoms")
    public ResponseEntity<List<SymptomLog>> getPatientSymptomLogs(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @PathVariable Long patientId) {
        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long doctorId = doctor.getId();
        
        List<SymptomLog> logs = doctorService.getPatientSymptomLogs(doctorId, patientId);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/patients/{patientId}/all-kick-session")
    public ResponseEntity<List<KickSession>> getPatientKickCountLogs(@AuthenticationPrincipal UserDetails userDetails,
                                                                      @PathVariable Long patientId,
                                                                      @RequestParam(required = false) String date) {
        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long doctorId = doctor.getId();
        
        List<KickSession> logs = doctorService.getAllKickSessions(doctorId, patientId);
        return ResponseEntity.ok(logs);
    }
} 