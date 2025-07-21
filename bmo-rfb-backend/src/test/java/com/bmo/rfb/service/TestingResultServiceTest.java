package com.bmo.rfb.service;

import com.bmo.rfb.model.TestingResult;
import com.bmo.rfb.repository.TestingResultRepository;
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
class TestingResultServiceTest {

    @Mock
    private TestingResultRepository testingResultRepository;

    @InjectMocks
    private TestingResultService testingResultService;

    private TestingResult testingResult1;
    private TestingResult testingResult2;

    @BeforeEach
    void setUp() {
        testingResult1 = new TestingResult();
        testingResult1.setId(1L);
        testingResult1.setStatementDesc("Q4 2024 Financial Statement");
        testingResult1.setTrackingItem("BMO SK Funded Ratio EBITDA");
        testingResult1.setPassFail("Pass");
        testingResult1.setStatus("Saved");

        testingResult2 = new TestingResult();
        testingResult2.setId(2L);
        testingResult2.setStatementDesc("Q4 2024 Covenant Testing");
        testingResult2.setTrackingItem("BMO Minimum Equity to Assets");
        testingResult2.setPassFail("Pass");
        testingResult2.setStatus("Approved");
    }

    @Test
    void testGetAllTestingResults() {
        List<TestingResult> expectedResults = Arrays.asList(testingResult1, testingResult2);
        when(testingResultRepository.findAll()).thenReturn(expectedResults);

        List<TestingResult> actualResults = testingResultService.getAllTestingResults();

        assertEquals(2, actualResults.size());
        assertEquals(expectedResults, actualResults);
        verify(testingResultRepository).findAll();
    }

    @Test
    void testGetTestingResultById() {
        when(testingResultRepository.findById(1L)).thenReturn(Optional.of(testingResult1));

        Optional<TestingResult> result = testingResultService.getTestingResultById(1L);

        assertTrue(result.isPresent());
        assertEquals(testingResult1, result.get());
        verify(testingResultRepository).findById(1L);
    }

    @Test
    void testGetTestingResultByIdNotFound() {
        when(testingResultRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<TestingResult> result = testingResultService.getTestingResultById(999L);

        assertFalse(result.isPresent());
        verify(testingResultRepository).findById(999L);
    }

    @Test
    void testGetTestingResultsByStatus() {
        List<TestingResult> expectedResults = Arrays.asList(testingResult1);
        when(testingResultRepository.findByStatus("Saved")).thenReturn(expectedResults);

        List<TestingResult> actualResults = testingResultService.getTestingResultsByStatus("Saved");

        assertEquals(1, actualResults.size());
        assertEquals(expectedResults, actualResults);
        verify(testingResultRepository).findByStatus("Saved");
    }

    @Test
    void testSaveTestingResult() {
        when(testingResultRepository.save(testingResult1)).thenReturn(testingResult1);

        TestingResult savedResult = testingResultService.saveTestingResult(testingResult1);

        assertEquals(testingResult1, savedResult);
        verify(testingResultRepository).save(testingResult1);
    }

    @Test
    void testDeleteTestingResult() {
        when(testingResultRepository.existsById(1L)).thenReturn(true);

        boolean result = testingResultService.deleteTestingResult(1L);

        assertTrue(result);
        verify(testingResultRepository).existsById(1L);
        verify(testingResultRepository).deleteById(1L);
    }

    @Test
    void testDeleteTestingResultNotFound() {
        when(testingResultRepository.existsById(999L)).thenReturn(false);

        boolean result = testingResultService.deleteTestingResult(999L);

        assertFalse(result);
        verify(testingResultRepository).existsById(999L);
        verify(testingResultRepository, never()).deleteById(any());
    }

    @Test
    void testGetTestingResultsByUen() {
        List<TestingResult> expectedResults = Arrays.asList(testingResult1);
        when(testingResultRepository.findByUen("123456789A")).thenReturn(expectedResults);

        List<TestingResult> actualResults = testingResultService.getTestingResultsByUen("123456789A");

        assertEquals(1, actualResults.size());
        assertEquals(expectedResults, actualResults);
        verify(testingResultRepository).findByUen("123456789A");
    }

    @Test
    void testGetTestingResultsByBorrowerName() {
        List<TestingResult> expectedResults = Arrays.asList(testingResult1);
        when(testingResultRepository.findByBorrowerNameContainingIgnoreCase("Test Company")).thenReturn(expectedResults);

        List<TestingResult> actualResults = testingResultService.getTestingResultsByBorrowerName("Test Company");

        assertEquals(1, actualResults.size());
        assertEquals(expectedResults, actualResults);
        verify(testingResultRepository).findByBorrowerNameContainingIgnoreCase("Test Company");
    }

    @Test
    void testGetTestingResultsByApprovalStatus() {
        List<TestingResult> expectedResults = Arrays.asList(testingResult2);
        when(testingResultRepository.findByApprovalStatus("Approved")).thenReturn(expectedResults);

        List<TestingResult> actualResults = testingResultService.getTestingResultsByApprovalStatus("Approved");

        assertEquals(1, actualResults.size());
        assertEquals(expectedResults, actualResults);
        verify(testingResultRepository).findByApprovalStatus("Approved");
    }

    @Test
    void testGetTestingResultsByUenAndApprovalStatus() {
        List<TestingResult> expectedResults = Arrays.asList(testingResult1);
        when(testingResultRepository.findByUenAndApprovalStatus("123456789A", "Pending")).thenReturn(expectedResults);

        List<TestingResult> actualResults = testingResultService.getTestingResultsByUenAndApprovalStatus("123456789A", "Pending");

        assertEquals(1, actualResults.size());
        assertEquals(expectedResults, actualResults);
        verify(testingResultRepository).findByUenAndApprovalStatus("123456789A", "Pending");
    }

    @Test
    void testUpdateTestingResult() {
        TestingResult updatedDetails = new TestingResult();
        updatedDetails.setUen("987654321B");
        updatedDetails.setBorrowerName("Updated Company");
        updatedDetails.setStatementDesc("Updated Statement");
        updatedDetails.setTrackingItem("Updated Item");
        updatedDetails.setDueDays(45);
        updatedDetails.setThreshold(2.0);
        updatedDetails.setResult(2.5);
        updatedDetails.setPassFail("Pass");
        updatedDetails.setComments("Updated comments");
        updatedDetails.setStatus("Updated");
        updatedDetails.setApprovalStatus("Approved");

        when(testingResultRepository.findById(1L)).thenReturn(Optional.of(testingResult1));
        when(testingResultRepository.save(any(TestingResult.class))).thenReturn(testingResult1);

        TestingResult result = testingResultService.updateTestingResult(1L, updatedDetails);

        assertNotNull(result);
        verify(testingResultRepository).findById(1L);
        verify(testingResultRepository).save(any(TestingResult.class));
    }

    @Test
    void testUpdateTestingResultNotFound() {
        TestingResult updatedDetails = new TestingResult();
        when(testingResultRepository.findById(999L)).thenReturn(Optional.empty());

        TestingResult result = testingResultService.updateTestingResult(999L, updatedDetails);

        assertNull(result);
        verify(testingResultRepository).findById(999L);
        verify(testingResultRepository, never()).save(any(TestingResult.class));
    }
}
