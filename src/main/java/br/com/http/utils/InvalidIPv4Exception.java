package br.com.http.utils;

public class InvalidIPv4Exception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidIPv4Exception(String value, Throwable e) {
		super("Invalid IPv4 pattern : " + value, e);
	}

	public InvalidIPv4Exception(String value) {
		this("Invalid IPv4 pattern : " + value, null);
	}
}
