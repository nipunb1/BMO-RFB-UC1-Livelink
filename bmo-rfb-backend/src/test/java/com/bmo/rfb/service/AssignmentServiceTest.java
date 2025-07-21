package com.bmo.rfb.service;

import com.bmo.rfb.model.Assignment;
import com.bmo.rfb.repository.AssignmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    private Assignment assignment1;
    private Assignment assignment2;

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
        assignment1.setId(1L);

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
        assignment2.setId(2L);
    }

    @Test
    void testGetAllAssignments() {
        List<Assignment> expectedAssignments = Arrays.asList(assignment1, assignment2);
        when(assignmentRepository.findAll()).thenReturn(expectedAssignments);

        List<Assignment> actualAssignments = assignmentService.getAllAssignments();

        assertEquals(2, actualAssignments.size());
        assertEquals(expectedAssignments, actualAssignments);
        verify(assignmentRepository).findAll();
    }

    @Test
    void testGetAssignmentById() {
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment1));

        Optional<Assignment> result = assignmentService.getAssignmentById(1L);

        assertTrue(result.isPresent());
        assertEquals(assignment1, result.get());
        verify(assignmentRepository).findById(1L);
    }

    @Test
    void testGetAssignmentByIdNotFound() {
        when(assignmentRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Assignment> result = assignmentService.getAssignmentById(999L);

        assertFalse(result.isPresent());
        verify(assignmentRepository).findById(999L);
    }

    @Test
    void testGetAssignmentsByUen() {
        List<Assignment> expectedAssignments = Arrays.asList(assignment1);
        when(assignmentRepository.findByUen("10002/78")).thenReturn(expectedAssignments);

        List<Assignment> actualAssignments = assignmentService.getAssignmentsByUen("10002/78");

        assertEquals(1, actualAssignments.size());
        assertEquals(expectedAssignments, actualAssignments);
        verify(assignmentRepository).findByUen("10002/78");
    }

    @Test
    void testGetAssignmentsByBorrowerName() {
        List<Assignment> expectedAssignments = Arrays.asList(assignment1, assignment2);
        when(assignmentRepository.findByBorrowerNameContainingIgnoreCase("Transamerica"))
            .thenReturn(expectedAssignments);

        List<Assignment> actualAssignments = assignmentService.getAssignmentsByBorrowerName("Transamerica");

        assertEquals(2, actualAssignments.size());
        assertEquals(expectedAssignments, actualAssignments);
        verify(assignmentRepository).findByBorrowerNameContainingIgnoreCase("Transamerica");
    }

    @Test
    void testGetAssignmentsByStatus() {
        List<Assignment> expectedAssignments = Arrays.asList(assignment1, assignment2);
        when(assignmentRepository.findByStatus("Active")).thenReturn(expectedAssignments);

        List<Assignment> actualAssignments = assignmentService.getAssignmentsByStatus("Active");

        assertEquals(2, actualAssignments.size());
        assertEquals(expectedAssignments, actualAssignments);
        verify(assignmentRepository).findByStatus("Active");
    }

    @Test
    void testGetAssignmentsBySubmittedBy() {
        List<Assignment> expectedAssignments = Arrays.asList(assignment1);
        when(assignmentRepository.findBySubmittedByOrderByLastUpdatedDesc("John Smith"))
            .thenReturn(expectedAssignments);

        List<Assignment> actualAssignments = assignmentService.getAssignmentsBySubmittedBy("John Smith");

        assertEquals(1, actualAssignments.size());
        assertEquals(expectedAssignments, actualAssignments);
        verify(assignmentRepository).findBySubmittedByOrderByLastUpdatedDesc("John Smith");
    }

    @Test
    void testSaveAssignment() {
        when(assignmentRepository.save(assignment1)).thenReturn(assignment1);

        Assignment savedAssignment = assignmentService.saveAssignment(assignment1);

        assertEquals(assignment1, savedAssignment);
        verify(assignmentRepository).save(assignment1);
    }

    @Test
    void testUpdateAssignmentSuccess() {
        Assignment updatedDetails = new Assignment(
            "10002/79",
            "Updated Borrower",
            "Updated Statement",
            "Updated Item",
            45,
            1.5,
            1.8,
            "Pass",
            "Updated comments",
            "Updated User",
            "Updated"
        );

        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment1));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment1);

        Assignment result = assignmentService.updateAssignment(1L, updatedDetails);

        assertNotNull(result);
        verify(assignmentRepository).findById(1L);
        verify(assignmentRepository).save(assignment1);
        
        assertEquals("10002/79", assignment1.getUen());
        assertEquals("Updated Borrower", assignment1.getBorrowerName());
        assertEquals("Updated Statement", assignment1.getStatementDesc());
        assertEquals("Updated Item", assignment1.getTrackingItem());
        assertEquals(45, assignment1.getDueDays());
        assertEquals(1.5, assignment1.getThreshold());
        assertEquals(1.8, assignment1.getResult());
        assertEquals("Pass", assignment1.getPassFail());
        assertEquals("Updated comments", assignment1.getComments());
        assertEquals("Updated User", assignment1.getSubmittedBy());
        assertEquals("Updated", assignment1.getStatus());
    }

    @Test
    void testUpdateAssignmentNotFound() {
        Assignment updatedDetails = new Assignment();
        when(assignmentRepository.findById(999L)).thenReturn(Optional.empty());

        Assignment result = assignmentService.updateAssignment(999L, updatedDetails);

        assertNull(result);
        verify(assignmentRepository).findById(999L);
        verify(assignmentRepository, never()).save(any());
    }

    @Test
    void testDeleteAssignmentSuccess() {
        when(assignmentRepository.existsById(1L)).thenReturn(true);

        boolean result = assignmentService.deleteAssignment(1L);

        assertTrue(result);
        verify(assignmentRepository).existsById(1L);
        verify(assignmentRepository).deleteById(1L);
    }

    @Test
    void testDeleteAssignmentNotFound() {
        when(assignmentRepository.existsById(999L)).thenReturn(false);

        boolean result = assignmentService.deleteAssignment(999L);

        assertFalse(result);
        verify(assignmentRepository).existsById(999L);
        verify(assignmentRepository, never()).deleteById(any());
    }

    @Test
    void testGetAssignmentsByUenEmptyResult() {
        when(assignmentRepository.findByUen("nonexistent")).thenReturn(Arrays.asList());

        List<Assignment> result = assignmentService.getAssignmentsByUen("nonexistent");

        assertTrue(result.isEmpty());
        verify(assignmentRepository).findByUen("nonexistent");
    }

    @Test
    void testGetAssignmentsByBorrowerNameEmptyResult() {
        when(assignmentRepository.findByBorrowerNameContainingIgnoreCase("nonexistent"))
            .thenReturn(Arrays.asList());

        List<Assignment> result = assignmentService.getAssignmentsByBorrowerName("nonexistent");

        assertTrue(result.isEmpty());
        verify(assignmentRepository).findByBorrowerNameContainingIgnoreCase("nonexistent");
    }
}
