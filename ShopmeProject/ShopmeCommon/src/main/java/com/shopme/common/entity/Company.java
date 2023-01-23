package com.shopme.common.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String address;
    
	@Column(name="IsActive", columnDefinition="boolean default true", nullable=false)
	private Boolean active;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<FinancialYear> financialYears;

    
    
	public Company() {
	}

	public Company(String name, String address, Boolean active, List<FinancialYear> financialYears) {
		this.name = name;
		this.address = address;
		this.active = active;
		this.financialYears = financialYears;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<FinancialYear> getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(List<FinancialYear> financialYears) {
		this.financialYears = financialYears;
	}
    
    
}