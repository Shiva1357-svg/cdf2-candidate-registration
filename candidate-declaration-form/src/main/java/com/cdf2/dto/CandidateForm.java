package com.cdf2.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandidateForm {
    @NotBlank private String candidateId;
    @NotBlank private String firstName;
    private String middleName;
    @NotBlank private String lastName;
    @NotBlank private String gender;
    @NotNull @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) private LocalDate dateOfBirth;
    @NotBlank @Pattern(regexp="[0-9]{10}", message="Enter a valid 10-digit mobile number") private String mobileNumber;
    @NotBlank @Email private String email;
    @NotBlank @Pattern(regexp="[A-Za-z]{5}[0-9]{4}[A-Za-z]", message="Enter a valid PAN number") private String panNumber;
    private boolean speciallyChallenged;
    @NotBlank private String nationality;
    @NotBlank private String highestQualification;
    private String currentlyEmployed;
    private String previousExperience;
    @Valid private List<EmploymentForm> employments = new ArrayList<>();

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
    public List<EmploymentForm> getEmployments(){return employments;} public void setEmployments(List<EmploymentForm> v){employments=v;}
}
