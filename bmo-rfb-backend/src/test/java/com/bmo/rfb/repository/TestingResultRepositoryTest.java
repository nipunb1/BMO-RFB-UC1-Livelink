package com.bmo.rfb.repository;

import com.bmo.rfb.model.TestingResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DataJpaTest
class TestingResultRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TestingResultRepository testingResultRepository;

    private TestingResult testingResult1;
    private TestingResult testingResult2;

    @BeforeEach
    void setUp() {
        testingResult1 = new TestingResult();
        testingResult1.setUen("10002/78");
        testingResult1.setBorrowerName("Transamerica Funding Ventures");
        testingResult1.setStatementDesc("Q4 2024 Financial Statement");
        testingResult1.setTrackingItem("BMO SK Funded Ratio EBITDA");
        testingResult1.setDueDays(30);
        testingResult1.setThreshold(1.25);
        testingResult1.setResult(1.45);
        testingResult1.setPassFail("Pass");
        testingResult1.setComments("Meets requirements");
        testingResult1.setStatus("Saved");

        testingResult2 = new TestingResult();
        testingResult2.setUen("10002/80");
        testingResult2.setBorrowerName("Transamerica Funding Ventures");
        testingResult2.setStatementDesc("Q4 2024 Covenant Testing");
        testingResult2.setTrackingItem("BMO Minimum Equity to Assets");
        testingResult2.setDueDays(30);
        testingResult2.setThreshold(0.35);
        testingResult2.setResult(0.42);
        testingResult2.setPassFail("Pass");
        testingResult2.setComments("Above threshold");
        testingResult2.setStatus("Approved");

        entityManager.persistAndFlush(testingResult1);
        entityManager.persistAndFlush(testingResult2);
    }

    @Test
    void testFindByPassFail() {
        List<TestingResult> passResults = testingResultRepository.findByPassFail("Pass");
        
        assertEquals(2, passResults.size());
        assertTrue(passResults.stream().allMatch(r -> r.getPassFail().equals("Pass")));
    }

    @Test
    void testFindByStatus() {
        List<TestingResult> savedResults = testingResultRepository.findByStatus("Saved");
        List<TestingResult> approvedResults = testingResultRepository.findByStatus("Approved");
        
        assertEquals(1, savedResults.size());
        assertEquals(1, approvedResults.size());
        assertEquals("Saved", savedResults.get(0).getStatus());
        assertEquals("Approved", approvedResults.get(0).getStatus());
    }

    @Test
    void testFindAll() {
        List<TestingResult> allResults = testingResultRepository.findAll();
        
        assertEquals(2, allResults.size());
    }

    @Test
    void testSaveAndFindById() {
        TestingResult newResult = new TestingResult();
        newResult.setUen("10004/90");
        newResult.setBorrowerName("Test Company");
        newResult.setStatementDesc("Test Statement");
        newResult.setTrackingItem("Test Item");
        newResult.setDueDays(45);
        newResult.setThreshold(2.0);
        newResult.setResult(2.5);
        newResult.setPassFail("Pass");
        newResult.setComments("Test comments");
        newResult.setStatus("Draft");

        TestingResult saved = testingResultRepository.save(newResult);
        assertNotNull(saved.getId());

        TestingResult found = testingResultRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("10004/90", found.getUen());
        assertEquals("Test Company", found.getBorrowerName());
        assertEquals("Test Statement", found.getStatementDesc());
        assertEquals("Test Item", found.getTrackingItem());
    }
}
