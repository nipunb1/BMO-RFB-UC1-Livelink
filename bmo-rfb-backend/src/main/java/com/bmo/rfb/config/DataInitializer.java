package com.bmo.rfb.config;

import com.bmo.rfb.model.Assignment;
import com.bmo.rfb.model.TestingResult;
import com.bmo.rfb.model.NotificationRecipient;
import com.bmo.rfb.repository.AssignmentRepository;
import com.bmo.rfb.repository.TestingResultRepository;
import com.bmo.rfb.repository.NotificationRecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private TestingResultRepository testingResultRepository;
    
    @Autowired
    private NotificationRecipientRepository notificationRecipientRepository;
    
    @Override
    public void run(String... args) throws Exception {
        initializeAssignments();
        initializeTestingResults();
        initializeNotificationRecipients();
    }
    
    private void initializeAssignments() {
        if (assignmentRepository.count() == 0) {
            assignmentRepository.save(new Assignment(
                "10002/78",
                "Transamerica Funding Ventures",
                "Q4 2024 Financial Covenant Analysis - Comprehensive review of debt service coverage ratio and minimum equity requirements for compliance verification.",
                "BMO Debt Service Coverage Ratio",
                30,
                1.25,
                1.45,
                "Pass",
                "Ratio exceeds minimum threshold. Strong performance maintained throughout the quarter.",
                "John Smith",
                "Active"
            ));
            
            assignmentRepository.save(new Assignment(
                "10002/80",
                "BMO Capital Markets Corp",
                "Q4 2024 Liquidity Assessment - Analysis of current ratio and working capital requirements for operational flexibility.",
                "BMO Current Ratio Analysis",
                30,
                1.5,
                1.8,
                "Pass",
                "Current ratio indicates adequate liquidity position for short-term obligations.",
                "Sarah Johnson",
                "Active"
            ));
            
            assignmentRepository.save(new Assignment(
                "10002/84",
                "BMO Transamerica Funding Ventures",
                "Q4 2024 Asset Quality Review - Evaluation of asset portfolio quality and risk assessment metrics.",
                "BMO Asset Quality Metrics",
                45,
                2.0,
                2.35,
                "Pass",
                "Asset quality metrics demonstrate strong portfolio performance with low risk profile.",
                "Michael Brown",
                "Active"
            ));
        }
    }
    
    private void initializeTestingResults() {
        if (testingResultRepository.count() == 0) {
            testingResultRepository.save(new TestingResult(
                "10002/84",
                "BMO Transamerica Funding Ventures",
                "Q4 2024 Financial Statement Analysis - Comprehensive review of financial position including balance sheet, income statement, and cash flow analysis for covenant compliance verification.",
                "BMO SK Funded Ratio EBITDA - test",
                30,
                1.25,
                1.45,
                "Pass",
                "Ratio exceeds minimum threshold. Strong performance maintained throughout the quarter with consistent EBITDA growth.",
                "Saved",
                "Outstanding"
            ));
            
            testingResultRepository.save(new TestingResult(
                "10002/84",
                "BMO Transamerica Funding Ventures",
                "Q4 2024 Equity Analysis - Detailed assessment of equity position relative to total assets for maintaining required capital adequacy ratios.",
                "BMO Minimum Equity to Assets - test",
                30,
                0.35,
                0.42,
                "Pass",
                "Equity ratio well above minimum requirement. Capital position remains strong with adequate buffer for operational flexibility.",
                "Saved",
                "Outstanding"
            ));
            
            testingResultRepository.save(new TestingResult(
                "10002/84",
                "BMO Transamerica Funding Ventures",
                "Q4 2024 Asset Quality Review - Comprehensive evaluation of asset portfolio quality and debt service coverage capabilities.",
                "BMO PMI Assets/Debt - Testing Limit 1",
                45,
                2.0,
                2.35,
                "Pass",
                "Asset to debt ratio demonstrates strong financial stability. Portfolio quality metrics indicate low risk profile with diversified holdings.",
                "Saved",
                "Outstanding"
            ));
            
            testingResultRepository.save(new TestingResult(
                "10002/84",
                "BMO Transamerica Funding Ventures",
                "Q4 2024 Liquidity Assessment - Analysis of current liquidity position and short-term debt obligations coverage.",
                "BMO Current Ratio - Liquidity Test",
                30,
                1.5,
                1.8,
                "Pass",
                "Current ratio indicates adequate liquidity position. Short-term obligations are well covered by current assets.",
                "Saved",
                "Outstanding"
            ));
        }
    }
    
    private void initializeNotificationRecipients() {
        if (notificationRecipientRepository.count() == 0) {
            notificationRecipientRepository.save(new NotificationRecipient(
                "John Smith",
                "john.smith@bmo.com",
                "Risk Management",
                "Senior Risk Analyst",
                "+1-416-555-0101",
                "Active"
            ));
            
            notificationRecipientRepository.save(new NotificationRecipient(
                "Sarah Johnson",
                "sarah.johnson@bmo.com",
                "Credit Risk",
                "Credit Risk Manager",
                "+1-416-555-0102",
                "Active"
            ));
            
            notificationRecipientRepository.save(new NotificationRecipient(
                "Michael Brown",
                "michael.brown@bmo.com",
                "Compliance",
                "Compliance Officer",
                "+1-416-555-0103",
                "Active"
            ));
            
            notificationRecipientRepository.save(new NotificationRecipient(
                "Emily Davis",
                "emily.davis@bmo.com",
                "Operations",
                "Operations Manager",
                "+1-416-555-0104",
                "Active"
            ));
            
            notificationRecipientRepository.save(new NotificationRecipient(
                "David Wilson",
                "david.wilson@bmo.com",
                "Treasury",
                "Treasury Analyst",
                "+1-416-555-0105",
                "Active"
            ));
            
            notificationRecipientRepository.save(new NotificationRecipient(
                "Lisa Anderson",
                "lisa.anderson@bmo.com",
                "Audit",
                "Internal Auditor",
                "+1-416-555-0106",
                "Active"
            ));
            
            notificationRecipientRepository.save(new NotificationRecipient(
                "Robert Taylor",
                "robert.taylor@bmo.com",
                "Legal",
                "Legal Counsel",
                "+1-416-555-0107",
                "Active"
            ));
            
            notificationRecipientRepository.save(new NotificationRecipient(
                "Jennifer Martinez",
                "jennifer.martinez@bmo.com",
                "Finance",
                "Financial Analyst",
                "+1-416-555-0108",
                "Active"
            ));
        }
    }
}
