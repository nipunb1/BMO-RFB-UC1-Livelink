package com.bmo.rfb.controller;

import com.bmo.rfb.model.Assignment;
import com.bmo.rfb.service.AssignmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(AssignmentController.class)
class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssignmentService assignmentService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testGetAllAssignments() throws Exception {
        List<Assignment> assignments = Arrays.asList(assignment1, assignment2);
        when(assignmentService.getAllAssignments()).thenReturn(assignments);

        mockMvc.perform(get("/api/assignments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].uen").value("10002/78"))
                .andExpect(jsonPath("$[0].borrowerName").value("Transamerica Funding Ventures"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].uen").value("10002/80"));

        verify(assignmentService).getAllAssignments();
    }

    @Test
    void testGetAssignmentById() throws Exception {
        when(assignmentService.getAssignmentById(1L)).thenReturn(Optional.of(assignment1));

        mockMvc.perform(get("/api/assignments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.uen").value("10002/78"))
                .andExpect(jsonPath("$.borrowerName").value("Transamerica Funding Ventures"));

        verify(assignmentService).getAssignmentById(1L);
    }

    @Test
    void testGetAssignmentByIdNotFound() throws Exception {
        when(assignmentService.getAssignmentById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/assignments/999"))
                .andExpect(status().isNotFound());

        verify(assignmentService).getAssignmentById(999L);
    }

    @Test
    void testGetAssignmentsByUen() throws Exception {
        List<Assignment> assignments = Arrays.asList(assignment1);
        when(assignmentService.getAssignmentsByUen("10002/78")).thenReturn(assignments);

        mockMvc.perform(get("/api/assignments/uen/{uen}", "10002%2F78"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].uen").value("10002/78"));

        verify(assignmentService).getAssignmentsByUen("10002/78");
    }

    @Test
    void testGetAssignmentsByBorrowerName() throws Exception {
        List<Assignment> assignments = Arrays.asList(assignment1, assignment2);
        when(assignmentService.getAssignmentsByBorrowerName("Transamerica")).thenReturn(assignments);

        mockMvc.perform(get("/api/assignments/borrower/Transamerica"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(assignmentService).getAssignmentsByBorrowerName("Transamerica");
    }

    @Test
    void testGetAssignmentsByStatus() throws Exception {
        List<Assignment> assignments = Arrays.asList(assignment1, assignment2);
        when(assignmentService.getAssignmentsByStatus("Active")).thenReturn(assignments);

        mockMvc.perform(get("/api/assignments/status/Active"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(assignmentService).getAssignmentsByStatus("Active");
    }

    @Test
    void testGetAssignmentsBySubmittedBy() throws Exception {
        List<Assignment> assignments = Arrays.asList(assignment1);
        when(assignmentService.getAssignmentsBySubmittedBy("John Smith")).thenReturn(assignments);

        mockMvc.perform(get("/api/assignments/submitted-by/John Smith"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].submittedBy").value("John Smith"));

        verify(assignmentService).getAssignmentsBySubmittedBy("John Smith");
    }

    @Test
    void testCreateAssignment() throws Exception {
        Assignment newAssignment = new Assignment(
            "10003/85",
            "New Borrower",
            "New Statement",
            "New Item",
            45,
            1.5,
            1.8,
            "Pass",
            "New comments",
            "New User",
            "Draft"
        );
        newAssignment.setId(3L);

        when(assignmentService.saveAssignment(any(Assignment.class))).thenReturn(newAssignment);

        mockMvc.perform(post("/api/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAssignment)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.uen").value("10003/85"))
                .andExpect(jsonPath("$.borrowerName").value("New Borrower"));

        verify(assignmentService).saveAssignment(any(Assignment.class));
    }

    @Test
    void testUpdateAssignment() throws Exception {
        Assignment updatedAssignment = new Assignment(
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
        updatedAssignment.setId(1L);

        when(assignmentService.updateAssignment(eq(1L), any(Assignment.class))).thenReturn(updatedAssignment);

        mockMvc.perform(put("/api/assignments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAssignment)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.uen").value("10002/79"))
                .andExpect(jsonPath("$.borrowerName").value("Updated Borrower"));

        verify(assignmentService).updateAssignment(eq(1L), any(Assignment.class));
    }

    @Test
    void testUpdateAssignmentNotFound() throws Exception {
        Assignment updatedAssignment = new Assignment();
        when(assignmentService.updateAssignment(eq(999L), any(Assignment.class))).thenReturn(null);

        mockMvc.perform(put("/api/assignments/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAssignment)))
                .andExpect(status().isNotFound());

        verify(assignmentService).updateAssignment(eq(999L), any(Assignment.class));
    }

    @Test
    void testDeleteAssignment() throws Exception {
        when(assignmentService.deleteAssignment(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/assignments/1"))
                .andExpect(status().isOk());

        verify(assignmentService).deleteAssignment(1L);
    }

    @Test
    void testDeleteAssignmentNotFound() throws Exception {
        when(assignmentService.deleteAssignment(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/assignments/999"))
                .andExpect(status().isNotFound());

        verify(assignmentService).deleteAssignment(999L);
    }

    @Test
    void testCreateAssignmentWithInvalidData() throws Exception {
        Assignment invalidAssignment = new Assignment();

        mockMvc.perform(post("/api/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAssignment)))
                .andExpect(status().isOk());

        verify(assignmentService).saveAssignment(any(Assignment.class));
    }

    @Test
    void testGetAssignmentsByUenWithSpecialCharacters() throws Exception {
        String specialUen = "10002/78-special";
        List<Assignment> assignments = Arrays.asList(assignment1);
        when(assignmentService.getAssignmentsByUen(specialUen)).thenReturn(assignments);

        mockMvc.perform(get("/api/assignments/uen/{uen}", "10002%2F78-special"))
                .andExpect(status().isOk());

        verify(assignmentService).getAssignmentsByUen(specialUen);
    }
}
