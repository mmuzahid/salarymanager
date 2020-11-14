package my.demo.salarymanager.exception;

public class AccountException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public AccountException() {
		super();
	}
	
	public AccountException(String message) {
		super(message);
	}
}
