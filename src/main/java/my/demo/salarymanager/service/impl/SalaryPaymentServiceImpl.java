package my.demo.salarymanager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import my.demo.salarymanager.entity.Employee;
import my.demo.salarymanager.entity.SalaryPayment;
import my.demo.salarymanager.exception.SalaryPaymentException;
import my.demo.salarymanager.repository.SalaryPaymentRepository;
import my.demo.salarymanager.service.SalaryPaymentService;

@Service
public class SalaryPaymentServiceImpl implements SalaryPaymentService {

	@Autowired
	private SalaryPaymentRepository salaryPaymentRepository;
	
	@Override
	@Cacheable(value = "salaryPayments", key="#id")
	public SalaryPayment getSalaryPaymentById(Long id) {
		Optional<SalaryPayment> salaryPayment = salaryPaymentRepository.findById(id);
        if(salaryPayment.isPresent()) {
            return salaryPayment.get();
        } else {
            throw new SalaryPaymentException("No salaryPayment record exist for given id");
        }
	}

	@Override
	public List<SalaryPayment> getSalaryPayments(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<SalaryPayment> salaryPaymentPage = salaryPaymentRepository.findAll(pageable);
		return salaryPaymentPage.getContent();
	}
	
	@Override
	public Page<SalaryPayment> getSalaryPaymentsPage(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<SalaryPayment> salaryPaymentPage = salaryPaymentRepository.findAll(pageable);
		return salaryPaymentPage;
	}

	@Override
	@CacheEvict(value = "salaryPayments", key = "#salaryPayment.id")
	public void saveSalaryPayment(SalaryPayment salaryPayment) {
		salaryPaymentRepository.save(salaryPayment);
	}

	@Override
	public List<SalaryPayment> getSalaryPaymentListByEmployeeId(Employee employee) {
		
		return salaryPaymentRepository.findByEmployee(employee);
		
	}
	
	@Override
	public Page<SalaryPayment> getSalaryPaymentListByEmployeeId(Employee employee, Integer page, Integer pageSize, String sortBy) {		
		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		
		SalaryPayment s = new SalaryPayment();
		s.setEmployee(employee);
		
		Example<SalaryPayment> example = Example.of(s);
		
		Page<SalaryPayment> employeePage = salaryPaymentRepository.findAll(example, pageable);
		return employeePage;
	}

}
