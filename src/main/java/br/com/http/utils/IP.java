package br.com.http.utils;

public interface IP {

	boolean isPrivate();

	boolean isPublic();

	String toString();

	long toLong();

	byte[] toBytes();

	boolean matches(IP ip);
}
