package com.bmo.rfb.service;

import com.bmo.rfb.model.NotificationRecipient;
import com.bmo.rfb.repository.NotificationRecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationRecipientService {
    
    @Autowired
    private NotificationRecipientRepository notificationRecipientRepository;
    
    public List<NotificationRecipient> getAllRecipients() {
        return notificationRecipientRepository.findAll();
    }
    
    public Optional<NotificationRecipient> getRecipientById(Long id) {
        return notificationRecipientRepository.findById(id);
    }
    
    public List<NotificationRecipient> getActiveRecipients() {
        return notificationRecipientRepository.findActiveRecipientsOrderByName();
    }
    
    public List<NotificationRecipient> searchRecipients(String searchTerm) {
        return notificationRecipientRepository.searchRecipients(searchTerm);
    }
    
    public List<NotificationRecipient> getRecipientsByDepartment(String department) {
        return notificationRecipientRepository.findByDepartment(department);
    }
    
    public List<NotificationRecipient> getRecipientsByStatus(String status) {
        return notificationRecipientRepository.findByStatus(status);
    }
    
    public NotificationRecipient saveRecipient(NotificationRecipient recipient) {
        return notificationRecipientRepository.save(recipient);
    }
    
    public NotificationRecipient updateRecipient(Long id, NotificationRecipient recipientDetails) {
        Optional<NotificationRecipient> optionalRecipient = notificationRecipientRepository.findById(id);
        if (optionalRecipient.isPresent()) {
            NotificationRecipient recipient = optionalRecipient.get();
            recipient.setName(recipientDetails.getName());
            recipient.setEmail(recipientDetails.getEmail());
            recipient.setDepartment(recipientDetails.getDepartment());
            recipient.setRole(recipientDetails.getRole());
            recipient.setPhone(recipientDetails.getPhone());
            recipient.setStatus(recipientDetails.getStatus());
            return notificationRecipientRepository.save(recipient);
        }
        return null;
    }
    
    public boolean deleteRecipient(Long id) {
        if (notificationRecipientRepository.existsById(id)) {
            notificationRecipientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
