package com.aiims.gdms.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import java.util.List;

import com.aiims.gdms.entity.MealMaster.MealType;

@Entity
@Table(name = "meal_logs")
public class  MealLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnore
    private User patient;
    
    @OneToMany(mappedBy = "mealLog", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MealItem> mealItems;

    @Enumerated(EnumType.STRING)
    private MealType mealType;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column
    private Integer estimatedCarbohydrates;
    
    @Column
    private String notes;
    
        
        // Constructors
        public MealLog() {}
        
        public MealLog(User patient, MealType mealType) {
            this.patient = patient;
            this.mealType = mealType;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getPatient() {
        return patient;
    }
    
    public void setPatient(User patient) {
        this.patient = patient;
    }
    

    public List<MealItem> getMealItems() {
        return mealItems;
    }

    public void setMealItems(List<MealItem> mealItems) {
        this.mealItems = mealItems;
    }

    public MealType getMealType() {
        return mealType;
    }
    
    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public Integer getEstimatedCarbohydrates() {
        return estimatedCarbohydrates;
    }
    
    public void setEstimatedCarbohydrates(Integer estimatedCarbohydrates) {
        this.estimatedCarbohydrates = estimatedCarbohydrates;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
} 