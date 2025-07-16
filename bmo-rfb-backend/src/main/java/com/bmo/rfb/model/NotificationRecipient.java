package com.bmo.rfb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_recipients")
public class NotificationRecipient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "name")
    private String name;
    
    @Email
    @NotBlank
    @Column(name = "email")
    private String email;
    
    @Column(name = "department")
    private String department;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    public NotificationRecipient() {}
    
    public NotificationRecipient(String name, String email, String department, 
                               String role, String phone, String status) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.role = role;
        this.phone = phone;
        this.status = status;
        this.createdDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
