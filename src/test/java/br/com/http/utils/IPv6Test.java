package br.com.http.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class IPv6Test {

	@Test
	public void testValidIPv6Patterns() {
		assertValidIPv6("1111:2222:3333:4444:5555:6666:7777:8888");
		assertValidIPv6("1111:2222:3333:4444:5555:6666:7777::");
		assertValidIPv6("1111:2222:3333:4444:5555:6666::");
		assertValidIPv6("1111:2222:3333:4444:5555::");
		assertValidIPv6("1111:2222:3333:4444::");
		assertValidIPv6("1111:2222:3333::");
		assertValidIPv6("1111:2222::");
		assertValidIPv6("1111::");
		assertValidIPv6("::");
		assertValidIPv6("1111:2222:3333:4444:5555:6666::8888");
		assertValidIPv6("1111:2222:3333:4444:5555::8888");
		assertValidIPv6("1111:2222:3333:4444::8888");
		assertValidIPv6("1111:2222:3333::8888");
		assertValidIPv6("1111:2222::8888");
		assertValidIPv6("1111::8888");
		assertValidIPv6("::8888");
		assertValidIPv6("1111:2222:3333:4444:5555::7777:8888");
		assertValidIPv6("1111:2222:3333:4444::7777:8888");
		assertValidIPv6("1111:2222:3333::7777:8888");
		assertValidIPv6("1111:2222::7777:8888");
		assertValidIPv6("1111::7777:8888");
		assertValidIPv6("::7777:8888");
		assertValidIPv6("1111:2222:3333:4444::6666:7777:8888");
		assertValidIPv6("1111:2222:3333::6666:7777:8888");
		assertValidIPv6("1111:2222::6666:7777:8888");
		assertValidIPv6("1111::6666:7777:8888");
		assertValidIPv6("::6666:7777:8888");
		assertValidIPv6("1111:2222:3333::5555:6666:7777:8888");
		assertValidIPv6("1111:2222::5555:6666:7777:8888");
		assertValidIPv6("1111::5555:6666:7777:8888");
		assertValidIPv6("::5555:6666:7777:8888");
		assertValidIPv6("1111:2222::4444:5555:6666:7777:8888");
		assertValidIPv6("1111::4444:5555:6666:7777:8888");
		assertValidIPv6("::4444:5555:6666:7777:8888");
		assertValidIPv6("1111::3333:4444:5555:6666:7777:8888");
		assertValidIPv6("::3333:4444:5555:6666:7777:8888");
		assertValidIPv6("::2222:3333:4444:5555:6666:7777:8888");
	}

	private void assertValidIPv6(String address) {
		assertTrue(IPv6.isIPv6(address));
	}
}
