package com.bmo.rfb.repository;

import com.bmo.rfb.model.TestingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestingResultRepository extends JpaRepository<TestingResult, Long> {
    
    List<TestingResult> findByUen(String uen);
    
    List<TestingResult> findByBorrowerNameContainingIgnoreCase(String borrowerName);
    
    List<TestingResult> findByApprovalStatus(String approvalStatus);
    
    List<TestingResult> findByStatus(String status);
    
    List<TestingResult> findByPassFail(String passFail);
    
    @Query("SELECT t FROM TestingResult t WHERE t.uen = :uen AND t.approvalStatus = :approvalStatus")
    List<TestingResult> findByUenAndApprovalStatus(@Param("uen") String uen, @Param("approvalStatus") String approvalStatus);
    
    @Query("SELECT t FROM TestingResult t WHERE t.borrowerName LIKE %:borrowerName% ORDER BY t.lastUpdated DESC")
    List<TestingResult> findByBorrowerNameOrderByLastUpdated(@Param("borrowerName") String borrowerName);
}
