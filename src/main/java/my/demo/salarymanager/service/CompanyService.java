package my.demo.salarymanager.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import my.demo.salarymanager.entity.Account;
import my.demo.salarymanager.entity.AccountType;
import my.demo.salarymanager.entity.Company;

public interface CompanyService {
	public Company getCompany();

	public void save(Company company);

}
