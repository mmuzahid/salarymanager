package my.demo.salarymanager.controller;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import my.demo.salarymanager.dto.TabulatorDto;
import my.demo.salarymanager.dto.converter.TabulatorDtoConverter;
import my.demo.salarymanager.entity.Account;
import my.demo.salarymanager.entity.Company;
import my.demo.salarymanager.entity.Employee;
import my.demo.salarymanager.entity.Grade;
import my.demo.salarymanager.entity.LowerBasic;
import my.demo.salarymanager.entity.SalaryPayment;
import my.demo.salarymanager.exception.EmployeeException;
import my.demo.salarymanager.service.AccountService;
import my.demo.salarymanager.service.BankService;
import my.demo.salarymanager.service.CompanyService;
import my.demo.salarymanager.service.EmployeeService;
import my.demo.salarymanager.service.GradeService;
import my.demo.salarymanager.service.SalaryPaymentService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class); 
	
	@Autowired
	private EmployeeService employeeService;	
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private BankService	bankService;
	
	@Autowired
	private CompanyService	companyService;
	
	@Autowired
	private SalaryPaymentService	salaryPaymentService;
	
	@GetMapping(path="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Employee viewJson(@PathVariable(name="id") Long id, Model model) {
		return employeeService.getEmployeeById(id);
	}
	
	@PostMapping(value = "/")
	public String save(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "employeeForm";
		}

		employeeService.saveEmployee(employee);
		logger.info("Saved Employee : {}", employee);
		return "redirect:/employee/";
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteEmployee(@PathVariable(name="id") Long id, Model model) {
		try{
			Employee employee = employeeService.getEmployeeById(id);
			logger.info("Deleting Employee: {}", employee);
			employeeService.deleteEmployeeById(id);
			logger.info("Deleted Employee id: {}", id);
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(Exception ex) {
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(path="/getEmployeePayments/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TabulatorDto<SalaryPayment> getEmployeePayments(@PathVariable(name="id") Long id,@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize, @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
		Employee employee =  employeeService.getEmployeeById(id);
		
		return TabulatorDtoConverter.<SalaryPayment>convertToDto(salaryPaymentService.getSalaryPaymentListByEmployeeId(employee, page, pageSize, sortBy));
	}
	

	@GetMapping(path="/{id}")
	public String view(@PathVariable(name="id") Long id, Model model) {
		Employee employee =  employeeService.getEmployeeById(id);
		List<SalaryPayment> salaryPayments =  salaryPaymentService.getSalaryPaymentListByEmployeeId(employee);
		model.addAttribute("employee", employee);
		model.addAttribute("salaryPayments", salaryPayments);
		return "employeeView";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable(name="id") Long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		model.addAttribute("account", employee.getAccount() == null ? employee.getAccount() : new Account());
		model.addAttribute("banks", bankService.getBanks());
		model.addAttribute("branches", bankService.getBranches());
		model.addAttribute("accountTypes", accountService.getAccountTypes());
		model.addAttribute("grades", gradeService.getGrades());
		return "employeeForm";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("account", new Account());
		model.addAttribute("banks", bankService.getBanks());
		model.addAttribute("branches", bankService.getBranches());
		model.addAttribute("accountTypes", accountService.getAccountTypes());
		model.addAttribute("grades", gradeService.getGrades());
		return "employeeForm";
	}
	
	@GetMapping("/")
	public String home(Model model) {		
		Company company = companyService.getCompany();
		model.addAttribute("company", company);
		model.addAttribute("lowerBasicAmount", gradeService.getLowerBasic().getValue());
		return "employeeHome";
	}

	@GetMapping("/paysalary")
	@Transactional
	public String paysalary(Model model) {		
		Company company = companyService.getCompany();
		List<Employee> employees = employeeService.getEmployees().stream().sorted((e1, e2) -> e1.getSalary().compareTo(e2.getSalary())).collect(Collectors.toList());
		try {		
			for (Employee employee : employees) {
				BigInteger salary = employee.getSalary();
				SalaryPayment salaryPayment = new SalaryPayment();
				salaryPayment.setAmount(salary);
				salaryPayment.setCompany(company);
				salaryPayment.setEmployee(employee);
				salaryPayment.setGrade(employee.getGrade());
				salaryPayment.setCreatedAt(new Date());
				
				if (company.getAccount().getCurrentBalance().compareTo(salary) == -1) {
					model.addAttribute("balanceError", "Partially Updated due to Insufficent Balance, balance should be minimum " + salary);
					throw new RuntimeException();
				}
				
				company.getAccount().setCurrentBalance(company.getAccount().getCurrentBalance().subtract(salary));
				employee.getAccount().setCurrentBalance(employee.getAccount().getCurrentBalance().add(salary));
				salaryPaymentService.saveSalaryPayment(salaryPayment);
				employeeService.saveEmployee(employee);
				companyService.save(company);
	
			}
			
			model.addAttribute("balanceSuccess", "Salary Transfered Successfully");

		} catch(Exception ex) {
		}

		model.addAttribute("company", company);
		model.addAttribute("lowerBasicAmount", gradeService.getLowerBasic().getValue());
		return "employeeHome";
	}
		
	@GetMapping(value = "/list", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TabulatorDto<Employee> list(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize, @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
	
		Page<Employee> employeePage = employeeService.getEmployeesPage(page-1, pageSize, sortBy);
		
		return TabulatorDtoConverter.<Employee>convertToDto(employeePage);
	}
	
	@ExceptionHandler(EmployeeException.class)
	public String handleNpeException(EmployeeException ex, Model model) {
		model.addAttribute("exception", ex);
		return "employeeException";
	}
	
	@PostMapping("/increaseCompanyBalance")
	public String increaseCompanyBalance(@RequestParam BigInteger increaseAmount) {		
		
		Company company = companyService.getCompany();
		company.getAccount().setCurrentBalance(company.getAccount().getCurrentBalance().add(increaseAmount));
		companyService.save(company);

		return "redirect:/employee/";
	}
	
	@PostMapping("/saveLowerBasic")
	public String updateGrades(@RequestParam BigInteger lowerBasicAmount) {
		
		LowerBasic lowerBasic = gradeService.getLowerBasic();
		lowerBasic.setValue(lowerBasicAmount);
		gradeService.saveLowerBasic(lowerBasic);
		updateGradeBasics(lowerBasicAmount);
		
		return "redirect:/employee/";
	}
	
	public void updateGradeBasics(BigInteger basic) {
		List<Grade> grades = gradeService.getGrades();
		for (Grade grade : grades) {
			switch (grade.getName()) {
			case "Grade-6":
				grade.setBasicSalary(basic);
				break;
			case "Grade-5":
				grade.setBasicSalary(basic.add(BigInteger.valueOf(5000)));
				break;
			case "Grade-4":
				grade.setBasicSalary(basic.add(BigInteger.valueOf(10000)));
				break;
			case "Grade-3":
				grade.setBasicSalary(basic.add(BigInteger.valueOf(15000)));
				break;
			case "Grade-2":
				grade.setBasicSalary(basic.add(BigInteger.valueOf(20000)));
				break;
			case "Grade-1":
				grade.setBasicSalary(basic.add(BigInteger.valueOf(25000)));
				break;
			default:
				break;
			}
			gradeService.saveGrade(grade);
		}
	}
	
}