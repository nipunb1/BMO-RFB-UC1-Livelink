package com.bmo.rfb.repository;

import com.bmo.rfb.model.NotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {
    
    List<NotificationRecipient> findByStatus(String status);
    
    List<NotificationRecipient> findByDepartment(String department);
    
    List<NotificationRecipient> findByNameContainingIgnoreCase(String name);
    
    List<NotificationRecipient> findByEmailContainingIgnoreCase(String email);
    
    @Query("SELECT n FROM NotificationRecipient n WHERE n.name LIKE %:searchTerm% OR n.email LIKE %:searchTerm% OR n.department LIKE %:searchTerm%")
    List<NotificationRecipient> searchRecipients(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT n FROM NotificationRecipient n WHERE n.status = 'Active' ORDER BY n.name ASC")
    List<NotificationRecipient> findActiveRecipientsOrderByName();
}
