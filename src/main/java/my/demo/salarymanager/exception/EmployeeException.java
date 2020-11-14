package my.demo.salarymanager.exception;

public class EmployeeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public EmployeeException() {
		super();
	}
	
	public EmployeeException(String message) {
		super(message);
	}
}
