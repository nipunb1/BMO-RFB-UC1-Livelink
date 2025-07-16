package com.bmo.rfb.controller;

import com.bmo.rfb.model.NotificationRecipient;
import com.bmo.rfb.service.NotificationRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notification-recipients")
@CrossOrigin(origins = "*")
public class NotificationRecipientController {
    
    @Autowired
    private NotificationRecipientService notificationRecipientService;
    
    @GetMapping
    public ResponseEntity<List<NotificationRecipient>> getAllRecipients() {
        List<NotificationRecipient> recipients = notificationRecipientService.getAllRecipients();
        return ResponseEntity.ok(recipients);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NotificationRecipient> getRecipientById(@PathVariable Long id) {
        Optional<NotificationRecipient> recipient = notificationRecipientService.getRecipientById(id);
        return recipient.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<NotificationRecipient>> getActiveRecipients() {
        List<NotificationRecipient> recipients = notificationRecipientService.getActiveRecipients();
        return ResponseEntity.ok(recipients);
    }
    
    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<NotificationRecipient>> searchRecipients(@PathVariable String searchTerm) {
        List<NotificationRecipient> recipients = notificationRecipientService.searchRecipients(searchTerm);
        return ResponseEntity.ok(recipients);
    }
    
    @GetMapping("/department/{department}")
    public ResponseEntity<List<NotificationRecipient>> getRecipientsByDepartment(@PathVariable String department) {
        List<NotificationRecipient> recipients = notificationRecipientService.getRecipientsByDepartment(department);
        return ResponseEntity.ok(recipients);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationRecipient>> getRecipientsByStatus(@PathVariable String status) {
        List<NotificationRecipient> recipients = notificationRecipientService.getRecipientsByStatus(status);
        return ResponseEntity.ok(recipients);
    }
    
    @PostMapping
    public ResponseEntity<NotificationRecipient> createRecipient(@RequestBody NotificationRecipient recipient) {
        NotificationRecipient savedRecipient = notificationRecipientService.saveRecipient(recipient);
        return ResponseEntity.ok(savedRecipient);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<NotificationRecipient> updateRecipient(@PathVariable Long id, @RequestBody NotificationRecipient recipientDetails) {
        NotificationRecipient updatedRecipient = notificationRecipientService.updateRecipient(id, recipientDetails);
        if (updatedRecipient != null) {
            return ResponseEntity.ok(updatedRecipient);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        boolean deleted = notificationRecipientService.deleteRecipient(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
