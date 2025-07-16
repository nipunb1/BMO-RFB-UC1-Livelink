package com.bmo.rfb.controller;

import com.bmo.rfb.model.Assignment;
import com.bmo.rfb.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {
    
    @Autowired
    private AssignmentService assignmentService;
    
    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentService.getAssignmentById(id);
        return assignment.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/uen/{uen}")
    public ResponseEntity<List<Assignment>> getAssignmentsByUen(@PathVariable String uen) {
        List<Assignment> assignments = assignmentService.getAssignmentsByUen(uen);
        return ResponseEntity.ok(assignments);
    }
    
    @GetMapping("/borrower/{borrowerName}")
    public ResponseEntity<List<Assignment>> getAssignmentsByBorrowerName(@PathVariable String borrowerName) {
        List<Assignment> assignments = assignmentService.getAssignmentsByBorrowerName(borrowerName);
        return ResponseEntity.ok(assignments);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Assignment>> getAssignmentsByStatus(@PathVariable String status) {
        List<Assignment> assignments = assignmentService.getAssignmentsByStatus(status);
        return ResponseEntity.ok(assignments);
    }
    
    @GetMapping("/submitted-by/{submittedBy}")
    public ResponseEntity<List<Assignment>> getAssignmentsBySubmittedBy(@PathVariable String submittedBy) {
        List<Assignment> assignments = assignmentService.getAssignmentsBySubmittedBy(submittedBy);
        return ResponseEntity.ok(assignments);
    }
    
    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        Assignment savedAssignment = assignmentService.saveAssignment(assignment);
        return ResponseEntity.ok(savedAssignment);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment assignmentDetails) {
        Assignment updatedAssignment = assignmentService.updateAssignment(id, assignmentDetails);
        if (updatedAssignment != null) {
            return ResponseEntity.ok(updatedAssignment);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        boolean deleted = assignmentService.deleteAssignment(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
