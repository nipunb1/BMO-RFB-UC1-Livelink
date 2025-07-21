package com.bmo.rfb.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class NotificationRecipientTest {

    private NotificationRecipient recipient;

    @BeforeEach
    void setUp() {
        recipient = new NotificationRecipient();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(recipient);
        assertNull(recipient.getId());
        assertNull(recipient.getName());
        assertNull(recipient.getEmail());
        assertNull(recipient.getDepartment());
        assertNull(recipient.getRole());
        assertNull(recipient.getPhone());
        assertNull(recipient.getStatus());
        assertNull(recipient.getCreatedDate());
        assertNull(recipient.getLastUpdated());
    }

    @Test
    void testParameterizedConstructor() {
        String name = "John Smith";
        String email = "john.smith@bmo.com";
        String department = "Risk Management";
        String role = "Manager";
        String phone = "416-555-0123";
        String status = "Active";

        NotificationRecipient nr = new NotificationRecipient(name, email, department, role, phone, status);

        assertEquals(name, nr.getName());
        assertEquals(email, nr.getEmail());
        assertEquals(department, nr.getDepartment());
        assertEquals(role, nr.getRole());
        assertEquals(phone, nr.getPhone());
        assertEquals(status, nr.getStatus());
        assertNotNull(nr.getCreatedDate());
        assertNotNull(nr.getLastUpdated());
    }

    @Test
    void testSettersAndGetters() {
        Long id = 1L;
        String name = "Jane Doe";
        String email = "jane.doe@bmo.com";
        String department = "Credit Analysis";
        String role = "Analyst";
        String phone = "416-555-0124";
        String status = "Active";
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastUpdated = LocalDateTime.now();

        recipient.setId(id);
        recipient.setName(name);
        recipient.setEmail(email);
        recipient.setDepartment(department);
        recipient.setRole(role);
        recipient.setPhone(phone);
        recipient.setStatus(status);
        recipient.setCreatedDate(createdDate);
        recipient.setLastUpdated(lastUpdated);

        assertEquals(id, recipient.getId());
        assertEquals(name, recipient.getName());
        assertEquals(email, recipient.getEmail());
        assertEquals(department, recipient.getDepartment());
        assertEquals(role, recipient.getRole());
        assertEquals(phone, recipient.getPhone());
        assertEquals(status, recipient.getStatus());
        assertEquals(createdDate, recipient.getCreatedDate());
        assertEquals(lastUpdated, recipient.getLastUpdated());
    }

    @Test
    void testConstructorSetsTimestamps() {
        String name = "Test User";
        String email = "test@bmo.com";
        String department = "Test Department";
        String role = "Tester";
        String phone = "416-555-0000";
        String status = "Active";

        LocalDateTime beforeCreation = LocalDateTime.now();
        NotificationRecipient nr = new NotificationRecipient(name, email, department, role, phone, status);
        LocalDateTime afterCreation = LocalDateTime.now();

        assertNotNull(nr.getCreatedDate());
        assertNotNull(nr.getLastUpdated());
        assertTrue(nr.getCreatedDate().isAfter(beforeCreation.minusSeconds(1)));
        assertTrue(nr.getCreatedDate().isBefore(afterCreation.plusSeconds(1)));
        assertTrue(nr.getLastUpdated().isAfter(beforeCreation.minusSeconds(1)));
        assertTrue(nr.getLastUpdated().isBefore(afterCreation.plusSeconds(1)));
    }

    @Test
    void testPreUpdate() {
        recipient.setName("Test User");
        recipient.setEmail("test@bmo.com");
        recipient.setDepartment("Test Department");
        recipient.setRole("Tester");
        recipient.setPhone("416-555-0000");
        recipient.setStatus("Active");
        recipient.setCreatedDate(LocalDateTime.now().minusDays(1));

        LocalDateTime beforePreUpdate = LocalDateTime.now();
        recipient.preUpdate();
        LocalDateTime afterPreUpdate = LocalDateTime.now();

        assertNotNull(recipient.getLastUpdated());
        assertTrue(recipient.getLastUpdated().isAfter(beforePreUpdate.minusSeconds(1)));
        assertTrue(recipient.getLastUpdated().isBefore(afterPreUpdate.plusSeconds(1)));
    }

    @Test
    void testEqualsAndHashCode() {
        NotificationRecipient nr1 = new NotificationRecipient();
        nr1.setId(1L);
        nr1.setName("John Smith");
        nr1.setEmail("john.smith@bmo.com");

        NotificationRecipient nr2 = new NotificationRecipient();
        nr2.setId(1L);
        nr2.setName("John Smith");
        nr2.setEmail("john.smith@bmo.com");

        NotificationRecipient nr3 = new NotificationRecipient();
        nr3.setId(2L);
        nr3.setName("Jane Doe");
        nr3.setEmail("jane.doe@bmo.com");

        assertNotEquals(nr1, nr2);
        assertNotEquals(nr1, nr3);
        assertNotEquals(nr1.hashCode(), nr2.hashCode());
        assertNotEquals(nr1.hashCode(), nr3.hashCode());
        
        assertEquals(nr1, nr1);
        assertEquals(nr1.hashCode(), nr1.hashCode());
    }

    @Test
    void testToString() {
        recipient.setId(1L);
        recipient.setName("John Smith");
        recipient.setEmail("john.smith@bmo.com");
        recipient.setDepartment("Risk Management");

        String toString = recipient.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("NotificationRecipient"));
        assertTrue(toString.contains("@"));
    }

    @Test
    void testValidationConstraints() {
        recipient.setName("");
        recipient.setEmail("invalid-email");
        recipient.setDepartment("");
        recipient.setRole("");
        recipient.setPhone("");
        recipient.setStatus("");

        assertNotNull(recipient.getName());
        assertNotNull(recipient.getEmail());
        assertNotNull(recipient.getDepartment());
        assertNotNull(recipient.getRole());
        assertNotNull(recipient.getPhone());
        assertNotNull(recipient.getStatus());
    }
}
