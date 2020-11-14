package my.demo.salarymanager.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.AccountType;
import my.demo.salarymanager.entity.Bank;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
