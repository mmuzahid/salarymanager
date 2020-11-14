package my.demo.salarymanager.exception;

public class AccountTransactionException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public AccountTransactionException() {
		super();
	}
	
	public AccountTransactionException(String message) {
		super(message);
	}
}
