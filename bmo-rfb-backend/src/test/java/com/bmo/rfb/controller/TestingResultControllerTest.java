package com.bmo.rfb.controller;

import com.bmo.rfb.model.TestingResult;
import com.bmo.rfb.service.TestingResultService;
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

@WebMvcTest(TestingResultController.class)
class TestingResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestingResultService testingResultService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testGetAllTestingResults() throws Exception {
        List<TestingResult> results = Arrays.asList(testingResult1, testingResult2);
        when(testingResultService.getAllTestingResults()).thenReturn(results);

        mockMvc.perform(get("/api/testing-results"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].statementDesc").value("Q4 2024 Financial Statement"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].statementDesc").value("Q4 2024 Covenant Testing"));

        verify(testingResultService).getAllTestingResults();
    }

    @Test
    void testGetTestingResultById() throws Exception {
        when(testingResultService.getTestingResultById(1L)).thenReturn(Optional.of(testingResult1));

        mockMvc.perform(get("/api/testing-results/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.statementDesc").value("Q4 2024 Financial Statement"));

        verify(testingResultService).getTestingResultById(1L);
    }

    @Test
    void testGetTestingResultByIdNotFound() throws Exception {
        when(testingResultService.getTestingResultById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/testing-results/999"))
                .andExpect(status().isNotFound());

        verify(testingResultService).getTestingResultById(999L);
    }

    @Test
    void testCreateTestingResult() throws Exception {
        TestingResult newResult = new TestingResult();
        newResult.setId(3L);
        newResult.setStatementDesc("New Statement");
        newResult.setTrackingItem("New Item");
        newResult.setPassFail("Pass");
        newResult.setStatus("Draft");

        when(testingResultService.saveTestingResult(any(TestingResult.class))).thenReturn(newResult);

        mockMvc.perform(post("/api/testing-results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newResult)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.statementDesc").value("New Statement"));

        verify(testingResultService).saveTestingResult(any(TestingResult.class));
    }

    @Test
    void testDeleteTestingResult() throws Exception {
        when(testingResultService.deleteTestingResult(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/testing-results/1"))
                .andExpect(status().isOk());

        verify(testingResultService).deleteTestingResult(1L);
    }

    @Test
    void testDeleteTestingResultNotFound() throws Exception {
        when(testingResultService.deleteTestingResult(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/testing-results/999"))
                .andExpect(status().isNotFound());

        verify(testingResultService).deleteTestingResult(999L);
    }
}
