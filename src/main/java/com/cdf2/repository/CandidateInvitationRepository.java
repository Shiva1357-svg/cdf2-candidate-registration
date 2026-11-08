package com.cdf2.repository;

import com.cdf2.entity.CandidateInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CandidateInvitationRepository extends JpaRepository<CandidateInvitation, Long> {
    Optional<CandidateInvitation> findByToken(String token);
    boolean existsByCandidateIdAndStatus(String candidateId, String status);
    List<CandidateInvitation> findAllByOrderByCreatedAtDesc();
}
