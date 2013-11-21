package br.com.http.utils;

public class StubIPv6 implements IP {

	private final String address;

	public StubIPv6(String address) {
		super();
		this.address = address;
	}

	@Override
	public boolean isPrivate() {
		return false;
	}

	@Override
	public boolean isPublic() {
		return true;
	}

	@Override
	public long toLong() {
		return 0;
	}

	@Override
	public byte[] toBytes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean matches(IP ip) {
		return address.equals(ip.toString());
	}

	@Override
	public boolean isIPv4() {
		return false;
	}

	@Override
	public boolean isIPv6() {
		return true;
	}

	@Override
	public String toString() {
		return address;
	}
}
