package my.demo.salarymanager.entity;

import java.math.BigInteger;
import java.time.Month;
import java.time.Year;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SalaryPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@ManyToOne
	private Company company;
	@NotNull
	@ManyToOne
	private Employee employee;
	@NotNull
	@ManyToOne
	private Grade grade;
	@NotNull
	private BigInteger amount;
	//@NotNull
	private Month month;
	//@NotNull
	private Year year;
	@NotNull
	private Date createdAt;
	//@NotNull
	private String remarks;

}

