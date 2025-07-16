package com.bmo.rfb.service;

import com.bmo.rfb.model.Assignment;
import com.bmo.rfb.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
    
    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }
    
    public List<Assignment> getAssignmentsByUen(String uen) {
        return assignmentRepository.findByUen(uen);
    }
    
    public List<Assignment> getAssignmentsByBorrowerName(String borrowerName) {
        return assignmentRepository.findByBorrowerNameContainingIgnoreCase(borrowerName);
    }
    
    public List<Assignment> getAssignmentsByStatus(String status) {
        return assignmentRepository.findByStatus(status);
    }
    
    public List<Assignment> getAssignmentsBySubmittedBy(String submittedBy) {
        return assignmentRepository.findBySubmittedByOrderByLastUpdatedDesc(submittedBy);
    }
    
    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
    
    public Assignment updateAssignment(Long id, Assignment assignmentDetails) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(id);
        if (optionalAssignment.isPresent()) {
            Assignment assignment = optionalAssignment.get();
            assignment.setUen(assignmentDetails.getUen());
            assignment.setBorrowerName(assignmentDetails.getBorrowerName());
            assignment.setStatementDesc(assignmentDetails.getStatementDesc());
            assignment.setTrackingItem(assignmentDetails.getTrackingItem());
            assignment.setDueDays(assignmentDetails.getDueDays());
            assignment.setThreshold(assignmentDetails.getThreshold());
            assignment.setResult(assignmentDetails.getResult());
            assignment.setPassFail(assignmentDetails.getPassFail());
            assignment.setComments(assignmentDetails.getComments());
            assignment.setSubmittedBy(assignmentDetails.getSubmittedBy());
            assignment.setStatus(assignmentDetails.getStatus());
            return assignmentRepository.save(assignment);
        }
        return null;
    }
    
    public boolean deleteAssignment(Long id) {
        if (assignmentRepository.existsById(id)) {
            assignmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
