package com.cdf2.controller;

import com.cdf2.dto.CandidateForm;
import com.cdf2.dto.EmploymentForm;
import com.cdf2.entity.CandidateInvitation;
import com.cdf2.service.CandidateInvitationService;
import com.cdf2.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CandidateController {
    private final CandidateService candidateService;
    private final CandidateInvitationService invitationService;

    public CandidateController(CandidateService candidateService,
                               CandidateInvitationService invitationService) {
        this.candidateService = candidateService;
        this.invitationService = invitationService;
    }

    @GetMapping("/candidate/{token}")
    public String form(@PathVariable String token, Model model) {
        var invitation = invitationService.findUsableInvitation(token);
        if (invitation.isEmpty()) return "invitation-unavailable";

        CandidateForm form = new CandidateForm();
        form.setCandidateId(invitation.get().getCandidateId());
        form.setEmail(invitation.get().getCandidateEmail());
        form.setEmployments(new ArrayList<>(List.of(new EmploymentForm())));
        model.addAttribute("candidateForm", form);
        model.addAttribute("candidateIdAssigned", true);
        model.addAttribute("invitationToken", token);
        addOptions(model);
        return "cdf2-form";
    }

    @PostMapping("/candidate/{token}/submit")
    public String submit(@PathVariable String token,
                         @Valid @ModelAttribute("candidateForm") CandidateForm form,
                         BindingResult result,
                         Model model) {
        var invitationOptional = invitationService.findUsableInvitation(token);
        if (invitationOptional.isEmpty()) return "invitation-unavailable";

        CandidateInvitation invitation = invitationOptional.get();

        // The ID and email come from the server-side invitation, never from browser input.
        form.setCandidateId(invitation.getCandidateId());
        form.setEmail(invitation.getCandidateEmail());

        boolean employmentMissing =
                "Yes".equalsIgnoreCase(form.getPreviousExperience())
                && (form.getEmployments() == null
                    || form.getEmployments().stream().noneMatch(e ->
                        e.getEmployerName() != null && !e.getEmployerName().isBlank()));

        if (employmentMissing) {
            model.addAttribute("employmentError", "Please select your previous employer.");
        }

        if (result.hasErrors() || employmentMissing) {
            model.addAttribute("candidateIdAssigned", true);
            model.addAttribute("invitationToken", token);
            addOptions(model);
            return "cdf2-form";
        }

        try {
            var savedCandidate = candidateService.save(form);
            invitationService.markCompleted(invitation);
            model.addAttribute("candidateId", savedCandidate.getCandidateId());
            return "success";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("invitationMessage", ex.getMessage());
            return "invitation-unavailable";
        }
    }

    private void addOptions(Model model) {
        model.addAttribute("genders", List.of("Male", "Female", "Other", "Prefer not to say"));
        model.addAttribute("nationalities", List.of("Indian", "Other"));
        model.addAttribute("qualifications", List.of("10th", "12th", "Diploma",
                "Bachelor's Degree", "Master's Degree", "PhD", "Other"));
        model.addAttribute("leavingReasons", List.of("Better opportunity", "Career growth",
                "Personal reasons", "Relocation", "End of contract", "Other"));
    }
}
