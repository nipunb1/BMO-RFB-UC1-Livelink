package com.bmo.rfb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "testing_results")
public class TestingResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "uen")
    private String uen;
    
    @NotBlank
    @Column(name = "borrower_name")
    private String borrowerName;
    
    @Column(name = "statement_desc", columnDefinition = "TEXT")
    private String statementDesc;
    
    @Column(name = "tracking_item")
    private String trackingItem;
    
    @Column(name = "due_days")
    private Integer dueDays;
    
    @Column(name = "threshold")
    private Double threshold;
    
    @Column(name = "result")
    private Double result;
    
    @Column(name = "pass_fail")
    private String passFail;
    
    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "approval_status")
    private String approvalStatus;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    public TestingResult() {}
    
    public TestingResult(String uen, String borrowerName, String statementDesc, 
                        String trackingItem, Integer dueDays, Double threshold, 
                        Double result, String passFail, String comments, 
                        String status, String approvalStatus) {
        this.uen = uen;
        this.borrowerName = borrowerName;
        this.statementDesc = statementDesc;
        this.trackingItem = trackingItem;
        this.dueDays = dueDays;
        this.threshold = threshold;
        this.result = result;
        this.passFail = passFail;
        this.comments = comments;
        this.status = status;
        this.approvalStatus = approvalStatus;
        this.createdDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUen() { return uen; }
    public void setUen(String uen) { this.uen = uen; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public String getStatementDesc() { return statementDesc; }
    public void setStatementDesc(String statementDesc) { this.statementDesc = statementDesc; }

    public String getTrackingItem() { return trackingItem; }
    public void setTrackingItem(String trackingItem) { this.trackingItem = trackingItem; }

    public Integer getDueDays() { return dueDays; }
    public void setDueDays(Integer dueDays) { this.dueDays = dueDays; }

    public Double getThreshold() { return threshold; }
    public void setThreshold(Double threshold) { this.threshold = threshold; }

    public Double getResult() { return result; }
    public void setResult(Double result) { this.result = result; }

    public String getPassFail() { return passFail; }
    public void setPassFail(String passFail) { this.passFail = passFail; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
