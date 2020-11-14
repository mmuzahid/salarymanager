package my.demo.salarymanager.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import my.demo.salarymanager.entity.Bank;
import my.demo.salarymanager.entity.Branch;

public interface BankService {
	public Bank getBankById(Long id);
	public List<Bank> getBanks();
	public List<Bank> getBanks(Integer page, Integer pageSize, String sortBy);
	public void saveBank(Bank bank);
	public Page<Bank> getBanksPage(Integer page, Integer pageSize, String sortBy);
	public List<Branch> getBranches();
	public List<Branch> getBranches(Bank bank);
	public Branch getBranchById(Long id);
}
