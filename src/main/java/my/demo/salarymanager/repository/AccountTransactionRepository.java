package my.demo.salarymanager.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.Account;
import my.demo.salarymanager.entity.AccountTransaction;
import my.demo.salarymanager.entity.Employee;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
	
}
