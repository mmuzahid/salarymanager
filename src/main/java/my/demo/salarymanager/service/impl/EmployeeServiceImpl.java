package my.demo.salarymanager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import my.demo.salarymanager.entity.Employee;
import my.demo.salarymanager.exception.EmployeeException;
import my.demo.salarymanager.repository.EmployeeRepository;
import my.demo.salarymanager.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	@Cacheable(value = "employees", key="#id")
	public Employee getEmployeeById(Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new EmployeeException("No employee record exist for given id");
        }
	}
	
	
	@Override
	public List<Employee> getEmployees() {		
		return employeeRepository.findAll();
	}
	

	@Override
	public List<Employee> getEmployees(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<Employee> employeePage = employeeRepository.findAll(pageable);
		return employeePage.getContent();
	}
	
	@Override
	public Page<Employee> getEmployeesPage(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<Employee> employeePage = employeeRepository.findAll(pageable);
		return employeePage;
	}

	@Override
	@CacheEvict(value = "employees", key = "#employee.id")
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	@CacheEvict(value = "employees", key = "#id")
	public void deleteEmployeeById(Long id) {
		try {
			employeeRepository.deleteById(id);
		} catch(EmptyResultDataAccessException ex) {
			throw new EmployeeException("No employee record exist to delete");
		}
	}
	
	@Override
	public void deleteAllEmployees() {
		employeeRepository.deleteAll();
	}



}
