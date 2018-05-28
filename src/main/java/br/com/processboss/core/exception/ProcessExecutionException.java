package br.com.processboss.core.exception;

public class ProcessExecutionException extends Exception {

	private static final long serialVersionUID = -3937274075606762004L;

	public ProcessExecutionException() {
		super();
	}

	public ProcessExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcessExecutionException(String message) {
		super(message);
	}

	public ProcessExecutionException(Throwable cause) {
		super(cause);
	}
	
	
}
