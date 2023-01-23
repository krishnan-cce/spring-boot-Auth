package com.shopme.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopme.admin.company.FinancialYearRepository;
import com.shopme.admin.user.UserRepository;
import com.shopme.common.entity.Company;
import com.shopme.common.entity.FinancialYear;
import com.shopme.common.entity.User;

public class ShopmeUserDetailsService implements UserDetailsService{
	
	@Autowired
	private FinancialYearRepository financialYearRepository;
	
	@Autowired
	private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DisabledException {
    	
        User user = userRepo.getUserByEmail(email);
        Long financialYearId = getFinancialYearIdByUser(user);
        
        if(user == null) {
            throw new UsernameNotFoundException("Could not find user with email: " + email);
        }
        if(!user.getCompany().getActive()){
            throw new DisabledException("User is not allowed to login, Company Expired");
        }
        return new ShopmeUserDetails(user,financialYearId);
    }

    public Long getFinancialYearIdByUser(User user) {
        Company company = user.getCompany();
        FinancialYear financialYear = financialYearRepository.findByCompanyId(company.getId());
        return financialYear.getId();
    }

}
