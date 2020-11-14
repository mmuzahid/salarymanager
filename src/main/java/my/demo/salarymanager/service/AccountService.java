package my.demo.salarymanager.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import my.demo.salarymanager.entity.Account;
import my.demo.salarymanager.entity.AccountType;

public interface AccountService {
	public Account getAccountById(Long id);
	public List<Account> getAccounts(Integer page, Integer pageSize, String sortBy);
	public void saveAccount(Account account);
	public void deleteAccountById(Long id);
	public Page<Account> getAccountsPage(Integer page, Integer pageSize, String sortBy);
	public void deleteAllAccounts();
	public List<AccountType> getAccountTypes();
}
