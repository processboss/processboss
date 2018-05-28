package br.com.processboss.core.exception;

public class ProcessBossException extends Exception {

	private static final long serialVersionUID = -3937274075606762004L;

	public ProcessBossException() {
		super();
	}

	public ProcessBossException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcessBossException(String message) {
		super(message);
	}

	public ProcessBossException(Throwable cause) {
		super(cause);
	}
	
	
}
