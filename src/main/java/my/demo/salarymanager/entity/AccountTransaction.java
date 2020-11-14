package my.demo.salarymanager.entity;

import java.math.BigInteger;
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
public class AccountTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@ManyToOne
	private Account account;
	@NotNull
	private BigInteger amount;
	@NotNull
	private BigInteger balance;
	@NotNull
	private OperationType operationType;
	@NotNull
	private Date createdAt;	


	private enum OperationType {
		DEBIT, CREDIT
	}
}

