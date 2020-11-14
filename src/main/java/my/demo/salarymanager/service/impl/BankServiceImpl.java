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

import my.demo.salarymanager.entity.Bank;
import my.demo.salarymanager.entity.Branch;
import my.demo.salarymanager.exception.BankException;
import my.demo.salarymanager.repository.BankRepository;
import my.demo.salarymanager.repository.BranchRepository;
import my.demo.salarymanager.service.BankService;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankRepository bankRepository;
	
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Override
	@Cacheable(value = "banks", key="#id")
	public Bank getBankById(Long id) {
		Optional<Bank> bank = bankRepository.findById(id);
        if(bank.isPresent()) {
            return bank.get();
        } else {
            throw new BankException("No bank record exist for given id");
        }
	}

	@Override
	public List<Bank> getBanks() {		
		return bankRepository.findAll();
	}
	
	@Override
	public List<Bank> getBanks(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<Bank> bankPage = bankRepository.findAll(pageable);
		return bankPage.getContent();
	}
	
	@Override
	public Page<Bank> getBanksPage(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<Bank> bankPage = bankRepository.findAll(pageable);
		return bankPage;
	}

	@Override
	@CacheEvict(value = "banks", key = "#bank.id")
	public void saveBank(Bank bank) {
		branchRepository.findByBank(bank);
		bankRepository.save(bank);
	}

	
	@Override
	public List<Branch> getBranches() {
		return branchRepository.findAll();
	}

	
	@Override
	public List<Branch> getBranches(Bank bank) {
		return branchRepository.findByBank(bank);
	}

	
	@Override
	public Branch getBranchById(Long id) {
		
		Optional<Branch> branch = branchRepository.findById(id);
        if(branch.isPresent()) {
            return branch.get();
        } else {
            throw new BankException("No branch record exist for given id");
        }	
	}
	

}
