package my.demo.salarymanager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.Bank;
import my.demo.salarymanager.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
	List<Branch> findByBank(Bank bank);

}
