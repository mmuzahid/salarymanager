package my.demo.salarymanager.entity;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String name;
	@ManyToOne(cascade=CascadeType.ALL)
	private Account account;
	@ManyToOne
	private Grade grade;	
	@NotNull
	private String address;
	@NotNull
	private String mobile;	
	
	@Transient
	public BigInteger getSalary() {
		BigInteger basic = grade.getBasicSalary();
		BigInteger houseRent = grade.getBasicSalary().multiply(BigInteger.valueOf(20)).divide(BigInteger.valueOf(100));
		BigInteger medical =grade.getBasicSalary().multiply(BigInteger.valueOf(15)).divide(BigInteger.valueOf(100));
		BigInteger salary = basic.add(houseRent).add(medical);
		
		return salary;		
	}
	
}
