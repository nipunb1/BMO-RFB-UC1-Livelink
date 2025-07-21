package com.bmo.rfb;

import com.bmo.rfb.model.Assignment;
import com.bmo.rfb.repository.AssignmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class RfbApplicationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        assignmentRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertNotNull(webApplicationContext);
        assertNotNull(assignmentRepository);
    }

    @Test
    void testCreateAndRetrieveAssignment() throws Exception {
        Assignment assignment = new Assignment(
            "10002/78",
            "Integration Test Borrower",
            "Integration Test Statement",
            "Integration Test Item",
            30,
            1.25,
            1.45,
            "Pass",
            "Integration test comments",
            "Integration Test User",
            "Active"
        );

        String response = mockMvc.perform(post("/api/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uen").value("10002/78"))
                .andExpect(jsonPath("$.borrowerName").value("Integration Test Borrower"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assignment createdAssignment = objectMapper.readValue(response, Assignment.class);
        assertNotNull(createdAssignment.getId());

        mockMvc.perform(get("/api/assignments/" + createdAssignment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdAssignment.getId()))
                .andExpect(jsonPath("$.uen").value("10002/78"))
                .andExpect(jsonPath("$.borrowerName").value("Integration Test Borrower"));
    }

    @Test
    void testUpdateAssignment() throws Exception {
        Assignment assignment = new Assignment(
            "10002/78",
            "Original Borrower",
            "Original Statement",
            "Original Item",
            30,
            1.25,
            1.45,
            "Pass",
            "Original comments",
            "Original User",
            "Active"
        );

        Assignment saved = assignmentRepository.save(assignment);

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

        mockMvc.perform(put("/api/assignments/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAssignment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uen").value("10002/79"))
                .andExpect(jsonPath("$.borrowerName").value("Updated Borrower"));

        Assignment retrieved = assignmentRepository.findById(saved.getId()).orElse(null);
        assertNotNull(retrieved);
        assertEquals("10002/79", retrieved.getUen());
        assertEquals("Updated Borrower", retrieved.getBorrowerName());
    }

    @Test
    void testDeleteAssignment() throws Exception {
        Assignment assignment = new Assignment(
            "10002/78",
            "Delete Test Borrower",
            "Delete Test Statement",
            "Delete Test Item",
            30,
            1.25,
            1.45,
            "Pass",
            "Delete test comments",
            "Delete Test User",
            "Active"
        );

        Assignment saved = assignmentRepository.save(assignment);
        assertTrue(assignmentRepository.existsById(saved.getId()));

        mockMvc.perform(delete("/api/assignments/" + saved.getId()))
                .andExpect(status().isOk());

        assertFalse(assignmentRepository.existsById(saved.getId()));
    }

    @Test
    void testSearchByUen() throws Exception {
        Assignment assignment1 = new Assignment(
            "10002/78",
            "Search Test Borrower 1",
            "Search Test Statement 1",
            "Search Test Item 1",
            30,
            1.25,
            1.45,
            "Pass",
            "Search test comments 1",
            "Search Test User 1",
            "Active"
        );

        Assignment assignment2 = new Assignment(
            "10002/80",
            "Search Test Borrower 2",
            "Search Test Statement 2",
            "Search Test Item 2",
            30,
            0.35,
            0.42,
            "Pass",
            "Search test comments 2",
            "Search Test User 2",
            "Active"
        );

        assignmentRepository.save(assignment1);
        assignmentRepository.save(assignment2);

        mockMvc.perform(get("/api/assignments/uen/{uen}", "10002%2F78"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].uen").value("10002/78"))
                .andExpect(jsonPath("$[0].borrowerName").value("Search Test Borrower 1"));
    }

    @Test
    void testSearchByBorrowerName() throws Exception {
        Assignment assignment1 = new Assignment(
            "10002/78",
            "Transamerica Test Company",
            "Search Test Statement 1",
            "Search Test Item 1",
            30,
            1.25,
            1.45,
            "Pass",
            "Search test comments 1",
            "Search Test User 1",
            "Active"
        );

        Assignment assignment2 = new Assignment(
            "10002/80",
            "Other Test Company",
            "Search Test Statement 2",
            "Search Test Item 2",
            30,
            0.35,
            0.42,
            "Pass",
            "Search test comments 2",
            "Search Test User 2",
            "Active"
        );

        assignmentRepository.save(assignment1);
        assignmentRepository.save(assignment2);

        mockMvc.perform(get("/api/assignments/borrower/Transamerica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].borrowerName").value("Transamerica Test Company"));
    }

    @Test
    void testGetAllAssignments() throws Exception {
        Assignment assignment1 = new Assignment(
            "10002/78",
            "All Test Borrower 1",
            "All Test Statement 1",
            "All Test Item 1",
            30,
            1.25,
            1.45,
            "Pass",
            "All test comments 1",
            "All Test User 1",
            "Active"
        );

        Assignment assignment2 = new Assignment(
            "10002/80",
            "All Test Borrower 2",
            "All Test Statement 2",
            "All Test Item 2",
            30,
            0.35,
            0.42,
            "Pass",
            "All test comments 2",
            "All Test User 2",
            "Active"
        );

        assignmentRepository.save(assignment1);
        assignmentRepository.save(assignment2);

        mockMvc.perform(get("/api/assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
