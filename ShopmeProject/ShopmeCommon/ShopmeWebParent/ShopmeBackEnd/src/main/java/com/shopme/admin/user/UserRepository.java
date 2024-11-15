package com.shopme.admin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.User;


@Repository
public interface UserRepository  extends PagingAndSortingRepository<User, Long>{


	Long companyId = (long) 2;
	
	User findByEmailAndPassword(String email, String password);
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	public Long countById(Long id);
	
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Long id, boolean enabled);

	@Query("SELECT u FROM User u WHERE (:keyword is null or :keyword = '' or CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %:keyword%) AND u.company.id = :companyId")
	public Page<User> findAll(@Param("keyword") String keyword,@Param("companyId") Long companyId, Pageable pageable);



}
