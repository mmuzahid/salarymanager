package my.demo.salarymanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.Employee;
import my.demo.salarymanager.entity.SalaryPayment;

public interface SalaryPaymentRepository extends JpaRepository<SalaryPayment, Long> {
	public List<SalaryPayment> findByEmployee(Employee employee);
	public Page<SalaryPayment>  findByEmployee(Employee employee, Pageable pageable);
}
