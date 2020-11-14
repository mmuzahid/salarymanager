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
import my.demo.salarymanager.exception.AccountException;
import my.demo.salarymanager.repository.AccountRepository;
import my.demo.salarymanager.repository.AccountTypeRepository;
import my.demo.salarymanager.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountTypeRepository accountTypeRepository;
	
	@Override
	@Cacheable(value = "accounts", key="#id")
	public Account getAccountById(Long id) {
		Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()) {
            return account.get();
        } else {
            throw new AccountException("No account record exist for given id");
        }
	}

	@Override
	public List<Account> getAccounts(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<Account> accountPage = accountRepository.findAll(pageable);
		return accountPage.getContent();
	}
	
	@Override
	public Page<Account> getAccountsPage(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<Account> accountPage = accountRepository.findAll(pageable);
		return accountPage;
	}

	@Override
	@CacheEvict(value = "accounts", key = "#account.id")
	public void saveAccount(Account account) {
		accountRepository.save(account);
	}

	@Override
	@CacheEvict(value = "accounts", key = "#id")
	public void deleteAccountById(Long id) {
		try {
			accountRepository.deleteById(id);
		} catch(EmptyResultDataAccessException ex) {
			throw new AccountException("No account record exist to delete");
		}
	}
	
	@Override
	public void deleteAllAccounts() {
		accountRepository.deleteAll();
	}

	@Override
	public List<AccountType> getAccountTypes() {
		return accountTypeRepository.findAll();
	}
	


}
