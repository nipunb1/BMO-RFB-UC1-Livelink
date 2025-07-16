package com.bmo.rfb.controller;

import com.bmo.rfb.model.TestingResult;
import com.bmo.rfb.service.TestingResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/testing-results")
@CrossOrigin(origins = "*")
public class TestingResultController {
    
    @Autowired
    private TestingResultService testingResultService;
    
    @GetMapping
    public ResponseEntity<List<TestingResult>> getAllTestingResults() {
        List<TestingResult> testingResults = testingResultService.getAllTestingResults();
        return ResponseEntity.ok(testingResults);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TestingResult> getTestingResultById(@PathVariable Long id) {
        Optional<TestingResult> testingResult = testingResultService.getTestingResultById(id);
        return testingResult.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/uen/{uen}")
    public ResponseEntity<List<TestingResult>> getTestingResultsByUen(@PathVariable String uen) {
        List<TestingResult> testingResults = testingResultService.getTestingResultsByUen(uen);
        return ResponseEntity.ok(testingResults);
    }
    
    @GetMapping("/borrower/{borrowerName}")
    public ResponseEntity<List<TestingResult>> getTestingResultsByBorrowerName(@PathVariable String borrowerName) {
        List<TestingResult> testingResults = testingResultService.getTestingResultsByBorrowerName(borrowerName);
        return ResponseEntity.ok(testingResults);
    }
    
    @GetMapping("/approval-status/{approvalStatus}")
    public ResponseEntity<List<TestingResult>> getTestingResultsByApprovalStatus(@PathVariable String approvalStatus) {
        List<TestingResult> testingResults = testingResultService.getTestingResultsByApprovalStatus(approvalStatus);
        return ResponseEntity.ok(testingResults);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TestingResult>> getTestingResultsByStatus(@PathVariable String status) {
        List<TestingResult> testingResults = testingResultService.getTestingResultsByStatus(status);
        return ResponseEntity.ok(testingResults);
    }
    
    @GetMapping("/uen/{uen}/approval-status/{approvalStatus}")
    public ResponseEntity<List<TestingResult>> getTestingResultsByUenAndApprovalStatus(
            @PathVariable String uen, @PathVariable String approvalStatus) {
        List<TestingResult> testingResults = testingResultService.getTestingResultsByUenAndApprovalStatus(uen, approvalStatus);
        return ResponseEntity.ok(testingResults);
    }
    
    @PostMapping
    public ResponseEntity<TestingResult> createTestingResult(@RequestBody TestingResult testingResult) {
        TestingResult savedTestingResult = testingResultService.saveTestingResult(testingResult);
        return ResponseEntity.ok(savedTestingResult);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TestingResult> updateTestingResult(@PathVariable Long id, @RequestBody TestingResult testingResultDetails) {
        TestingResult updatedTestingResult = testingResultService.updateTestingResult(id, testingResultDetails);
        if (updatedTestingResult != null) {
            return ResponseEntity.ok(updatedTestingResult);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestingResult(@PathVariable Long id) {
        boolean deleted = testingResultService.deleteTestingResult(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
