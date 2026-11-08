package com.cdf2.service;

import com.cdf2.dto.*;
import com.cdf2.entity.*;
import com.cdf2.repository.CandidateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CandidateService {
    private final CandidateRepository repository;
    public CandidateService(CandidateRepository repository){this.repository=repository;}

    @Transactional
    public Candidate save(CandidateForm form) {
        if (repository.findByCandidateId(form.getCandidateId()).isPresent())
            throw new IllegalArgumentException("Candidate ID already exists.");
        Candidate c = new Candidate();
        c.setCandidateId(form.getCandidateId().trim());
        c.setFirstName(form.getFirstName().trim());
        c.setMiddleName(form.getMiddleName());
        c.setLastName(form.getLastName().trim());
        c.setGender(form.getGender());
        c.setDateOfBirth(form.getDateOfBirth());
        c.setMobileNumber(form.getMobileNumber());
        c.setEmail(form.getEmail().trim());
        c.setPanNumber(form.getPanNumber().toUpperCase());
        c.setSpeciallyChallenged(form.isSpeciallyChallenged());
        c.setNationality(form.getNationality());
        c.setHighestQualification(form.getHighestQualification());
        c.setCurrentlyEmployed(form.getCurrentlyEmployed());
        c.setPreviousExperience(form.getPreviousExperience());

        if ("Yes".equalsIgnoreCase(form.getPreviousExperience()) && form.getEmployments()!=null) {
            for (EmploymentForm row : form.getEmployments()) {
                if (row.getEmployerName()!=null && !row.getEmployerName().isBlank()) {
                    Employment e = new Employment();
                    e.setEmployerName(row.getEmployerName());
                    e.setFromDate(row.getFromDate());
                    e.setToDate(row.getToDate());
                    e.setReasonForLeaving(row.getReasonForLeaving());
                    c.addEmployment(e);
                }
            }
        }
        return repository.save(c);
    }
}
