# CDF-2 Recruiter Portal

**Important:** Open this exact `cdf2-recruiter-portal` folder in IntelliJ. The `pom.xml` file must be visible directly under the project root (not one folder deeper).

# CDF-2 Candidate Declaration Form

Spring Boot 3.4.5 / Java 17 / Maven / Thymeleaf application with a recruiter invitation workflow.

## Workflow
1. Recruiter signs in at `/login`.
2. Recruiter creates an invitation using a Candidate ID and candidate email address.
3. Application generates a cryptographically random, candidate-specific link.
4. If SMTP is configured, the link is emailed automatically. Otherwise, recruiter copies the link and sends it manually.
5. Candidate opens the link. Candidate ID and email are populated from the server-side invitation and displayed read-only.
6. The candidate submits the form. The invitation becomes `COMPLETED` and cannot be reused.
7. Invitations expire after 7 days. Recruiter dashboard shows Pending, Completed and Expired invitations.

## Project structure
```text
candidate-declaration-form/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/cdf2/
    │   │   ├── Cdf2Application.java
    │   │   ├── config/SecurityConfig.java
    │   │   ├── controller/
    │   │   │   ├── CandidateController.java
    │   │   │   ├── LoginController.java
    │   │   │   └── RecruiterController.java
    │   │   ├── dto/
    │   │   │   ├── CandidateForm.java
    │   │   │   ├── EmploymentForm.java
    │   │   │   └── InvitationRequest.java
    │   │   ├── entity/
    │   │   │   ├── Candidate.java
    │   │   │   ├── CandidateInvitation.java
    │   │   │   └── Employment.java
    │   │   ├── repository/
    │   │   │   ├── CandidateRepository.java
    │   │   │   └── CandidateInvitationRepository.java
    │   │   └── service/
    │   │       ├── CandidateService.java
    │   │       ├── CandidateInvitationService.java
    │   │       └── InvitationEmailService.java
    │   └── resources/
    │       ├── application.properties
    │       ├── templates/
    │       │   ├── cdf2-form.html
    │       │   ├── login.html
    │       │   ├── recruiter-dashboard.html
    │       │   ├── invitation-unavailable.html
    │       │   └── success.html
    │       └── static/{css,js,images}/
    └── test/java/com/cdf2/
```

## Run locally
1. Open the folder containing `pom.xml` in IntelliJ.
2. Use JDK 17.
3. Run `mvn clean test`.
4. Run `mvn spring-boot:run`.
5. Open `http://localhost:8080/login`.

Local development recruiter credentials:
- Username: `recruiter`
- Password: `ChangeMe-Immediately-123!`

Set `CDF2_RECRUITER_USERNAME` and a strong `CDF2_RECRUITER_PASSWORD` before any shared/public deployment. Do not keep the local default password in production.

## Optional automatic email
Configure SMTP environment variables:
- `MAIL_HOST`
- `MAIL_PORT` (usually 587)
- `MAIL_USERNAME`
- `MAIL_PASSWORD`
- `MAIL_FROM`

Without SMTP configuration, the recruiter dashboard still generates a unique link with a Copy Link action, which the recruiter can send using their normal email client.

## Database
H2 file storage is used for local development. For Render or any multi-instance/production deployment, configure PostgreSQL using `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, and `SPRING_DATASOURCE_DRIVER=org.postgresql.Driver`, and add the PostgreSQL JDBC driver dependency to the build. H2's local file should not be relied on for production persistence.

## Security and production checklist
- Change the default recruiter credentials and use HTTPS.
- Configure SMTP credentials using Render environment variables, never source control.
- Configure PostgreSQL with backups and restricted network access.
- Candidate invitation links are bearer secrets; do not publish them or put them in logs.
- This starter has a single in-memory recruiter account. Add a database-backed recruiter/user management system and role-based access before use by multiple recruiters.
- Add rate limiting, CSRF review, audit logging, retention/deletion rules and security testing before processing real PAN or other sensitive personal information.
- This is an independent development starter, not an official Cognizant system. Use synthetic candidate data while testing.
