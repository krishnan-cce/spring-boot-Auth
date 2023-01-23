package com.shopme.common.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FinancialYear")
public class FinancialYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate start;
    
    private LocalDate end;
    
    private String title;
    
    @ManyToOne
    private Company company;
    
    

	public FinancialYear() {
	}



	public FinancialYear(LocalDate start, LocalDate end,String title, Company company) {
		this.start = start;
		this.end = end;
		this.title = title;
		this.company = company;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public LocalDate getStart() {
		return start;
	}



	public void setStart(LocalDate localDate) {
		this.start = localDate;
	}



	public LocalDate getEnd() {
		return end;
	}



	public void setEnd(LocalDate end) {
		this.end = end;
	}



	public Company getCompany() {
		return company;
	}



	public void setCompany(Company company) {
		this.company = company;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}
    
    
    
}