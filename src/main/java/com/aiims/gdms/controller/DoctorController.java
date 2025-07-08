package com.aiims.gdms.controller;

import com.aiims.gdms.entity.*;
import com.aiims.gdms.repository.UserRepository;
import com.aiims.gdms.service.DoctorService;

import jakarta.validation.Valid;

import com.aiims.gdms.dto.AllDoctorPatientLogsResponse;
import com.aiims.gdms.dto.ApiResponse;
import com.aiims.gdms.dto.ClinicalNotesRequest;
import com.aiims.gdms.dto.DoctorPatientFullLogsRequest;
import com.aiims.gdms.dto.DoctorPatientGlucoseRequest;
import com.aiims.gdms.dto.DoctorPatientKickSessionDto;
import com.aiims.gdms.dto.DoctorPatientKickSessionRequest;
import com.aiims.gdms.dto.DoctorPatientMealRequest;
import com.aiims.gdms.dto.DoctorPatientSymptomRequest;
import com.aiims.gdms.dto.GlucoseLogResponse;
import com.aiims.gdms.dto.MealLogDto;
import com.aiims.gdms.dto.PatientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            .orElseThrow(() -> new RuntimeException("Doctor not found. Please check the username"));

        try {
            // Find patient username by ID
            User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient with ID " + patientId + " not found. Please verify the patient ID"));

            doctorService.assignPatientToDoctor(doctor.getUsername(), patient.getUsername());
            return ResponseEntity.ok(ApiResponse.success("Patient assigned successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Error: " + e.getMessage()));
        } catch (Exception e) {
            // Catch any other unforeseen exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("An unexpected error occurred. Please try again later."));
        }
    }

    
    @GetMapping("/find-doctor-patient-mapping")
    public ResponseEntity<List<PatientResponseDto>> getPatients(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long doctorId = doctor.getId();
        List<PatientResponseDto> patients = doctorService.getDoctorPatientsAsDto(doctorId);
        return ResponseEntity.ok(patients);
    }
    
    @PostMapping("/patients/glucose-logs")
    public ResponseEntity<List<GlucoseLogResponse>> getPatientGlucoseLogs(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid DoctorPatientGlucoseRequest request) {

        String username = userDetails.getUsername();

        User doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Doctor not authenticated"));

        List<GlucoseLogResponse> logs = doctorService.getPatientGlucoseLogs(
                doctor.getId(),
                request.getPatientId(),
                request.getFromDate(),
                request.getToDate()
        );

        return ResponseEntity.ok(logs);
    }

    
    @PostMapping("/patient/meals")
    public ResponseEntity<List<MealLogDto>> getDoctorPatientMealLogs(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid DoctorPatientMealRequest request) {

        String username = userDetails.getUsername();

        User doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Doctor not authenticated"));

        List<MealLogDto> meals = doctorService.getPatientMealLogs(
                doctor.getId(),
                request.getPatientId(),
                request.getFromDate(),
                request.getToDate()
        );

        return ResponseEntity.ok(meals);
    }
    
    

    @PostMapping("/patient/symptoms")
    public ResponseEntity<List<SymptomLog>> getDoctorPatientSymptomLogs(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid DoctorPatientSymptomRequest request) {

        String username = userDetails.getUsername();

        User doctor = userRepository.findByUsername(username)
        	    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Doctor not authenticated"));

        List<SymptomLog> logs = doctorService.getPatientSymptomLogs(
                doctor.getId(),
                request.getPatientId(),
                request.getFromDate(),
                request.getToDate()
        );

        return ResponseEntity.ok(logs);
    }

    
    @PostMapping("/patients/kick-sessions")
    public ResponseEntity<List<DoctorPatientKickSessionDto>> getKickSessions(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid DoctorPatientKickSessionRequest request) {

        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Doctor not authenticated"));

        List<DoctorPatientKickSessionDto> sessions = doctorService.getKickSessionsForDoctor(
                doctor.getId(),
                request.getPatientId(),
                request.getFromDate(),
                request.getToDate()
        );

        return ResponseEntity.ok(sessions);
    }
    
    
    @PostMapping("/patients/all-logs")
    public ResponseEntity<Object> getAllPatientLogs(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid  DoctorPatientFullLogsRequest request) {

        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Doctor not authenticated"));

        if (!doctorService.isDoctorAssignedToPatient(doctor.getId(), request.getPatientId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Doctor is not assigned to this patient");
        }

        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        AllDoctorPatientLogsResponse response = new AllDoctorPatientLogsResponse();

        response.setGlucoseLogs(doctorService.getPatientGlucoseLogs(doctor.getId(), request.getPatientId(), request.getFromDate(), request.getToDate()));
        response.setMealLogs(doctorService.getPatientMealLogs(doctor.getId(), request.getPatientId(), request.getFromDate(), request.getToDate()));
        response.setSymptomLogs(doctorService.getPatientSymptomLogs(doctor.getId(), request.getPatientId(), request.getFromDate(), request.getToDate()));
        response.setKickSessions(doctorService.getKickSessionsForDoctor(doctor.getId(), request.getPatientId(), request.getFromDate(), request.getToDate()));

        return ResponseEntity.ok(response);
    }

    
    
    @PostMapping("/patients/add-clinical-notes")
    public ResponseEntity<?> addNoteToPatient(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ClinicalNotesRequest request) {

        String username = userDetails.getUsername();
        User doctor = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctorService.addClinicalNote(doctor, request);
        return ResponseEntity.ok("Clinical note added successfully");
    }

    
    



} 