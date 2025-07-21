package com.bmo.rfb.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class TestingResultTest {

    private TestingResult testingResult;

    @BeforeEach
    void setUp() {
        testingResult = new TestingResult();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(testingResult);
        assertNull(testingResult.getId());
        assertNull(testingResult.getStatementDesc());
        assertNull(testingResult.getTrackingItem());
    }

    @Test
    void testGettersAndSetters() {
        testingResult.setId(1L);
        testingResult.setStatementDesc("Q4 2024 Financial Statement");
        testingResult.setTrackingItem("BMO SK Funded Ratio EBITDA");
        testingResult.setDueDays(30);
        testingResult.setThreshold(1.25);
        testingResult.setResult(1.45);
        testingResult.setPassFail("Pass");
        testingResult.setComments("Test comments");
        testingResult.setStatus("Saved");
        
        LocalDateTime now = LocalDateTime.now();
        testingResult.setCreatedDate(now);
        testingResult.setLastUpdated(now);

        assertEquals(1L, testingResult.getId());
        assertEquals("Q4 2024 Financial Statement", testingResult.getStatementDesc());
        assertEquals("BMO SK Funded Ratio EBITDA", testingResult.getTrackingItem());
        assertEquals(30, testingResult.getDueDays());
        assertEquals(1.25, testingResult.getThreshold());
        assertEquals(1.45, testingResult.getResult());
        assertEquals("Pass", testingResult.getPassFail());
        assertEquals("Test comments", testingResult.getComments());
        assertEquals("Saved", testingResult.getStatus());
        assertEquals(now, testingResult.getCreatedDate());
        assertEquals(now, testingResult.getLastUpdated());
    }

    @Test
    void testNullValues() {
        testingResult.setStatementDesc(null);
        testingResult.setTrackingItem(null);
        testingResult.setDueDays(null);
        testingResult.setThreshold(null);
        testingResult.setResult(null);

        assertNull(testingResult.getStatementDesc());
        assertNull(testingResult.getTrackingItem());
        assertNull(testingResult.getDueDays());
        assertNull(testingResult.getThreshold());
        assertNull(testingResult.getResult());
    }

    @Test
    void testBoundaryValues() {
        testingResult.setDueDays(0);
        testingResult.setThreshold(0.0);
        testingResult.setResult(0.0);

        assertEquals(0, testingResult.getDueDays());
        assertEquals(0.0, testingResult.getThreshold());
        assertEquals(0.0, testingResult.getResult());

        testingResult.setDueDays(Integer.MAX_VALUE);
        testingResult.setThreshold(Double.MAX_VALUE);
        testingResult.setResult(Double.MAX_VALUE);

        assertEquals(Integer.MAX_VALUE, testingResult.getDueDays());
        assertEquals(Double.MAX_VALUE, testingResult.getThreshold());
        assertEquals(Double.MAX_VALUE, testingResult.getResult());
    }

    @Test
    void testPreUpdate() {
        testingResult.setStatementDesc("Test Statement");
        testingResult.setTrackingItem("Test Item");
        testingResult.setCreatedDate(LocalDateTime.now().minusDays(1));

        LocalDateTime beforePreUpdate = LocalDateTime.now();
        testingResult.preUpdate();
        LocalDateTime afterPreUpdate = LocalDateTime.now();

        assertNotNull(testingResult.getLastUpdated());
        assertTrue(testingResult.getLastUpdated().isAfter(beforePreUpdate.minusSeconds(1)));
        assertTrue(testingResult.getLastUpdated().isBefore(afterPreUpdate.plusSeconds(1)));
    }
}
