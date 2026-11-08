package com.cdf2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class InvitationRequest {
    @NotBlank
    @Size(max = 80)
    @Pattern(regexp = "[A-Za-z0-9_-]+", message = "Use letters, numbers, hyphens or underscores only")
    private String candidateId;

    @NotBlank
    @Email
    @Size(max = 254)
    private String candidateEmail;

    public String getCandidateId() { return candidateId; }
    public void setCandidateId(String candidateId) { this.candidateId = candidateId; }
    public String getCandidateEmail() { return candidateEmail; }
    public void setCandidateEmail(String candidateEmail) { this.candidateEmail = candidateEmail; }
}
