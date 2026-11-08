package com.cdf2.dto;

import java.time.LocalDate;

public class EmploymentForm {
    private String employerName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reasonForLeaving;
    public String getEmployerName(){return employerName;} public void setEmployerName(String v){employerName=v;}
    public LocalDate getFromDate(){return fromDate;} public void setFromDate(LocalDate v){fromDate=v;}
    public LocalDate getToDate(){return toDate;} public void setToDate(LocalDate v){toDate=v;}
    public String getReasonForLeaving(){return reasonForLeaving;} public void setReasonForLeaving(String v){reasonForLeaving=v;}
}
