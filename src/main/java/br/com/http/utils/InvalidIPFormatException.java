package br.com.http.utils;

public class InvalidIPFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidIPFormatException(String address, Throwable e) {
		super("Invalid IP pattern : " + address, e);
	}

	public InvalidIPFormatException(String address) {
		this("Invalid IP pattern : " + address, null);
	}
}
