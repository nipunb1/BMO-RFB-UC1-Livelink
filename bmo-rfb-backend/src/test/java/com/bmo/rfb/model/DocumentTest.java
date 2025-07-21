package com.bmo.rfb.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class DocumentTest {

    private Document document;

    @BeforeEach
    void setUp() {
        document = new Document();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(document);
        assertNull(document.getId());
        assertNull(document.getUen());
        assertNull(document.getBorrowerName());
        assertNull(document.getFileName());
        assertNull(document.getFilePath());
        assertNull(document.getFileSize());
        assertNull(document.getFileType());
        assertNull(document.getYearFolder());
        assertNull(document.getUploadStatus());
        assertNull(document.getUploadedBy());
        assertNull(document.getCreatedDate());
        assertNull(document.getLastUpdated());
    }

    @Test
    void testParameterizedConstructor() {
        String uen = "123456789A";
        String borrowerName = "Test Company Ltd";
        String fileName = "test-document.pdf";
        String filePath = "/uploads/2024/test-document.pdf";
        Long fileSize = 1024L;
        String fileType = "application/pdf";
        String yearFolder = "2024";
        String uploadStatus = "COMPLETED";
        String uploadedBy = "john.doe@bmo.com";

        Document doc = new Document(uen, borrowerName, fileName, filePath, fileSize, fileType, yearFolder, uploadStatus, uploadedBy);

        assertEquals(uen, doc.getUen());
        assertEquals(borrowerName, doc.getBorrowerName());
        assertEquals(fileName, doc.getFileName());
        assertEquals(filePath, doc.getFilePath());
        assertEquals(fileSize, doc.getFileSize());
        assertEquals(fileType, doc.getFileType());
        assertEquals(yearFolder, doc.getYearFolder());
        assertEquals(uploadStatus, doc.getUploadStatus());
        assertEquals(uploadedBy, doc.getUploadedBy());
        assertNotNull(doc.getCreatedDate());
        assertNotNull(doc.getLastUpdated());
    }

    @Test
    void testSettersAndGetters() {
        Long id = 1L;
        String uen = "987654321B";
        String borrowerName = "Another Company Inc";
        String fileName = "financial-statement.xlsx";
        String filePath = "/uploads/2024/financial-statement.xlsx";
        String fileType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        Long fileSize = 2048L;
        String yearFolder = "2024";
        String uploadStatus = "PENDING";
        String uploadedBy = "jane.smith@bmo.com";
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastUpdated = LocalDateTime.now();

        document.setId(id);
        document.setUen(uen);
        document.setBorrowerName(borrowerName);
        document.setFileName(fileName);
        document.setFilePath(filePath);
        document.setFileType(fileType);
        document.setFileSize(fileSize);
        document.setYearFolder(yearFolder);
        document.setUploadStatus(uploadStatus);
        document.setUploadedBy(uploadedBy);
        document.setCreatedDate(createdDate);
        document.setLastUpdated(lastUpdated);

        assertEquals(id, document.getId());
        assertEquals(uen, document.getUen());
        assertEquals(borrowerName, document.getBorrowerName());
        assertEquals(fileName, document.getFileName());
        assertEquals(filePath, document.getFilePath());
        assertEquals(fileType, document.getFileType());
        assertEquals(fileSize, document.getFileSize());
        assertEquals(yearFolder, document.getYearFolder());
        assertEquals(uploadStatus, document.getUploadStatus());
        assertEquals(uploadedBy, document.getUploadedBy());
        assertEquals(createdDate, document.getCreatedDate());
        assertEquals(lastUpdated, document.getLastUpdated());
    }

    @Test
    void testPreUpdate() {
        document.setUen("123456789A");
        document.setBorrowerName("Test Company");
        document.setFileName("test.pdf");
        document.setFilePath("/uploads/test.pdf");
        document.setFileType("application/pdf");
        document.setFileSize(1024L);
        document.setYearFolder("2024");
        document.setUploadStatus("COMPLETED");
        document.setUploadedBy("test@bmo.com");
        document.setCreatedDate(LocalDateTime.now().minusDays(1));

        LocalDateTime beforePreUpdate = LocalDateTime.now();
        document.preUpdate();
        LocalDateTime afterPreUpdate = LocalDateTime.now();

        assertNotNull(document.getLastUpdated());
        assertTrue(document.getLastUpdated().isAfter(beforePreUpdate.minusSeconds(1)));
        assertTrue(document.getLastUpdated().isBefore(afterPreUpdate.plusSeconds(1)));
    }

    @Test
    void testValidationConstraints() {
        document.setUen("");
        document.setBorrowerName("");
        document.setFileName("");

        assertNotNull(document.getUen());
        assertNotNull(document.getBorrowerName());
        assertNotNull(document.getFileName());
    }
}
