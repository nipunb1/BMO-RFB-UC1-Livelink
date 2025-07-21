package com.bmo.rfb.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class AssignmentTest {

    private Assignment assignment;

    @BeforeEach
    void setUp() {
        assignment = new Assignment();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(assignment);
        assertNull(assignment.getId());
        assertNull(assignment.getUen());
        assertNull(assignment.getBorrowerName());
    }

    @Test
    void testParameterizedConstructor() {
        Assignment assignment = new Assignment(
            "10002/78",
            "Test Borrower",
            "Q4 2024 Financial Statement",
            "BMO SK Funded Ratio EBITDA",
            30,
            1.25,
            1.45,
            "Pass",
            "Meets requirements",
            "John Smith",
            "Active"
        );

        assertEquals("10002/78", assignment.getUen());
        assertEquals("Test Borrower", assignment.getBorrowerName());
        assertEquals("Q4 2024 Financial Statement", assignment.getStatementDesc());
        assertEquals("BMO SK Funded Ratio EBITDA", assignment.getTrackingItem());
        assertEquals(30, assignment.getDueDays());
        assertEquals(1.25, assignment.getThreshold());
        assertEquals(1.45, assignment.getResult());
        assertEquals("Pass", assignment.getPassFail());
        assertEquals("Meets requirements", assignment.getComments());
        assertEquals("John Smith", assignment.getSubmittedBy());
        assertEquals("Active", assignment.getStatus());
        assertNotNull(assignment.getCreatedDate());
        assertNotNull(assignment.getLastUpdated());
    }

    @Test
    void testGettersAndSetters() {
        assignment.setId(1L);
        assignment.setUen("10002/78");
        assignment.setBorrowerName("Test Borrower");
        assignment.setStatementDesc("Test Statement");
        assignment.setTrackingItem("Test Item");
        assignment.setDueDays(30);
        assignment.setThreshold(1.25);
        assignment.setResult(1.45);
        assignment.setPassFail("Pass");
        assignment.setComments("Test comments");
        assignment.setSubmittedBy("Test User");
        assignment.setStatus("Active");
        
        LocalDateTime now = LocalDateTime.now();
        assignment.setCreatedDate(now);
        assignment.setLastUpdated(now);

        assertEquals(1L, assignment.getId());
        assertEquals("10002/78", assignment.getUen());
        assertEquals("Test Borrower", assignment.getBorrowerName());
        assertEquals("Test Statement", assignment.getStatementDesc());
        assertEquals("Test Item", assignment.getTrackingItem());
        assertEquals(30, assignment.getDueDays());
        assertEquals(1.25, assignment.getThreshold());
        assertEquals(1.45, assignment.getResult());
        assertEquals("Pass", assignment.getPassFail());
        assertEquals("Test comments", assignment.getComments());
        assertEquals("Test User", assignment.getSubmittedBy());
        assertEquals("Active", assignment.getStatus());
        assertEquals(now, assignment.getCreatedDate());
        assertEquals(now, assignment.getLastUpdated());
    }

    @Test
    void testPreUpdate() {
        LocalDateTime originalTime = LocalDateTime.now().minusHours(1);
        assignment.setLastUpdated(originalTime);
        
        assignment.preUpdate();
        
        assertTrue(assignment.getLastUpdated().isAfter(originalTime));
    }

    @Test
    void testNullValues() {
        assignment.setUen(null);
        assignment.setBorrowerName(null);
        assignment.setDueDays(null);
        assignment.setThreshold(null);
        assignment.setResult(null);

        assertNull(assignment.getUen());
        assertNull(assignment.getBorrowerName());
        assertNull(assignment.getDueDays());
        assertNull(assignment.getThreshold());
        assertNull(assignment.getResult());
    }

    @Test
    void testBoundaryValues() {
        assignment.setDueDays(0);
        assignment.setThreshold(0.0);
        assignment.setResult(0.0);

        assertEquals(0, assignment.getDueDays());
        assertEquals(0.0, assignment.getThreshold());
        assertEquals(0.0, assignment.getResult());

        assignment.setDueDays(Integer.MAX_VALUE);
        assignment.setThreshold(Double.MAX_VALUE);
        assignment.setResult(Double.MAX_VALUE);

        assertEquals(Integer.MAX_VALUE, assignment.getDueDays());
        assertEquals(Double.MAX_VALUE, assignment.getThreshold());
        assertEquals(Double.MAX_VALUE, assignment.getResult());
    }

    @Test
    void testStringFields() {
        String longText = "A".repeat(1000);
        assignment.setStatementDesc(longText);
        assignment.setComments(longText);

        assertEquals(longText, assignment.getStatementDesc());
        assertEquals(longText, assignment.getComments());
    }
}
