package my.demo.salarymanager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import my.demo.salarymanager.entity.Account;
import my.demo.salarymanager.entity.AccountType;
import my.demo.salarymanager.entity.Company;
import my.demo.salarymanager.exception.AccountException;
import my.demo.salarymanager.repository.AccountRepository;
import my.demo.salarymanager.repository.AccountTypeRepository;
import my.demo.salarymanager.repository.CompanyRepository;
import my.demo.salarymanager.service.AccountService;
import my.demo.salarymanager.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private AccountTypeRepository accountTypeRepository;
	
	@Override
	public Company getCompany() {
		return companyRepository.findAll().get(0);
	}

	@Override
	public void save(Company company) {
		companyRepository.save(company);
		
	}


}
