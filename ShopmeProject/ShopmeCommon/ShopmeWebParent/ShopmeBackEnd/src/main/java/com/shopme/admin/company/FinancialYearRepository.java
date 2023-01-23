package com.shopme.admin.company;

import org.springframework.stereotype.Repository;
import com.shopme.common.entity.FinancialYear;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface FinancialYearRepository extends JpaRepository<FinancialYear, Long>{
	
	 FinancialYear findByCompanyId(Long companyId);
	 
	 FinancialYear findByCompanyIdAndId(Long companyId, Long financialYearId);
	 
	 FinancialYear findByCompanyIdAndTitle(Long companyId, String title);
}
