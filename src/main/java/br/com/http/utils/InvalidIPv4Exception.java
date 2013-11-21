package br.com.http.utils;

public class InvalidIPv4Exception extends InvalidIPFormatException {

	private static final long serialVersionUID = 1L;

	public InvalidIPv4Exception(String value, Throwable e) {
		super(value, e);
	}

	public InvalidIPv4Exception(String value) {
		this(value, null);
	}
}
