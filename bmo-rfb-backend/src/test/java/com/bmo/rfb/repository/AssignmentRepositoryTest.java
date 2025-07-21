package com.bmo.rfb.repository;

import com.bmo.rfb.model.Assignment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DataJpaTest
class AssignmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AssignmentRepository assignmentRepository;

    private Assignment assignment1;
    private Assignment assignment2;
    private Assignment assignment3;

    @BeforeEach
    void setUp() {
        assignment1 = new Assignment(
            "10002/78",
            "Transamerica Funding Ventures",
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

        assignment2 = new Assignment(
            "10002/80",
            "Transamerica Funding Ventures",
            "Q4 2024 Covenant Testing",
            "BMO Minimum Equity to Assets",
            30,
            0.35,
            0.42,
            "Pass",
            "Above threshold",
            "Jane Doe",
            "Active"
        );

        assignment3 = new Assignment(
            "10003/85",
            "MKTG47 GRUMMAN GM",
            "Q1 2025 Financial Review",
            "BMO Current Ratio",
            45,
            1.5,
            1.2,
            "Fail",
            "Below threshold",
            "John Smith",
            "Pending"
        );

        entityManager.persistAndFlush(assignment1);
        entityManager.persistAndFlush(assignment2);
        entityManager.persistAndFlush(assignment3);
    }

    @Test
    void testFindByUen() {
        List<Assignment> results = assignmentRepository.findByUen("10002/78");
        
        assertEquals(1, results.size());
        assertEquals("10002/78", results.get(0).getUen());
        assertEquals("Transamerica Funding Ventures", results.get(0).getBorrowerName());
    }

    @Test
    void testFindByUenNotFound() {
        List<Assignment> results = assignmentRepository.findByUen("99999/99");
        
        assertTrue(results.isEmpty());
    }

    @Test
    void testFindByBorrowerNameContainingIgnoreCase() {
        List<Assignment> results = assignmentRepository.findByBorrowerNameContainingIgnoreCase("transamerica");
        
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(a -> a.getBorrowerName().contains("Transamerica")));
    }

    @Test
    void testFindByBorrowerNameContainingIgnoreCasePartial() {
        List<Assignment> results = assignmentRepository.findByBorrowerNameContainingIgnoreCase("GRUMMAN");
        
        assertEquals(1, results.size());
        assertEquals("MKTG47 GRUMMAN GM", results.get(0).getBorrowerName());
    }

    @Test
    void testFindByStatus() {
        List<Assignment> activeResults = assignmentRepository.findByStatus("Active");
        List<Assignment> pendingResults = assignmentRepository.findByStatus("Pending");
        
        assertEquals(2, activeResults.size());
        assertEquals(1, pendingResults.size());
        assertEquals("Pending", pendingResults.get(0).getStatus());
    }

    @Test
    void testFindByPassFail() {
        List<Assignment> passResults = assignmentRepository.findByPassFail("Pass");
        List<Assignment> failResults = assignmentRepository.findByPassFail("Fail");
        
        assertEquals(2, passResults.size());
        assertEquals(1, failResults.size());
        assertEquals("Fail", failResults.get(0).getPassFail());
    }

    @Test
    void testFindByUenAndBorrowerName() {
        List<Assignment> results = assignmentRepository.findByUenAndBorrowerName("10002/78", "Transamerica");
        
        assertEquals(1, results.size());
        assertEquals("10002/78", results.get(0).getUen());
        assertTrue(results.get(0).getBorrowerName().contains("Transamerica"));
    }

    @Test
    void testFindByUenAndBorrowerNameNoMatch() {
        List<Assignment> results = assignmentRepository.findByUenAndBorrowerName("10002/78", "NonExistent");
        
        assertTrue(results.isEmpty());
    }

    @Test
    void testFindBySubmittedByOrderByLastUpdatedDesc() {
        List<Assignment> results = assignmentRepository.findBySubmittedByOrderByLastUpdatedDesc("John Smith");
        
        assertEquals(2, results.size());
        assertEquals("John Smith", results.get(0).getSubmittedBy());
        assertEquals("John Smith", results.get(1).getSubmittedBy());
        assertTrue(results.get(0).getLastUpdated().isAfter(results.get(1).getLastUpdated()) ||
                  results.get(0).getLastUpdated().equals(results.get(1).getLastUpdated()));
    }

    @Test
    void testFindAll() {
        List<Assignment> allAssignments = assignmentRepository.findAll();
        
        assertEquals(3, allAssignments.size());
    }

    @Test
    void testSaveAndFindById() {
        Assignment newAssignment = new Assignment(
            "10004/90",
            "Test Company",
            "Test Statement",
            "Test Item",
            60,
            2.0,
            2.5,
            "Pass",
            "Test comments",
            "Test User",
            "Draft"
        );

        Assignment saved = assignmentRepository.save(newAssignment);
        assertNotNull(saved.getId());

        Assignment found = assignmentRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("10004/90", found.getUen());
        assertEquals("Test Company", found.getBorrowerName());
    }

    @Test
    void testDeleteById() {
        Long assignmentId = assignment1.getId();
        assertTrue(assignmentRepository.existsById(assignmentId));

        assignmentRepository.deleteById(assignmentId);
        assertFalse(assignmentRepository.existsById(assignmentId));
    }
}
