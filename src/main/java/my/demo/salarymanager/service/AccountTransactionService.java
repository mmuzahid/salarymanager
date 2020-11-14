package my.demo.salarymanager.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import my.demo.salarymanager.entity.AccountTransaction;

public interface AccountTransactionService {
	public AccountTransaction getAccountTransactionById(Long id);
	public List<AccountTransaction> getAccountTransactions(Integer page, Integer pageSize, String sortBy);
	public void saveAccountTransaction(AccountTransaction employee);
	public Page<AccountTransaction> getAccountTransactionsPage(Integer page, Integer pageSize, String sortBy);
}
