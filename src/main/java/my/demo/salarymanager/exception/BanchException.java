package my.demo.salarymanager.exception;

public class BanchException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public BanchException() {
		super();
	}
	
	public BanchException(String message) {
		super(message);
	}
}
