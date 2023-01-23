package com.shopme.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopme.admin.company.FinancialYearRepository;
import com.shopme.common.entity.FinancialYear;

@Controller
public class MainController {
	
	@Autowired
	private FinancialYearRepository financialYearRepository;
	
	@GetMapping("")
	public String viewHomePage(Model model, HttpSession session) {
		Long financialYearId = (Long) session.getAttribute("financialYearId");
		model.addAttribute("financialYearId", financialYearId);
		return "index";
	}
	
	@GetMapping("/login")
	public String viewLoginPage(Model model) {
		List<FinancialYear> financialYears = (List<FinancialYear>) financialYearRepository.findAll();
	    model.addAttribute("financialYears", financialYears);
	  
		return "login";
	}
	
    @PostMapping("/login_success_handler")
    public String submitLogin(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("financialYearId") Long financialYearId, HttpSession session) {
    	  System.out.println("Logging user login success...");
            session.setAttribute("financialYearId", financialYearId);
            System.out.println("FinancialYearID : " + financialYearId);
            System.out.println("Name : " + email);
            System.out.println("PAss : " + password);
            
            return "index";
      
    }

}