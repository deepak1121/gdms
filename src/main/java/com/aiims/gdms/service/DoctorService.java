package com.aiims.gdms.service;

import com.aiims.gdms.entity.*;
import com.aiims.gdms.repository.*;
import com.aiims.gdms.dto.PatientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    
    @Autowired
    private DoctorPatientMappingRepository mappingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PatientProfileRepository patientProfileRepository;
    
    @Autowired
    private GlucoseLogRepository glucoseLogRepository;
    
    @Autowired
    private MealLogRepository mealLogRepository;
    
    @Autowired
    private SymptomLogRepository symptomLogRepository;
    
    @Autowired
    private KickSessionRepository kickSessionRepository;


    public void assignPatientToDoctor(String doctorUsername, String patientUsername) {
        User doctor = userRepository.findByUsername(doctorUsername)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
        User patient = userRepository.findByUsername(patientUsername)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (doctor.getRole() != User.Role.DOCTOR) {
            throw new RuntimeException("User is not a doctor");
        }
        if (patient.getRole() != User.Role.PATIENT) {
            throw new RuntimeException("User is not a patient");
        }
        if (mappingRepository.existsByDoctorAndPatientAndActiveTrue(doctor, patient)) {
            throw new RuntimeException("Mapping already exists");
        }

        DoctorPatientMapping mapping = new DoctorPatientMapping(doctor, patient);
        mappingRepository.save(mapping);
    }
    
    public List<PatientResponseDto> getDoctorPatientsAsDto(Long doctorId) {
       
        Optional<User> doctorOpt = userRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            return List.of();
        }
        if (doctorOpt.get().getRole() != User.Role.DOCTOR) {
            System.out.println("User is not a doctor.");
            return List.of();
        }
        User doctor = doctorOpt.get();
        System.out.println("Doctor found: " + doctor.getUsername());

        List<DoctorPatientMapping> mappings = mappingRepository.findByDoctorAndActiveTrue(doctor);
        System.out.println("Found " + mappings.size() + " active patient mappings.");

        return mappings.stream()
                .map(mapping -> {
                    User patient = mapping.getPatient();
                    System.out.println("Mapping found for patient ID: " + patient.getId());

                    PatientProfile profile = patientProfileRepository.findByUser(patient)
                            .orElseThrow(() -> {
                                return new RuntimeException("Patient not found");
                            });

                    return new PatientResponseDto(
                    		profile.getId(),
                            profile.getFirstName(),
                            profile.getLastName(),
                            profile.getPhoneNumber(),
                            profile.getBirthYear(),
                            profile.getGravida(),
                            profile.getPara(),
                            profile.getLivingChildren(),
                            profile.getAbortions(),
                            profile.getLastMenstrualPeriod());
                })
                .collect(Collectors.toList());
    }
  
    public List<DoctorPatientMapping> getDoctorPatients(Long doctorId) {
        Optional<User> doctorOpt = userRepository.findById(doctorId);
        if (doctorOpt.isEmpty() || doctorOpt.get().getRole() != User.Role.DOCTOR) {
            return List.of();
        }
        
        return mappingRepository.findByDoctorAndActiveTrue(doctorOpt.get());
    }
    
    public List<GlucoseLog> getPatientGlucoseLogs(Long doctorId, Long patientId) {
        if (!isDoctorAssignedToPatient(doctorId, patientId)) {
            return List.of();
        }
        
        Optional<User> patientOpt = userRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            return List.of();
        }
        
        return glucoseLogRepository.findByPatient(patientOpt.get());
    }
    

    
    public List<MealLog> getPatientMealLogs(Long doctorId, Long patientId) {
        if (!isDoctorAssignedToPatient(doctorId, patientId)) {
            return List.of();
        }

        Optional<User> patientOpt = userRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            return List.of();
        }

        return mealLogRepository.findByPatient(patientOpt.get());
    }
    
    public List<SymptomLog> getPatientSymptomLogs(Long doctorId, Long patientId) {
        if (!isDoctorAssignedToPatient(doctorId, patientId)) {
            return List.of();
        }
        
        Optional<User> patientOpt = userRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            return List.of();
        }
        
        return symptomLogRepository.findByPatient(patientOpt.get());
    }
    
    public List<KickSession> getPatientCompletedKickSessions(Long doctorId, Long patientId) {
        if (!isDoctorAssignedToPatient(doctorId, patientId)) {
            throw new RuntimeException("Doctor is not assigned to this patient.");
        }
        
        User patient = userRepository.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        return kickSessionRepository.findByPatientAndCompletedTrueOrderByStartTimeDesc(patient);
    }

    public List<KickSession> getAllKickSessions(Long doctorId, Long patientId) {
        if (!isDoctorAssignedToPatient(doctorId, patientId))
         {
            throw new RuntimeException("Doctor is not assigned to this patient.");
        }
        
        User patient = userRepository.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        return kickSessionRepository.findByPatientOrderByStartTimeDesc(patient);
    }

    
    
    private boolean isDoctorAssignedToPatient(Long doctorId, Long patientId) {
        Optional<User> doctorOpt = userRepository.findById(doctorId);
        Optional<User> patientOpt = userRepository.findById(patientId);
        
        if (doctorOpt.isEmpty() || patientOpt.isEmpty()) {
            return false;
        }
        
        return mappingRepository.existsByDoctorAndPatientAndActiveTrue(doctorOpt.get(), patientOpt.get());
    }
} 