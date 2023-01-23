package com.shopme.admin.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

@Controller
public class UserController {
	
	@Autowired 
	private UserService service;
	
	@GetMapping("/users")
	public String listFirstPage(Model model,HttpSession session) {
		
		Long financialYearId = (Long) session.getAttribute("financialYearId");
		model.addAttribute("financialYearId", financialYearId);
		System.out.println("financialYearId : " + financialYearId);
		
		return listByPage(1,model,"firstName", "asc",null);
	}
	
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum,Model model,
			@Param("sortField") String sortField,@Param("sortDir") String sortDir,
			@Param("keyword") String keyword
			) {
		System.out.println("Sort Field " + sortField);
		System.out.println("Sort Order " + sortDir);
		
		Page<User> page = service.listByPage(pageNum, sortField, sortDir,keyword);
		
		List<User> listUsers = page.getContent();
		
	
		System.out.println("Page Number = " + pageNum);
		System.out.println("Total Elements = " + page.getTotalElements());
		System.out.println("Page pages = " + page.getTotalPages());
		
		long startCount = (pageNum -1 ) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc" ;
		
		model.addAttribute("keyword",keyword);
		model.addAttribute("reverseSortDir",reverseSortDir);
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("currentPage",pageNum);
		model.addAttribute("startCount",startCount);
		model.addAttribute("endCount",endCount);
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("listUsers",listUsers);
		
		return "users";
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();
		
		User user = new User();
		user.setEnabled(true);
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		
		return "users_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		System.out.println(user);
		System.out.println(multipartFile.getOriginalFilename());
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = service.save(user);
			
			String uploadDir = "user-photos/" + savedUser.getId();
			
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
			
			//AmazonS3Util.removeFolder(uploadDir);
			//AmazonS3Util.uploadFile(uploadDir, fileName, multipartFile.getInputStream());	
			
		} else {
			if (user.getPhotos().isEmpty()) user.setPhotos(null);
			service.save(user);
		}

		redirectAttributes.addFlashAttribute("message","The user has been saved successfully.");
		
		return getRedirectedUrlForUpdatedUser(user);
		
	}

	private String getRedirectedUrlForUpdatedUser(User user) {
		String firstPartOfEmail = user.getEmail().split("@")[0];
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" +firstPartOfEmail ;
	}
	
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Long id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			User user = service.get(id);
			List<Role> listRoles = service.listRoles();
			
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
			model.addAttribute("listRoles", listRoles);
			
			return "users_form";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Long id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
		
			redirectAttributes.addFlashAttribute("message", 
					"The user ID " + id + " has been deleted successfully");
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id")Long id, 
			@PathVariable("status")boolean enabled, RedirectAttributes redirectAttributes) {
		
		service.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled" ;
		String message = "The user ID " + id + " has been " + status ;
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/users";
				
	}
	
}
