package my.demo.salarymanager.exception;

public class SalaryPaymentException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public SalaryPaymentException() {
		super();
	}
	
	public SalaryPaymentException(String message) {
		super(message);
	}
}
