package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void createUserTableTest() {
		
	}
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userKV = new User("kv@online.com", "kv", "Krishnan", "KV");
		userKV.addRole(roleAdmin);
		
		User savedUser = ((UserService) repo).save(userKV);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		User savedUser = ((UserService) repo).save(userRavi);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userKv = repo.findById((long) 1).get();
		System.out.println(userKv);
		assertThat(userKv).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userKv = repo.findById((long)1).get();
		userKv.setEnabled(true);
		userKv.setEmail("krish@gmail.com");
		
		repo.save(userKv);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById((long)2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRole(roleSalesperson);
		
		((UserService) repo).save(userRavi);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById((long)userId);
		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "ravi@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById((long)id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus((long)id, false);
		
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 1;
		repo.updateEnabledStatus((long)id, true);
		
	}	
//	
//	@Test
//	public void testListFirstPage() {
//		int pageNumber = 0;
//		int pageSize = 5;
//		Long company = (long) 2;
//		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		Page<User> page = repo.findAllByCompanyId(company);
//		
//		List<User> listUsers = page.getContent();
//		
//		listUsers.forEach(user -> System.out.println(user));
//		
//		assertThat(listUsers.size()).isEqualTo(pageSize);
//	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "an";
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		Long companyId = ((User) principal).getCompany().getId();

		Long company = (long) 2;
				
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword,company,pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));	
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
//	
//	@Test
//	public void testSearchUsersWithCompany() {
//		String keyword = "sa";
//	
//
//		Long company = (long) 2;
//				
//		int pageNumber = 0;
//		int pageSize = 4;
//		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		Page<User> page = repo.findAllByKeywordAndCompanyId(keyword,company,pageable);
//		
//		List<User> listUsers = page.getContent();
//		
//		listUsers.forEach(user -> System.out.println(user));	
//		
//		assertThat(listUsers.size()).isGreaterThan(0);
//	}

}
