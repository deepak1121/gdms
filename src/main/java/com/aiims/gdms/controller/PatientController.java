package com.aiims.gdms.controller;

import com.aiims.gdms.dto.*;
import com.aiims.gdms.entity.*;
import com.aiims.gdms.repository.UserRepository;
import com.aiims.gdms.service.MealMasterService;
import com.aiims.gdms.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "*")
public class PatientController {
    
    @Autowired
    private PatientService patientService;

    @Autowired
    private MealMasterService mealMasterService;

    @Autowired
    private UserRepository userRepository;
    
    
    
    @GetMapping("/get-patient-details")
    public ResponseEntity<Object> addPatientDeatils(@AuthenticationPrincipal UserDetails userDetails){
    	 String username = userDetails.getUsername();
         User patient = userRepository.findByUsername(username)
             .orElseThrow(() -> new RuntimeException("User not found"));
    
    	try {
        	PatientResponseDto patientDetails = patientService.getPatientDetails(patient); 
            return new ResponseEntity<Object>((ApiResponse.success("Meal Master Data fetched", mealMasterService.getAllMeal())), HttpStatus.OK);
         } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
         }
        }

    
    @PostMapping("/add-glucose-logs")
    public ResponseEntity<ApiResponse<GlucoseLogResponse>> logGlucose(@AuthenticationPrincipal UserDetails userDetails,
                                                                      @Valid @RequestBody GlucoseLogRequest request) {
        String username = userDetails.getUsername();
        User patient = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long patientId = patient.getId();
        GlucoseLog log = patientService.logGlucose(patientId, request.getGlucoseLevel(), 
                                                  request.getReadingType(), request.getNotes());
        if (log != null) {
            GlucoseLogResponse response = new GlucoseLogResponse(log);
            return ResponseEntity.ok(ApiResponse.success("Glucose log created successfully", response));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to create glucose log"));
        }
    }
    
    @PostMapping("/meals")
    public ResponseEntity<ApiResponse<MealLog>> logMeal(@AuthenticationPrincipal UserDetails userDetails,
                                                       @Valid @RequestBody MealLogRequest request) {
        try {
            String username = userDetails.getUsername();
            User patient = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            Long patientId = patient.getId();
            
            MealLog log = patientService.createMealLog(patientId, request);
            return ResponseEntity.ok(ApiResponse.success("Meal log created successfully", log));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to create meal log: " + e.getMessage()));
        }
    }
    
    @PostMapping("/symptoms")
    public ResponseEntity<ApiResponse<SymptomLog>> logSymptoms(@AuthenticationPrincipal UserDetails userDetails,
                                                              @Valid @RequestBody SymptomLogRequest request) {
        String username = userDetails.getUsername();
        User patient = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        SymptomLog log = patientService.logSymptoms(patient, request);
        if (log != null) {
            return ResponseEntity.ok(ApiResponse.success("Symptom log created successfully", log));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to create symptom log"));
        }
    }
    
    @GetMapping("/get-glucose-logs")
    public ResponseEntity<ApiResponse<List<GlucoseLogResponse>>> getGlucoseLogs(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User patient = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long patientId = patient.getId();
        List<GlucoseLog> logs = patientService.getPatientGlucoseLogs(patientId);
        List<GlucoseLogResponse> responses = logs.stream()
                .map(GlucoseLogResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Glucose logs retrieved successfully", responses));
    }
    
    @GetMapping("/meals")
    public ResponseEntity<ApiResponse<List<MealLog>>> getMealLogs(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User patient = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long patientId = patient.getId();
        List<MealLog> logs = patientService.getPatientMealLogs(patientId);
        return ResponseEntity.ok(ApiResponse.success("Meal logs retrieved successfully", logs));
    }
    
    @GetMapping("/symptoms")
    public ResponseEntity<ApiResponse<List<SymptomLog>>> getSymptomLogs(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User patient = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long patientId = patient.getId();
        List<SymptomLog> logs = patientService.getPatientSymptomLogs(patientId);
        return ResponseEntity.ok(ApiResponse.success("Symptom logs retrieved successfully", logs));
    }
    
    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotifications(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User patient = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Long patientId = patient.getId();
        NotificationResponse notifications = patientService.getNotifications(patientId);
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved successfully", notifications));
    }

    @PostMapping("/kick-counts/kick")
    public ResponseEntity<ApiResponse<KickSession>> logFetalKick(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User patient = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        KickSession session = patientService.logKick(patient);
        return ResponseEntity.ok(ApiResponse.success("Kick recorded and session updated", session));
    }

    @GetMapping("/kick-counts/current-session")
    public ResponseEntity<ApiResponse<KickSession>> getCurrentKickSession(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User patient = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        KickSession session = patientService.getCurrentKickSession(patient);
        if (session == null) {
            return ResponseEntity.ok(ApiResponse.success("No active session", null));
        }
        return ResponseEntity.ok(ApiResponse.success("Current session fetched", session));
    }

    @GetMapping("/kick-counts/get-all-kick-session")
    public ResponseEntity<Object> getAllKickSessions(@AuthenticationPrincipal UserDetails userDetails){
         try {
            String username = userDetails.getUsername();
            User patient = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            List<KickSession> allKickSessions = patientService.getPatientKickSessionHistory(patient);
            return new ResponseEntity<>(ApiResponse.success("All kick sessions fetched", allKickSessions), HttpStatus.OK);
         } catch (Exception e) 
           {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        

    @GetMapping("/get-all-meals")
    public ResponseEntity<Object> getAllMeals(){
         try {
            return new ResponseEntity<Object>((ApiResponse.success("Meal Master Data fetched", mealMasterService.getAllMeal())), HttpStatus.OK);
         } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
         }
        }
    
    
    @PostMapping("/patient-full-logs")
    public ResponseEntity<ApiResponse<PatientLogsResponse>> getPatientLogsById(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody(required = false) String date) {  // Optional date parameter

        try {
            User patient = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (patient.getRole() != User.Role.PATIENT) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Only patients can view their logs"));
            }

            PatientLogsResponse logs = patientService.getPatientLogs(patient, date);
            return ResponseEntity.ok(ApiResponse.success("Patient logs fetched successfully", logs));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to fetch patient logs: " + e.getMessage()));
        }
    }


    
    
    @PostMapping("/meals-by-type")
    public ResponseEntity<ApiResponse<List<MealMaster>>> getMealsByType(@RequestBody MealTypeRequest request) {
        try {
            List<MealMaster> meals = mealMasterService.getMealsByType(request);
            return ResponseEntity.ok(ApiResponse.success("Meal list fetched successfully", meals));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to fetch meals: " + e.getMessage()));
        }
    }
    
} 
