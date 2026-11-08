package com.cdf2.service;

import com.cdf2.dto.InvitationRequest;
import com.cdf2.entity.CandidateInvitation;
import com.cdf2.repository.CandidateInvitationRepository;
import com.cdf2.repository.CandidateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateInvitationService {
    private final CandidateInvitationRepository invitationRepository;
    private final CandidateRepository candidateRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public CandidateInvitationService(CandidateInvitationRepository invitationRepository,
                                      CandidateRepository candidateRepository) {
        this.invitationRepository = invitationRepository;
        this.candidateRepository = candidateRepository;
    }

    @Transactional
    public CandidateInvitation create(InvitationRequest request, String recruiterUsername) {
        String candidateId = request.getCandidateId().trim();
        if (candidateRepository.findByCandidateId(candidateId).isPresent()) {
            throw new IllegalArgumentException("This Candidate ID has already submitted CDF-2.");
        }
        if (invitationRepository.existsByCandidateIdAndStatus(candidateId, "PENDING")) {
            throw new IllegalArgumentException("An active invitation already exists for this Candidate ID.");
        }

        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);

        CandidateInvitation invitation = new CandidateInvitation();
        invitation.setToken(Base64.getUrlEncoder().withoutPadding().encodeToString(bytes));
        invitation.setCandidateId(candidateId);
        invitation.setCandidateEmail(request.getCandidateEmail().trim().toLowerCase());
        invitation.setStatus("PENDING");
        invitation.setCreatedAt(LocalDateTime.now());
        invitation.setExpiresAt(LocalDateTime.now().plusDays(7));
        invitation.setCreatedBy(recruiterUsername);
        return invitationRepository.save(invitation);
    }

    @Transactional
    public Optional<CandidateInvitation> findUsableInvitation(String token) {
        Optional<CandidateInvitation> result = invitationRepository.findByToken(token);
        if (result.isEmpty()) return Optional.empty();

        CandidateInvitation invitation = result.get();
        if (!"PENDING".equals(invitation.getStatus())) return Optional.empty();

        if (invitation.getExpiresAt().isBefore(LocalDateTime.now())) {
            invitation.setStatus("EXPIRED");
            invitationRepository.save(invitation);
            return Optional.empty();
        }
        return Optional.of(invitation);
    }

    @Transactional
    public void markCompleted(CandidateInvitation invitation) {
        invitation.setStatus("COMPLETED");
        invitation.setCompletedAt(LocalDateTime.now());
        invitationRepository.save(invitation);
    }

    public List<CandidateInvitation> findAll() {
        return invitationRepository.findAllByOrderByCreatedAtDesc();
    }
}
