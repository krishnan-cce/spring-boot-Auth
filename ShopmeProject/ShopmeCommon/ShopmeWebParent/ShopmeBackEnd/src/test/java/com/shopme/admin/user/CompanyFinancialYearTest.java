package com.shopme.admin.user;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import com.shopme.admin.company.CompanyRepository;
import com.shopme.admin.company.FinancialYearRepository;
import com.shopme.common.entity.Company;
import com.shopme.common.entity.FinancialYear;


@SpringBootTest
@AutoConfigureMockMvc
public class CompanyFinancialYearTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private FinancialYearRepository financialYearRepository;

    @Test
    public void testCreateCompanyAndFinancialYear() throws Exception {
        Company company = new Company();
        company.setName("KraftLabs Inc.");
        company.setAddress("Thrissur - Kerala");
        company.setActive(true);
        companyRepository.save(company);

        FinancialYear financialYear = new FinancialYear();
        financialYear.setCompany(company);
        financialYear.setStart(LocalDate.of(2022, 4, 1));
        financialYear.setEnd(LocalDate.of(2023, 3, 31));
        financialYearRepository.save(financialYear);

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("KraftLabs Inc.")))
                .andExpect(content().string(containsString("2022-04-01 - 2023-03-31")));
    }
}
