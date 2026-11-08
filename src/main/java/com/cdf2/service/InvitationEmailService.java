package com.cdf2.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class InvitationEmailService {
    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Value("${spring.mail.host:}")
    private String mailHost;

    @Value("${cdf2.mail.from:noreply@example.com}")
    private String fromAddress;

    public InvitationEmailService(ObjectProvider<JavaMailSender> mailSenderProvider) {
        this.mailSenderProvider = mailSenderProvider;
    }

    public boolean sendInvitation(String recipient, String candidateId, String link) {
        if (mailHost == null || mailHost.isBlank()) return false;

        JavaMailSender sender = mailSenderProvider.getIfAvailable();
        if (sender == null) return false;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(recipient);
        message.setSubject("Action required: Complete your Candidate Declaration Form-2");
        message.setText(
                "Hello,\n\nPlease complete your Candidate Declaration Form-2 using the secure link below.\n\n"
                + "Candidate ID: " + candidateId + "\n"
                + "Form link: " + link + "\n\n"
                + "This link expires in 7 days and can be used only once.\n\n"
                + "Regards,\nRecruitment Team"
        );
        try {
            sender.send(message);
            return true;
        } catch (MailException ex) {
            // Keep the generated link available in the recruiter dashboard if SMTP is unavailable.
            return false;
        }
    }
}
