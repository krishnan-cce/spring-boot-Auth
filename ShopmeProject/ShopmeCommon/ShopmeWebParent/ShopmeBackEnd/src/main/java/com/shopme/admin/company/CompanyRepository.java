package com.shopme.admin.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long>{
	

}
