package my.demo.salarymanager.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import my.demo.salarymanager.entity.Employee;

public interface EmployeeService {
	public Employee getEmployeeById(Long id);
	public List<Employee> getEmployees();
	public List<Employee> getEmployees(Integer page, Integer pageSize, String sortBy);
	public void saveEmployee(Employee employee);
	public void deleteEmployeeById(Long id);
	public Page<Employee> getEmployeesPage(Integer page, Integer pageSize, String sortBy);
	public void deleteAllEmployees();
}
