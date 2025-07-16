package com.bmo.rfb.service;

import com.bmo.rfb.model.TestingResult;
import com.bmo.rfb.repository.TestingResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TestingResultService {
    
    @Autowired
    private TestingResultRepository testingResultRepository;
    
    public List<TestingResult> getAllTestingResults() {
        return testingResultRepository.findAll();
    }
    
    public Optional<TestingResult> getTestingResultById(Long id) {
        return testingResultRepository.findById(id);
    }
    
    public List<TestingResult> getTestingResultsByUen(String uen) {
        return testingResultRepository.findByUen(uen);
    }
    
    public List<TestingResult> getTestingResultsByBorrowerName(String borrowerName) {
        return testingResultRepository.findByBorrowerNameContainingIgnoreCase(borrowerName);
    }
    
    public List<TestingResult> getTestingResultsByApprovalStatus(String approvalStatus) {
        return testingResultRepository.findByApprovalStatus(approvalStatus);
    }
    
    public List<TestingResult> getTestingResultsByStatus(String status) {
        return testingResultRepository.findByStatus(status);
    }
    
    public List<TestingResult> getTestingResultsByUenAndApprovalStatus(String uen, String approvalStatus) {
        return testingResultRepository.findByUenAndApprovalStatus(uen, approvalStatus);
    }
    
    public TestingResult saveTestingResult(TestingResult testingResult) {
        return testingResultRepository.save(testingResult);
    }
    
    public TestingResult updateTestingResult(Long id, TestingResult testingResultDetails) {
        Optional<TestingResult> optionalTestingResult = testingResultRepository.findById(id);
        if (optionalTestingResult.isPresent()) {
            TestingResult testingResult = optionalTestingResult.get();
            testingResult.setUen(testingResultDetails.getUen());
            testingResult.setBorrowerName(testingResultDetails.getBorrowerName());
            testingResult.setStatementDesc(testingResultDetails.getStatementDesc());
            testingResult.setTrackingItem(testingResultDetails.getTrackingItem());
            testingResult.setDueDays(testingResultDetails.getDueDays());
            testingResult.setThreshold(testingResultDetails.getThreshold());
            testingResult.setResult(testingResultDetails.getResult());
            testingResult.setPassFail(testingResultDetails.getPassFail());
            testingResult.setComments(testingResultDetails.getComments());
            testingResult.setStatus(testingResultDetails.getStatus());
            testingResult.setApprovalStatus(testingResultDetails.getApprovalStatus());
            return testingResultRepository.save(testingResult);
        }
        return null;
    }
    
    public boolean deleteTestingResult(Long id) {
        if (testingResultRepository.existsById(id)) {
            testingResultRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
