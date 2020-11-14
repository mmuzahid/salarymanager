package my.demo.salarymanager.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
