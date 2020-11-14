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

import my.demo.salarymanager.entity.AccountTransaction;
import my.demo.salarymanager.exception.AccountTransactionException;
import my.demo.salarymanager.repository.AccountTransactionRepository;
import my.demo.salarymanager.service.AccountTransactionService;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

	@Autowired
	private AccountTransactionRepository accountTransactionRepository;
	
	@Override
	@Cacheable(value = "accountTransactions", key="#id")
	public AccountTransaction getAccountTransactionById(Long id) {
		Optional<AccountTransaction> accountTransaction = accountTransactionRepository.findById(id);
        if(accountTransaction.isPresent()) {
            return accountTransaction.get();
        } else {
            throw new AccountTransactionException("No accountTransaction record exist for given id");
        }
	}

	@Override
	public List<AccountTransaction> getAccountTransactions(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<AccountTransaction> accountTransactionPage = accountTransactionRepository.findAll(pageable);
		return accountTransactionPage.getContent();
	}
	
	@Override
	public Page<AccountTransaction> getAccountTransactionsPage(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<AccountTransaction> accountTransactionPage = accountTransactionRepository.findAll(pageable);
		return accountTransactionPage;
	}

	@Override
	@CacheEvict(value = "accountTransactions", key = "#accountTransaction.id")
	public void saveAccountTransaction(AccountTransaction accountTransaction) {
		accountTransactionRepository.save(accountTransaction);
	}



}
