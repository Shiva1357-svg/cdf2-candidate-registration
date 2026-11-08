package com.cdf2.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="candidate_employments")
public class Employment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private String employerName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reasonForLeaving;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="candidate_id")
    private Candidate candidate;
    public Long getId(){return id;}
    public String getEmployerName(){return employerName;} public void setEmployerName(String v){employerName=v;}
    public LocalDate getFromDate(){return fromDate;} public void setFromDate(LocalDate v){fromDate=v;}
    public LocalDate getToDate(){return toDate;} public void setToDate(LocalDate v){toDate=v;}
    public String getReasonForLeaving(){return reasonForLeaving;} public void setReasonForLeaving(String v){reasonForLeaving=v;}
    public void setCandidate(Candidate v){candidate=v;}
}
