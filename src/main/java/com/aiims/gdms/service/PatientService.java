package com.aiims.gdms.service;

import com.aiims.gdms.dto.ClinicalNoteInfo;
import com.aiims.gdms.dto.GlucoseLogDto;
import com.aiims.gdms.dto.KickCountLogDto;
import com.aiims.gdms.dto.MealItemDto;
import com.aiims.gdms.dto.MealItemRequest;
import com.aiims.gdms.dto.MealLogDto;
import com.aiims.gdms.dto.MealLogRequest;
import com.aiims.gdms.dto.NotificationResponse;
import com.aiims.gdms.dto.PatientLogsResponse;
import com.aiims.gdms.dto.PatientResponseDto;
import com.aiims.gdms.dto.SymptomLogDto;
import com.aiims.gdms.dto.SymptomLogRequest;
import com.aiims.gdms.entity.*;
import com.aiims.gdms.entity.MealMaster.MealType;
import com.aiims.gdms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GlucoseLogRepository glucoseLogRepository;
    
    @Autowired
    private MealLogRepository mealLogRepository;

    @Autowired
    private MealMasterRepository mealMasterRepository;
    
    @Autowired
    private SymptomLogRepository symptomLogRepository;
    
    @Autowired
    private KickSessionRepository kickSessionRepository;
    
    @Autowired
    private KickEventRepository kickEventRepository;
    
    @Autowired
    private PatientProfileRepository patientProfileRepository;
    
    @Autowired
    private ClinicalNotesRepository clinicalNotesRepository;

    public GlucoseLog logGlucose(Long patientId, Double glucoseLevel, GlucoseLog.ReadingType readingType, String notes) {
        Optional<User> patientOpt = userRepository.findById(patientId);
        if (patientOpt.isEmpty() || patientOpt.get().getRole() != User.Role.PATIENT) {
            return null;
        }
        
        GlucoseLog log = new GlucoseLog(patientOpt.get(), glucoseLevel, readingType);
        log.setNotes(notes);
        return glucoseLogRepository.save(log);
    }
    
    public MealLog createMealLog(Long patientId, MealLogRequest request) {
        User patient = userRepository.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Convert string to enum
        MealType mealType;
        try {
            mealType = MealType.valueOf(request.getMealType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid meal type: " + request.getMealType());
        }

        MealLog log = new MealLog();
        log.setPatient(patient);
        log.setTimestamp(LocalDateTime.now());
        log.setMealType(mealType);
        log.setNotes(request.getNotes());

        List<MealItem> items = new ArrayList<>();
        if (request.getMealItems() != null) {
            for (MealItemRequest itemReq : request.getMealItems()) {
                MealItem item = new MealItem();
                item.setMealLog(log);
                if (itemReq.getMealMasterId() != null) {
                    Optional<MealMaster> masterOpt = mealMasterRepository.findById(itemReq.getMealMasterId());
                    MealMaster master = masterOpt.orElseThrow(() -> new RuntimeException("MealMaster not found"));
                    item.setMealMaster(master);
                } else {
                    item.setCustomMealName(itemReq.getCustomMealName());
                }
                item.setQuantity(Double.valueOf(itemReq.getQuantity()));
                item.setUnit(itemReq.getUnit());
                items.add(item);
            }
        }

        log.setMealItems(items);
        return mealLogRepository.save(log);
    }
    
    
    public SymptomLog logSymptoms(User patient, SymptomLogRequest symptoms) {
        
        SymptomLog log = new SymptomLog();
        log.setPatient(patient);
        log.setTimestamp(LocalDateTime.now());
        log.setSeverity(symptoms.getSeverity());
        log.setNotes(symptoms.getNotes());
        if(symptoms.getSymptoms().isEmpty() || symptoms.isNoSymptoms()){
            log.setNoSymptoms(symptoms.isNoSymptoms());
        }
        else{
            log.setNoSymptoms(false);
        }      
        log.setSymptoms(symptoms.getSymptoms());

        
        return symptomLogRepository.save(log);
    }
    
    public List<GlucoseLog> getPatientGlucoseLogs(Long patientId) {
        Optional<User> patientOpt = userRepository.findById(patientId);
        if (patientOpt.isEmpty() || patientOpt.get().getRole() != User.Role.PATIENT) {
            return List.of();
        }
        
        return glucoseLogRepository.findByPatientOrderByTimestampDesc(patientOpt.get());
    }
    
    public List<MealLog> getPatientMealLogs(Long patientId) {
        Optional<User> patientOpt = userRepository.findById(patientId);
        if (patientOpt.isEmpty() || patientOpt.get().getRole() != User.Role.PATIENT) {
            return List.of();
        }
        
        return mealLogRepository.findByPatientOrderByTimestampDesc(patientOpt.get());
    }
    
    public List<SymptomLog> getPatientSymptomLogs(Long patientId) {
        Optional<User> patientOpt = userRepository.findById(patientId);
        if (patientOpt.isEmpty() || patientOpt.get().getRole() != User.Role.PATIENT) {
            return List.of();
        }
        
        return symptomLogRepository.findByPatientOrderByTimestampDesc(patientOpt.get());
    }
    
    public NotificationResponse getNotifications(Long patientId) {
        Optional<User> patientOpt = userRepository.findById(patientId);
        if (patientOpt.isEmpty() || patientOpt.get().getRole() != User.Role.PATIENT) {
            return new NotificationResponse(List.of(), List.of());
        }
        
        LocalDate today = LocalDate.now();
        User patient = patientOpt.get();
        
        List<String> missingLogs = new ArrayList<>();
        List<String> reminders = new ArrayList<>();
        
        // Check glucose logs
        long glucoseCount = glucoseLogRepository.countByPatientAndDate(patient, today);
        if (glucoseCount == 0) {
            missingLogs.add("Glucose reading");
            reminders.add("Log your glucose reading for today");
        } else if (glucoseCount < 4) {
            reminders.add("Consider logging additional glucose readings");
        }
        
        // Check meal logs
        long mealCount = mealLogRepository.countByPatientAndDate(patient, today);
        if (mealCount == 0) {
            missingLogs.add("Meal log");
            reminders.add("Log your meals for today");
        }
        
        // Check symptom logs
        long symptomCount = symptomLogRepository.countByPatientAndDate(patient, today);
        if (symptomCount == 0) {
            missingLogs.add("Symptom log");
            reminders.add("Log any symptoms you're experiencing");
        }
        
        // Check kick count logs
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusHours(12);
        
        if (kickSessionRepository.findActiveSession(patient, windowStart).isEmpty()) {
            missingLogs.add("Kick count");
            reminders.add("Perform and log fetal kick count");
        }
        
        return new NotificationResponse(missingLogs, reminders);
    }

    // Count-to-Ten Kick Count Logic
    public KickSession logKick(User patient) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusHours(12);
        // Find active session in the last 12 hours
        Optional<KickSession> sessionOpt = kickSessionRepository.findActiveSession(patient, windowStart);
        KickSession session;
        if (sessionOpt.isPresent()) 
            {
                session = sessionOpt.get();
                // If session is completed, start a new one
                if (session.isCompleted() || session.getStartTime().isBefore(windowStart)) 
                    {
                        session = new KickSession(patient, now);
                    }
            } 
        else 
            {
                session = new KickSession(patient, now);
            }
        // Save session if new
        if (session.getId() == null) {
            session = kickSessionRepository.save(session);
        }
        // Add kick event
        KickEvent kick = new KickEvent(session, now);
        kickEventRepository.save(kick);
        session.getKicks().add(kick);
        // Complete session if 10 kicks or 12 hours
        if (session.getKicks().size() >= 10) {
            session.setCompleted(true);
            session.setEndTime(now);
        } else if (now.isAfter(session.getStartTime().plusHours(12))) {
            session.setCompleted(true);
            session.setEndTime(now);
        }
        kickSessionRepository.save(session);
        return session;
    }

    public KickSession getCurrentKickSession(User patient) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusHours(12);
        Optional<KickSession> sessionOpt = kickSessionRepository.findActiveSession(patient, windowStart);
        return sessionOpt.orElse(null);
    }

    public List<KickSession> getPatientKickSessionHistory(User patient) {
        return kickSessionRepository.findByPatientAndCompletedTrueOrderByStartTimeDesc(patient);
    }

    public PatientResponseDto getPatientDetails(User patient) {
        PatientProfile profile = patientProfileRepository.findByUser(patient)
                .orElseThrow(() -> new RuntimeException("Patient Details are not fetched"));

        // Fetch clinical notes for this patient (optional filter by doctor if needed)
        List<ClinicalNotes> notes = clinicalNotesRepository.findByPatient(patient);

        List<ClinicalNoteInfo> noteInfoList = notes.stream()
                .map(note -> new ClinicalNoteInfo(note.getNotes(), note.getCreatedAt(), note.getDoctor().getUsername()))
                .collect(Collectors.toList());

        return new PatientResponseDto(
                patient.getId(),
                patient.getUsername(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhoneNumber(),
                profile.getBirthYear(),
                profile.getGravida(),
                profile.getPara(),
                profile.getLivingChildren(),
                profile.getAbortions(),
                profile.getLastMenstrualPeriod(),
                profile.getPhotoPath(),
                noteInfoList 
        );
    }


	
	
	
	public PatientLogsResponse getPatientLogs(User patient, String date) {

	    PatientLogsResponse response = new PatientLogsResponse();

	    // Kick Counts
	    List<KickCountLogDto> kickCounts = kickSessionRepository.findByPatientOrderByStartTimeDesc(patient)
	            .stream()
	            .filter(session -> isSameDate(session.getStartTime(), date))
	            .map(session -> new KickCountLogDto(
	                    session.getStartTime(),
	                    session.getEndTime(),
	                    kickEventRepository.findBySession(session).size()
	            ))
	            .collect(Collectors.toList());
	    response.setKickCounts(kickCounts);

	    // Meal Logs
	    List<MealLogDto> meals = mealLogRepository.findByPatient(patient)
	    	    .stream()
	    	    .filter(meal -> isSameDate(meal.getTimestamp(), date))
	    	    .map(meal -> new MealLogDto(
	    	            meal.getMealType().name(),
	    	            meal.getMealItems().stream()
	    	                    .map(item -> new MealItemDto(
	    	                            item.getCustomMealName() != null ? item.getCustomMealName()
	    	                                    : (item.getMealMaster() != null ? item.getMealMaster().getName() : null),
	    	                            item.getQuantity(),
	    	                            item.getUnit(),
	    	                            item.getMealMaster() != null && item.getMealMaster().getMealType() != null
	    	                                    ? item.getMealMaster().getMealType().name()
	    	                                    : null,
	    	                            item.getMealMaster() != null ? item.getMealMaster().getCarbs() : null,
	    	                            item.getMealMaster() != null && item.getMealMaster().isDoctorRecommended(),
	    	                            item.getMealMaster() != null ? item.getMealMaster().getMealImage() : null  // ✅ Added mealImage
	    	                    ))
	    	                    .collect(Collectors.toList())
	    	    ))
	    	    .collect(Collectors.toList());

	    	response.setMeals(meals);


	    // Glucose Logs
	    List<GlucoseLogDto> glucoseLogs = glucoseLogRepository.findByPatient(patient)
	            .stream()
	            .filter(glucose -> isSameDate(glucose.getTimestamp(), date))
	            .map(glucose -> new GlucoseLogDto(
	                    glucose.getGlucoseLevel(),
	                    glucose.getReadingType().name()
	            ))
	            .collect(Collectors.toList());
	    response.setGlucoseLogs(glucoseLogs);

	    // Symptom Logs
	    List<SymptomLogDto> symptomLogs = symptomLogRepository.findByPatient(patient)
	            .stream()
	            .filter(symptom -> isSameDate(symptom.getTimestamp(), date))
	            .map(symptom -> new SymptomLogDto(
	                    symptom.getSymptoms(),
	                    symptom.getSeverity(),
	                    symptom.isNoSymptoms(),
	                    symptom.getNotes()
	            ))
	            .collect(Collectors.toList());
	    response.setSymptomLogs(symptomLogs);

	    return response;
	}
	
	
	private boolean isSameDate(LocalDateTime timestamp, String date) {
	    if (date == null || date.isBlank()) {
	        return true;
	    }
	    date = date.trim();
	    LocalDate targetDate = LocalDate.parse(date);
	    return timestamp.toLocalDate().isEqual(targetDate);
	}




	
} 