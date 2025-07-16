package com.bmo.rfb.repository;

import com.bmo.rfb.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    List<Document> findByUen(String uen);
    
    List<Document> findByBorrowerNameContainingIgnoreCase(String borrowerName);
    
    List<Document> findByYearFolder(String yearFolder);
    
    List<Document> findByUploadStatus(String uploadStatus);
    
    @Query("SELECT d FROM Document d WHERE d.uen = :uen AND d.yearFolder = :yearFolder")
    List<Document> findByUenAndYearFolder(@Param("uen") String uen, @Param("yearFolder") String yearFolder);
    
    @Query("SELECT DISTINCT d.yearFolder FROM Document d WHERE d.uen = :uen ORDER BY d.yearFolder DESC")
    List<String> findDistinctYearFoldersByUen(@Param("uen") String uen);
    
    @Query("SELECT d FROM Document d WHERE d.borrowerName LIKE %:borrowerName% ORDER BY d.lastUpdated DESC")
    List<Document> findByBorrowerNameOrderByLastUpdated(@Param("borrowerName") String borrowerName);
}
