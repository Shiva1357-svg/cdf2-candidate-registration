package com.cdf2.controller;

import com.cdf2.dto.CandidateForm;
import com.cdf2.dto.EmploymentForm;
import com.cdf2.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CandidateController {

    private final CandidateService service;

    // Set by the administrator in application.properties for this candidate invitation.
    @Value("${cdf2.admin-assigned-candidate-id:}")
    private String adminAssignedCandidateId;

    public CandidateController(CandidateService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String form(Model model) {
        CandidateForm form = new CandidateForm();
        form.setCandidateId(adminAssignedCandidateId);
        form.setEmployments(new ArrayList<>(List.of(new EmploymentForm())));
        model.addAttribute("candidateForm", form);
        model.addAttribute("candidateIdAssigned", adminAssignedCandidateId != null
                && !adminAssignedCandidateId.isBlank());
        addOptions(model);
        return "cdf2-form";
    }

    @PostMapping("/submit")
    public String submit(
            @Valid @ModelAttribute("candidateForm") CandidateForm form,
            BindingResult result,
            Model model) {

        // Never trust a candidate ID posted by the browser. Reapply the admin-assigned value.
        form.setCandidateId(adminAssignedCandidateId);

        boolean candidateIdNotAssigned = adminAssignedCandidateId == null
                || adminAssignedCandidateId.isBlank();

        boolean employmentMissing =
                "Yes".equalsIgnoreCase(form.getPreviousExperience())
                && (form.getEmployments() == null
                    || form.getEmployments().stream().noneMatch(e ->
                        e.getEmployerName() != null && !e.getEmployerName().isBlank()));

        if (candidateIdNotAssigned) {
            model.addAttribute("candidateIdError",
                    "Candidate ID has not been assigned by the administrator yet.");
        }

        if (employmentMissing) {
            model.addAttribute("employmentError",
                    "Please select your previous employer.");
        }

        if (result.hasErrors() || employmentMissing || candidateIdNotAssigned) {
            model.addAttribute("candidateIdAssigned", !candidateIdNotAssigned);
            addOptions(model);
            return "cdf2-form";
        }

        try {
            var savedCandidate = service.save(form);
            model.addAttribute("candidateId", savedCandidate.getCandidateId());
            return "success";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("candidateIdAssigned", true);
            result.rejectValue("candidateId", "duplicate", ex.getMessage());
            addOptions(model);
            return "cdf2-form";
        }
    }

    private void addOptions(Model model) {
        model.addAttribute("genders",
                List.of("Male", "Female", "Other", "Prefer not to say"));
        model.addAttribute("nationalities", List.of("Indian", "Other"));
        model.addAttribute("qualifications",
                List.of("10th", "12th", "Diploma", "Bachelor's Degree",
                        "Master's Degree", "PhD", "Other"));
        model.addAttribute("leavingReasons",
                List.of("Better opportunity", "Career growth", "Personal reasons",
                        "Relocation", "End of contract", "Other"));
    }
}
