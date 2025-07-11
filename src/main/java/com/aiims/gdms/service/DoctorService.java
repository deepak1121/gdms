package com.aiims.gdms.service;

import com.aiims.gdms.entity.*;
import com.aiims.gdms.repository.*;
import com.aiims.gdms.dto.ClinicalNoteInfo;
import com.aiims.gdms.dto.ClinicalNotesRequest;
import com.aiims.gdms.dto.DoctorPatientKickSessionDto;
import com.aiims.gdms.dto.GlucoseLogResponse;
import com.aiims.gdms.dto.MealItemDto;
import com.aiims.gdms.dto.MealLogDto;
import com.aiims.gdms.dto.PatientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    
    @Autowired
    private ClinicalNotesRepository clinicalNotesRepository;


    public void assignPatientToDoctor(String doctorUsername, String patientUsername) {
        User doctor = userRepository.findByUsername(doctorUsername)
            .orElseThrow(() -> new RuntimeException("Doctor with username " + doctorUsername + " not found"));

        User patient = userRepository.findByUsername(patientUsername)
            .orElseThrow(() -> new RuntimeException("Patient with username " + patientUsername + " not found"));

        if (doctor.getRole() != User.Role.DOCTOR) {
            throw new RuntimeException("The user " + doctorUsername + " is not a doctor. Please assign a valid doctor.");
        }

        if (patient.getRole() != User.Role.PATIENT) {
            throw new RuntimeException("The user " + patientUsername + " is not a patient. Please assign a valid patient.");
        }

        if (mappingRepository.existsByDoctorAndPatientAndActiveTrue(doctor, patient)) {
            throw new RuntimeException("A mapping already exists between this doctor and patient. Patient is already assigned.");
        }

        DoctorPatientMapping mapping = new DoctorPatientMapping(doctor, patient);
        mappingRepository.save(mapping);
    }

    
    public List<PatientResponseDto> getDoctorPatientsAsDto(Long doctorId) {
        Optional<User> doctorOpt = userRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            return List.of();
        }

        User doctor = doctorOpt.get();
        if (doctor.getRole() != User.Role.DOCTOR) {
            System.out.println("User is not a doctor.");
            return List.of();
        }

        List<DoctorPatientMapping> mappings = mappingRepository.findByDoctorAndActiveTrue(doctor);
        System.out.println("Found " + mappings.size() + " active patient mappings.");

        return mappings.stream()
                .map(mapping -> {
                    User patient = mapping.getPatient();
                    System.out.println("Mapping found for patient ID: " + patient.getId());

                    PatientProfile profile = patientProfileRepository.findByUser(patient)
                            .orElseThrow(() -> new RuntimeException("Patient profile not found"));

                    // ✅ Fetch clinical notes for this patient by this doctor
                    List<ClinicalNotes> notesList = clinicalNotesRepository
                            .findByDoctorAndPatientOrderByCreatedAtDesc(doctor, patient);

                    List<ClinicalNoteInfo> noteInfos = notesList.stream()
                            .map(note -> new ClinicalNoteInfo(
                                    note.getNotes(),
                                    note.getCreatedAt(),
                                   
                                    doctor.getUsername() // doctor username
                            ))
                            .collect(Collectors.toList());

                    // ✅ Build response DTO
                    PatientResponseDto dto = new PatientResponseDto();
                    dto.setId(patient.getId());
                    dto.setUsername(patient.getUsername());
                    dto.setFirstName(profile.getFirstName());
                    dto.setLastName(profile.getLastName());
                    dto.setPhoneNumber(profile.getPhoneNumber());
                    dto.setBirthYear(profile.getBirthYear());
                    dto.setGravida(profile.getGravida());
                    dto.setPara(profile.getPara());
                    dto.setLivingChildren(profile.getLivingChildren());
                    dto.setAbortions(profile.getAbortions());
                    dto.setLastMenstrualPeriod(profile.getLastMenstrualPeriod());
                    dto.setPhotoPath(profile.getPhotoPath());
                    dto.setClinicalNotes(noteInfos); // ✅ add note list

                    return dto;
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
    
    public List<GlucoseLogResponse> getPatientGlucoseLogs(Long doctorId, Long patientId, LocalDate fromDate, LocalDate toDate) {
        if (!isDoctorAssignedToPatient(doctorId, patientId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Doctor is not assigned to this patient");
        }

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        if (fromDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromDate is required.");
        }

        LocalDateTime startDateTime = fromDate.atStartOfDay();
        LocalDateTime endDateTime;

        if (toDate == null) {
            
            endDateTime = fromDate.plusDays(1).atStartOfDay().minusNanos(1);
        } else {
            endDateTime = toDate.plusDays(1).atStartOfDay().minusNanos(1);
        }

        List<GlucoseLog> logs = glucoseLogRepository.findByPatientAndTimestampBetween(patient, startDateTime, endDateTime);

        return logs.stream()
                .map(GlucoseLogResponse::new)
                .collect(Collectors.toList());
    }



      

    public List<MealLogDto> getPatientMealLogs(Long doctorId, Long patientId, LocalDate fromDate, LocalDate toDate) {
        if (!isDoctorAssignedToPatient(doctorId, patientId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Doctor is not assigned to this patient.");
        }

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        if (fromDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromDate is required.");
        }

        LocalDateTime startDateTime = fromDate.atStartOfDay();
        LocalDateTime endDateTime;

        if (toDate == null) {
            // If toDate is not provided, use only fromDate for that day
            endDateTime = fromDate.plusDays(1).atStartOfDay().minusNanos(1);
        } else {
            endDateTime = toDate.plusDays(1).atStartOfDay().minusNanos(1);
        }

        List<MealLog> logs = mealLogRepository.findByPatientAndTimestampBetweenOrderByTimestampDesc(
                patient, startDateTime, endDateTime
        );

        return logs.stream()
                .map(log -> {
                    List<MealItemDto> mealItems = log.getMealItems().stream()
                            .map(item -> {
                                MealMaster master = item.getMealMaster();
                                String mealType = master != null && master.getMealType() != null ? master.getMealType().name() : null;

                                return new MealItemDto(
                                        master != null ? master.getName() : item.getCustomMealName(),
                                        item.getQuantity(),
                                        item.getUnit(),
                                        mealType,
                                        master != null ? master.getCarbs() : null,
                                        master != null && master.isDoctorRecommended(),
                                        master != null ? master.getMealImage() : null
                                );
                            })
                            .collect(Collectors.toList());

                    return new MealLogDto(log.getMealType().toString(), mealItems);
                })
                .collect(Collectors.toList());
    }



    
    public List<SymptomLog> getPatientSymptomLogs(Long doctorId, Long patientId, LocalDate fromDate, LocalDate toDate) {
        if (!isDoctorAssignedToPatient(doctorId, patientId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Doctor is not assigned to this patient.");
        }

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found."));

        if (fromDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromDate is required.");
        }

        LocalDateTime startDateTime = fromDate.atStartOfDay();
        LocalDateTime endDateTime;

        if (toDate == null) {
            // Only one day — fromDate
            endDateTime = fromDate.plusDays(1).atStartOfDay().minusNanos(1);
        } else {
            // Range from fromDate to toDate
            endDateTime = toDate.plusDays(1).atStartOfDay().minusNanos(1);
        }

        return symptomLogRepository.findByPatientAndExactDateRange(patient, startDateTime, endDateTime);
    }



    
    public List<KickSession> getPatientCompletedKickSessions(Long doctorId, Long patientId) {
        if (!isDoctorAssignedToPatient(doctorId, patientId)) {
            throw new RuntimeException("Doctor is not assigned to this patient.");
        }
        
        User patient = userRepository.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        return kickSessionRepository.findByPatientAndCompletedTrueOrderByStartTimeDesc(patient);
    }
    
    
    

    public List<DoctorPatientKickSessionDto> getKickSessionsForDoctor(Long doctorId, Long patientId, LocalDate fromDate, LocalDate toDate) {

        if (!isDoctorAssignedToPatient(doctorId, patientId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Doctor is not assigned to this patient");
        }

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        if (fromDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromDate is required.");
        }

        LocalDateTime startDateTime = fromDate.atStartOfDay();
        LocalDateTime endDateTime = (toDate == null)
                ? fromDate.plusDays(1).atStartOfDay().minusNanos(1)
                : toDate.plusDays(1).atStartOfDay().minusNanos(1);

        List<KickSession> sessions = kickSessionRepository.findByPatientAndStartTimeBetweenOrderByStartTimeDesc(
                patient, startDateTime, endDateTime
        );

        return sessions.stream()
                .map(DoctorPatientKickSessionDto::new)
                .toList();
    }

    
    
    
    
    public void addClinicalNote(User doctor, ClinicalNotesRequest request) {
        Long patientId = request.getPatientId();

        if (patientId == null) {
            throw new IllegalArgumentException("Patient ID must not be null.");
        }

        User patient = userRepository.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

        ClinicalNotes note = new ClinicalNotes();
        note.setDoctor(doctor);
        note.setPatient(patient);
        note.setCreatedAt(LocalDateTime.now());
        note.setNotes(request.getNotes());

        clinicalNotesRepository.save(note);
    }

    
    
    
    




    
    
    public boolean isDoctorAssignedToPatient(Long doctorId, Long patientId) {
        Optional<User> doctorOpt = userRepository.findById(doctorId);
        Optional<User> patientOpt = userRepository.findById(patientId);
        
        if (doctorOpt.isEmpty() || patientOpt.isEmpty()) {
            return false;
        }
        
        return mappingRepository.existsByDoctorAndPatientAndActiveTrue(doctorOpt.get(), patientOpt.get());
    }
    
} 