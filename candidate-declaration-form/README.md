# Candidate Declaration Form 2 (CDF-2)

## Project structure

```text
candidate-declaration-form/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/cdf2/
    │   │       ├── Cdf2Application.java
    │   │       ├── controller/
    │   │       │   └── CandidateController.java
    │   │       ├── dto/
    │   │       │   ├── CandidateForm.java
    │   │       │   └── EmploymentForm.java
    │   │       ├── entity/
    │   │       │   ├── Candidate.java
    │   │       │   └── Employment.java
    │   │       ├── repository/
    │   │       │   └── CandidateRepository.java
    │   │       └── service/
    │   │           └── CandidateService.java
    │   └── resources/
    │       ├── application.properties
    │       ├── templates/
    │       │   ├── cdf2-form.html
    │       │   └── success.html
    │       └── static/
    │           ├── css/style.css
    │           ├── js/cdf2.js
    │           └── images/
    │               ├── cognizant-logo.png
    │               └── gear-pattern.svg
    └── test/
        └── java/com/cdf2/Cdf2ApplicationTests.java
```

## Package responsibilities

- `controller`: MVC routes and form submission handling.
- `dto`: Web form objects and validation.
- `entity`: JPA database entities.
- `repository`: Spring Data repositories.
- `service`: business logic and persistence orchestration.
- `resources/templates`: Thymeleaf pages.
- `resources/static`: CSS, JavaScript and image assets.

## Run in IntelliJ

1. Extract this ZIP.
2. In IntelliJ, choose **File → Open** and select the `candidate-declaration-form` folder (the folder containing `pom.xml`).
3. Set Project SDK to Java 17.
4. Wait for Maven import to finish.
5. Run `com.cdf2.Cdf2Application`.

Or run:

```bash
mvn clean test
mvn spring-boot:run
```

Open `http://localhost:8080`.

## Admin-assigned Candidate ID

Set `cdf2.admin-assigned-candidate-id` in `src/main/resources/application.properties`.
The form displays the ID as read-only and the controller reapplies the configured ID on submission. This configuration is suitable only for a single configured candidate at a time; a multi-candidate workflow needs authenticated admin functionality and a database-backed invitation/assignment record.

## Employer dropdown

The previous-employer dropdown contains Indian IT company choices and `OTHERS`. The searchable enhancement uses Tom Select from a CDN, so internet access is required for search behavior.

## Database

The starter uses the H2 file database configured in `application.properties`. Use synthetic data only during local testing. Before production, add authentication, authorization, CSRF/security review, audit logging, privacy controls and a production database. This is an independent development starter, not an official Cognizant system.
