package com.cdf2.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String candidateId;
    @Column(nullable=false) private String firstName;
    private String middleName;
    @Column(nullable=false) private String lastName;
    @Column(nullable=false) private String gender;
    @Column(nullable=false) private LocalDate dateOfBirth;
    @Column(nullable=false) private String mobileNumber;
    @Column(nullable=false) private String email;
    @Column(nullable=false) private String panNumber;
    private boolean speciallyChallenged;
    @Column(nullable=false) private String nationality;
    @Column(nullable=false) private String highestQualification;
    private String currentlyEmployed;
    private String previousExperience;

    @OneToMany(mappedBy="candidate", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Employment> employments = new ArrayList<>();

    public void addEmployment(Employment employment) {
        employment.setCandidate(this);
        employments.add(employment);
    }
    public Long getId(){return id;}
    public String getCandidateId(){return candidateId;} public void setCandidateId(String v){candidateId=v;}
    public String getFirstName(){return firstName;} public void setFirstName(String v){firstName=v;}
    public String getMiddleName(){return middleName;} public void setMiddleName(String v){middleName=v;}
    public String getLastName(){return lastName;} public void setLastName(String v){lastName=v;}
    public String getGender(){return gender;} public void setGender(String v){gender=v;}
    public LocalDate getDateOfBirth(){return dateOfBirth;} public void setDateOfBirth(LocalDate v){dateOfBirth=v;}
    public String getMobileNumber(){return mobileNumber;} public void setMobileNumber(String v){mobileNumber=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPanNumber(){return panNumber;} public void setPanNumber(String v){panNumber=v;}
    public boolean isSpeciallyChallenged(){return speciallyChallenged;} public void setSpeciallyChallenged(boolean v){speciallyChallenged=v;}
    public String getNationality(){return nationality;} public void setNationality(String v){nationality=v;}
    public String getHighestQualification(){return highestQualification;} public void setHighestQualification(String v){highestQualification=v;}
    public String getCurrentlyEmployed(){return currentlyEmployed;} public void setCurrentlyEmployed(String v){currentlyEmployed=v;}
    public String getPreviousExperience(){return previousExperience;} public void setPreviousExperience(String v){previousExperience=v;}
    public List<Employment> getEmployments(){return employments;}
}
