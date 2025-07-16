package com.bmo.rfb.repository;

import com.bmo.rfb.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    List<Assignment> findByUen(String uen);
    
    List<Assignment> findByBorrowerNameContainingIgnoreCase(String borrowerName);
    
    List<Assignment> findByStatus(String status);
    
    List<Assignment> findByPassFail(String passFail);
    
    @Query("SELECT a FROM Assignment a WHERE a.uen = :uen AND a.borrowerName LIKE %:borrowerName%")
    List<Assignment> findByUenAndBorrowerName(@Param("uen") String uen, @Param("borrowerName") String borrowerName);
    
    @Query("SELECT a FROM Assignment a WHERE a.submittedBy = :submittedBy ORDER BY a.lastUpdated DESC")
    List<Assignment> findBySubmittedByOrderByLastUpdatedDesc(@Param("submittedBy") String submittedBy);
}
