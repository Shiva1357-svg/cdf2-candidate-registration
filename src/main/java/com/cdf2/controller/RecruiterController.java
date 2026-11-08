package com.cdf2.controller;

import com.cdf2.dto.InvitationRequest;
import com.cdf2.entity.CandidateInvitation;
import com.cdf2.service.CandidateInvitationService;
import com.cdf2.service.InvitationEmailService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class RecruiterController {
    private final CandidateInvitationService invitationService;
    private final InvitationEmailService emailService;

    public RecruiterController(CandidateInvitationService invitationService,
                               InvitationEmailService emailService) {
        this.invitationService = invitationService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/recruiter";
    }

    @GetMapping("/recruiter")
    public String dashboard(Model model) {
        if (!model.containsAttribute("invitationRequest")) {
            model.addAttribute("invitationRequest", new InvitationRequest());
        }
        model.addAttribute("invitations", invitationService.findAll());
        return "recruiter-dashboard";
    }

    @PostMapping("/recruiter/invitations")
    public String createInvitation(@Valid @ModelAttribute InvitationRequest invitationRequest,
                                   BindingResult result,
                                   Model model,
                                   java.security.Principal principal,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("invitations", invitationService.findAll());
            return "recruiter-dashboard";
        }

        try {
            CandidateInvitation invitation =
                    invitationService.create(invitationRequest, principal.getName());

            String link = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/candidate/")
                    .path(invitation.getToken())
                    .toUriString();

            boolean sent = emailService.sendInvitation(
                    invitation.getCandidateEmail(), invitation.getCandidateId(), link);

            redirectAttributes.addFlashAttribute("generatedLink", link);
            redirectAttributes.addFlashAttribute("successMessage",
                    sent ? "Invitation email sent successfully."
                         : "Invitation created. Copy the link below and send it to the candidate. Email delivery is not configured.");
            return "redirect:/recruiter";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("invitationError", ex.getMessage());
            model.addAttribute("invitations", invitationService.findAll());
            return "recruiter-dashboard";
        }
    }
}
