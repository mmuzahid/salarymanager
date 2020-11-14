package my.demo.salarymanager.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.Account;
import my.demo.salarymanager.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
