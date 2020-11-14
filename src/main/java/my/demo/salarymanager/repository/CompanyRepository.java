package my.demo.salarymanager.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.Account;
import my.demo.salarymanager.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
