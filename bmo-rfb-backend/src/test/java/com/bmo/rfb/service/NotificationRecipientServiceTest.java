package com.bmo.rfb.service;

import com.bmo.rfb.model.NotificationRecipient;
import com.bmo.rfb.repository.NotificationRecipientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NotificationRecipientServiceTest {

    @Mock
    private NotificationRecipientRepository notificationRecipientRepository;

    @InjectMocks
    private NotificationRecipientService notificationRecipientService;

    private NotificationRecipient recipient1;
    private NotificationRecipient recipient2;

    @BeforeEach
    void setUp() {
        recipient1 = new NotificationRecipient();
        recipient1.setId(1L);
        recipient1.setName("John Smith");
        recipient1.setEmail("john.smith@bmo.com");
        recipient1.setDepartment("Risk Management");
        recipient1.setRole("Manager");
        recipient1.setPhone("416-555-0123");
        recipient1.setStatus("Active");

        recipient2 = new NotificationRecipient();
        recipient2.setId(2L);
        recipient2.setName("Jane Doe");
        recipient2.setEmail("jane.doe@bmo.com");
        recipient2.setDepartment("Credit Analysis");
        recipient2.setRole("Analyst");
        recipient2.setPhone("416-555-0124");
        recipient2.setStatus("Active");
    }

    @Test
    void testGetAllRecipients() {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1, recipient2);
        when(notificationRecipientRepository.findAll()).thenReturn(recipients);

        List<NotificationRecipient> result = notificationRecipientService.getAllRecipients();

        assertEquals(2, result.size());
        assertEquals("John Smith", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
        verify(notificationRecipientRepository).findAll();
    }

    @Test
    void testGetRecipientById() {
        when(notificationRecipientRepository.findById(1L)).thenReturn(Optional.of(recipient1));

        Optional<NotificationRecipient> result = notificationRecipientService.getRecipientById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Smith", result.get().getName());
        verify(notificationRecipientRepository).findById(1L);
    }

    @Test
    void testGetRecipientByIdNotFound() {
        when(notificationRecipientRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<NotificationRecipient> result = notificationRecipientService.getRecipientById(999L);

        assertFalse(result.isPresent());
        verify(notificationRecipientRepository).findById(999L);
    }

    @Test
    void testGetActiveRecipients() {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1, recipient2);
        when(notificationRecipientRepository.findActiveRecipientsOrderByName()).thenReturn(recipients);

        List<NotificationRecipient> result = notificationRecipientService.getActiveRecipients();

        assertEquals(2, result.size());
        verify(notificationRecipientRepository).findActiveRecipientsOrderByName();
    }

    @Test
    void testSearchRecipients() {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1);
        when(notificationRecipientRepository.searchRecipients("John")).thenReturn(recipients);

        List<NotificationRecipient> result = notificationRecipientService.searchRecipients("John");

        assertEquals(1, result.size());
        assertEquals("John Smith", result.get(0).getName());
        verify(notificationRecipientRepository).searchRecipients("John");
    }

    @Test
    void testGetRecipientsByDepartment() {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1);
        when(notificationRecipientRepository.findByDepartment("Risk Management")).thenReturn(recipients);

        List<NotificationRecipient> result = notificationRecipientService.getRecipientsByDepartment("Risk Management");

        assertEquals(1, result.size());
        assertEquals("Risk Management", result.get(0).getDepartment());
        verify(notificationRecipientRepository).findByDepartment("Risk Management");
    }

    @Test
    void testGetRecipientsByStatus() {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1, recipient2);
        when(notificationRecipientRepository.findByStatus("Active")).thenReturn(recipients);

        List<NotificationRecipient> result = notificationRecipientService.getRecipientsByStatus("Active");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r -> r.getStatus().equals("Active")));
        verify(notificationRecipientRepository).findByStatus("Active");
    }

    @Test
    void testSaveRecipient() {
        when(notificationRecipientRepository.save(recipient1)).thenReturn(recipient1);

        NotificationRecipient result = notificationRecipientService.saveRecipient(recipient1);

        assertEquals("John Smith", result.getName());
        verify(notificationRecipientRepository).save(recipient1);
    }

    @Test
    void testUpdateRecipient() {
        NotificationRecipient updatedRecipient = new NotificationRecipient();
        updatedRecipient.setName("John Updated");
        updatedRecipient.setEmail("john.updated@bmo.com");
        updatedRecipient.setDepartment("Updated Department");
        updatedRecipient.setRole("Senior Manager");
        updatedRecipient.setPhone("416-555-9999");
        updatedRecipient.setStatus("Active");

        when(notificationRecipientRepository.findById(1L)).thenReturn(Optional.of(recipient1));
        when(notificationRecipientRepository.save(any(NotificationRecipient.class))).thenReturn(updatedRecipient);

        NotificationRecipient result = notificationRecipientService.updateRecipient(1L, updatedRecipient);

        assertNotNull(result);
        assertEquals("John Updated", result.getName());
        verify(notificationRecipientRepository).findById(1L);
        verify(notificationRecipientRepository).save(any(NotificationRecipient.class));
    }

    @Test
    void testUpdateRecipientNotFound() {
        NotificationRecipient updatedRecipient = new NotificationRecipient();
        when(notificationRecipientRepository.findById(999L)).thenReturn(Optional.empty());

        NotificationRecipient result = notificationRecipientService.updateRecipient(999L, updatedRecipient);

        assertNull(result);
        verify(notificationRecipientRepository).findById(999L);
        verify(notificationRecipientRepository, never()).save(any(NotificationRecipient.class));
    }

    @Test
    void testDeleteRecipient() {
        when(notificationRecipientRepository.existsById(1L)).thenReturn(true);

        boolean result = notificationRecipientService.deleteRecipient(1L);

        assertTrue(result);
        verify(notificationRecipientRepository).existsById(1L);
        verify(notificationRecipientRepository).deleteById(1L);
    }

    @Test
    void testDeleteRecipientNotFound() {
        when(notificationRecipientRepository.existsById(999L)).thenReturn(false);

        boolean result = notificationRecipientService.deleteRecipient(999L);

        assertFalse(result);
        verify(notificationRecipientRepository).existsById(999L);
        verify(notificationRecipientRepository, never()).deleteById(999L);
    }
}
