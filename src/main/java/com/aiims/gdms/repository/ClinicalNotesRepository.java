package com.aiims.gdms.repository;

import com.aiims.gdms.entity.ClinicalNotes;
import com.aiims.gdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicalNotesRepository extends JpaRepository<ClinicalNotes, Long> {
    List<ClinicalNotes> findByDoctor(User doctor);
    List<ClinicalNotes> findByPatient(User patient);
    List<ClinicalNotes> findByDoctorAndPatient(User doctor, User patient);
    List<ClinicalNotes> findByDoctorAndPatientOrderByCreatedAtDesc(User doctor, User patient);


    
}

