package br.com.http.utils;

public abstract class IPFactory {

	private IPFactory() {
	}

	public static IP buildIPFromString(String address) {
		if (IPv4.isIPv4(address)) {
			return new IPv4(address);
		} else if (IPv6.isIPv6(address)) {
			return new StubIPv6(address);
		} else {
			throw new InvalidIPFormatException(address);
		}
	}
}
