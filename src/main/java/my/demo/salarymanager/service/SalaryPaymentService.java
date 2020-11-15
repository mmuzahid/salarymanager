package my.demo.salarymanager.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import my.demo.salarymanager.entity.Employee;
import my.demo.salarymanager.entity.SalaryPayment;

public interface SalaryPaymentService {
	public SalaryPayment getSalaryPaymentById(Long id);
	public List<SalaryPayment> getSalaryPayments(Integer page, Integer pageSize, String sortBy);
	public void saveSalaryPayment(SalaryPayment salaryPayment);
	public Page<SalaryPayment> getSalaryPaymentsPage(Integer page, Integer pageSize, String sortBy);
	public List<SalaryPayment> getSalaryPaymentListByEmployeeId(Employee employee);
	public Page<SalaryPayment> getSalaryPaymentListByEmployee(Employee employee, Integer page, Integer pageSize,
			String sortBy);
}
