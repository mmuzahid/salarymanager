package my.demo.salarymanager.exception;

public class BankException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public BankException() {
		super();
	}
	
	public BankException(String message) {
		super(message);
	}
}
