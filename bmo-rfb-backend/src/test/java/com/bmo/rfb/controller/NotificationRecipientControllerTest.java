package com.bmo.rfb.controller;

import com.bmo.rfb.model.NotificationRecipient;
import com.bmo.rfb.service.NotificationRecipientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(NotificationRecipientController.class)
class NotificationRecipientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationRecipientService notificationRecipientService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testGetAllRecipients() throws Exception {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1, recipient2);
        when(notificationRecipientService.getAllRecipients()).thenReturn(recipients);

        mockMvc.perform(get("/api/notification-recipients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Smith"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));

        verify(notificationRecipientService).getAllRecipients();
    }

    @Test
    void testGetRecipientById() throws Exception {
        when(notificationRecipientService.getRecipientById(1L)).thenReturn(Optional.of(recipient1));

        mockMvc.perform(get("/api/notification-recipients/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.email").value("john.smith@bmo.com"));

        verify(notificationRecipientService).getRecipientById(1L);
    }

    @Test
    void testGetRecipientByIdNotFound() throws Exception {
        when(notificationRecipientService.getRecipientById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/notification-recipients/999"))
                .andExpect(status().isNotFound());

        verify(notificationRecipientService).getRecipientById(999L);
    }

    @Test
    void testSearchRecipients() throws Exception {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1);
        when(notificationRecipientService.searchRecipients("John")).thenReturn(recipients);

        mockMvc.perform(get("/api/notification-recipients/search/John"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Smith"));

        verify(notificationRecipientService).searchRecipients("John");
    }

    @Test
    void testGetRecipientsByDepartment() throws Exception {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1);
        when(notificationRecipientService.getRecipientsByDepartment("Risk Management")).thenReturn(recipients);

        mockMvc.perform(get("/api/notification-recipients/department/Risk Management"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].department").value("Risk Management"));

        verify(notificationRecipientService).getRecipientsByDepartment("Risk Management");
    }

    @Test
    void testGetRecipientsByStatus() throws Exception {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1, recipient2);
        when(notificationRecipientService.getRecipientsByStatus("Active")).thenReturn(recipients);

        mockMvc.perform(get("/api/notification-recipients/status/Active"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(notificationRecipientService).getRecipientsByStatus("Active");
    }

    @Test
    void testGetActiveRecipients() throws Exception {
        List<NotificationRecipient> recipients = Arrays.asList(recipient1, recipient2);
        when(notificationRecipientService.getActiveRecipients()).thenReturn(recipients);

        mockMvc.perform(get("/api/notification-recipients/active"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(notificationRecipientService).getActiveRecipients();
    }

    @Test
    void testCreateRecipient() throws Exception {
        NotificationRecipient newRecipient = new NotificationRecipient();
        newRecipient.setName("New User");
        newRecipient.setEmail("new.user@bmo.com");
        newRecipient.setDepartment("New Department");
        newRecipient.setRole("Director");
        newRecipient.setPhone("416-555-0125");
        newRecipient.setStatus("Active");
        newRecipient.setId(3L);

        when(notificationRecipientService.saveRecipient(any(NotificationRecipient.class))).thenReturn(newRecipient);

        mockMvc.perform(post("/api/notification-recipients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRecipient)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("New User"));

        verify(notificationRecipientService).saveRecipient(any(NotificationRecipient.class));
    }

    @Test
    void testUpdateRecipient() throws Exception {
        NotificationRecipient updatedRecipient = new NotificationRecipient();
        updatedRecipient.setId(1L);
        updatedRecipient.setName("Updated Name");
        updatedRecipient.setEmail("updated@bmo.com");
        updatedRecipient.setDepartment("Updated Department");
        updatedRecipient.setRole("Senior Manager");
        updatedRecipient.setPhone("416-555-9999");
        updatedRecipient.setStatus("Active");

        when(notificationRecipientService.updateRecipient(eq(1L), any(NotificationRecipient.class))).thenReturn(updatedRecipient);

        mockMvc.perform(put("/api/notification-recipients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRecipient)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Name"));

        verify(notificationRecipientService).updateRecipient(eq(1L), any(NotificationRecipient.class));
    }

    @Test
    void testUpdateRecipientNotFound() throws Exception {
        NotificationRecipient updatedRecipient = new NotificationRecipient();
        when(notificationRecipientService.updateRecipient(eq(999L), any(NotificationRecipient.class))).thenReturn(null);

        mockMvc.perform(put("/api/notification-recipients/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRecipient)))
                .andExpect(status().isNotFound());

        verify(notificationRecipientService).updateRecipient(eq(999L), any(NotificationRecipient.class));
    }

    @Test
    void testDeleteRecipient() throws Exception {
        when(notificationRecipientService.deleteRecipient(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/notification-recipients/1"))
                .andExpect(status().isOk());

        verify(notificationRecipientService).deleteRecipient(1L);
    }

    @Test
    void testDeleteRecipientNotFound() throws Exception {
        when(notificationRecipientService.deleteRecipient(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/notification-recipients/999"))
                .andExpect(status().isNotFound());

        verify(notificationRecipientService).deleteRecipient(999L);
    }
}
