package com.event.cia103g1springboot.member.emp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeRepository extends JpaRepository<EmployeeVO, Integer> {
    
    EmployeeVO findByEmpAcct(String empAcct);
    
    @Modifying
    @Transactional
    @Query("UPDATE EmployeeVO e SET e.empPwd = ?2 WHERE e.empAcct = ?1")
    int updatePassword(String empAcct, String newPassword);
    
    @Modifying
    @Transactional
    @Query("UPDATE EmployeeVO e SET e.empStat = ?2 WHERE e.empId = ?1")
    int updateStatus(Integer empId, Integer empStat);
} 