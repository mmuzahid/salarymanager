package my.demo.salarymanager.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.boot.context.properties.bind.DefaultValue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@ManyToOne
	private Bank bank;
	@NotNull
	@ManyToOne
	private Branch branch;
	@NotNull
	@ManyToOne
	private AccountType accountType;
	@NotNull
	@Size(min = 5, max = 50, message = "length of Name must be between 5 and 50")
	private String accountName;
	@NotNull
	private BigInteger currentBalance = BigInteger.ZERO;

}
