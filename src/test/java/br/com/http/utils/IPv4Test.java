package br.com.http.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class IPv4Test {

	@Test
	public void testCorrectPatterns() {
		new IPv4("127.0.0.1");
		new IPv4("255.255.255.255");
		new IPv4("0.0.0.0");
		new IPv4("172.16.129.1");
		new IPv4("192.168.0.1");
		new IPv4("10.0.0.1");
		new IPv4("132.87.0.5");
		new IPv4("132.87.0.0/8");
		new IPv4("132.87.0.0/16");
		new IPv4("132.87.0.0/32");
	}

	@Test(expected = InvalidIPv4Exception.class)
	public void testEmptyStringPattern() {
		new IPv4("");
	}

	@Test(expected = InvalidIPv4Exception.class)
	public void testNullStringPattern() {
		new IPv4(null);
	}

	@Test(expected = InvalidIPv4Exception.class)
	public void testIncompleteStringPattern() {
		new IPv4("10.0.0");
	}

	@Test(expected = InvalidIPv4Exception.class)
	public void testIPv6Pattern() {
		new IPv4("2001:0db8:85a3:0042:1000:8a2e:0370:7334");
	}

	@Test(expected = InvalidIPv4Exception.class)
	public void testWrongRangePattern() {
		new IPv4("256.0.0.0");
	}

	@Test
	public void testRemoveIpClass() {
		assertEquals("10.0.0.0", IPv4.removeIPClass("10.0.0.0"));
		assertEquals("10.0.0.0", IPv4.removeIPClass("10.0.0.0/8"));
	}

	@Test
	public void testCalculateHigherIPAddressAccordingToClass() {
		assertEquals("10.0.0.0",
				IPv4.toString(IPv4.calculateHigherIPAddressAccordingToClass(IPv4.toBytes("10.0.0.0"), 32)));
		assertEquals("10.255.255.255",
				IPv4.toString(IPv4.calculateHigherIPAddressAccordingToClass(IPv4.toBytes("10.0.0.0"), 8)));
		assertEquals("10.0.255.255",
				IPv4.toString(IPv4.calculateHigherIPAddressAccordingToClass(IPv4.toBytes("10.0.0.0"), 16)));
		assertEquals("172.31.255.255",
				IPv4.toString(IPv4.calculateHigherIPAddressAccordingToClass(IPv4.toBytes("172.16.0.0"), 12)));
	}

	@Test
	public void testCalculateHigherIPAddressAccordingToClassBits() {
		assertArrayEquals(new boolean[0], IPv4.calculateHigherIPAddressAccordingToClass(new boolean[] {}, 0));
		assertArrayEquals(new boolean[] { true, true },
				IPv4.calculateHigherIPAddressAccordingToClass(new boolean[] { true, false }, 1));
		assertArrayEquals(new boolean[] { true, false },
				IPv4.calculateHigherIPAddressAccordingToClass(new boolean[] { true, false }, 2));
		assertArrayEquals(new boolean[] { false, false },
				IPv4.calculateHigherIPAddressAccordingToClass(new boolean[] { false, false }, 2));
	}

	private void assertArrayEquals(boolean[] array1, boolean[] array2) {
		assertEquals(array1.length, array2.length);

		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				fail("Arrays are different at position " + i + " " + array1[i] + " != " + array2[i]);
			}
		}
	}

	@Test
	public void testExtractIPClass() {
		assertEquals(32, IPv4.extractIPClass("10.0.0.0"));
		assertEquals(8, IPv4.extractIPClass("10.0.0.0/8"));
		assertEquals(16, IPv4.extractIPClass("10.0.0.0/16"));
		assertEquals(12, IPv4.extractIPClass("172.16.0.0/12"));
	}

	@Test
	public void testMatches() {
		assertTrue(new IPv4("10.0.0.0/16").matches(new IPv4("10.0.0.1")));
		assertTrue(new IPv4("10.0.0.1").matches(new IPv4("10.0.0.1")));
		assertTrue(new IPv4("10.0.0.0/24").matches(new IPv4("10.0.0.1")));
		assertFalse(new IPv4("10.0.0.0/16").matches(new IPv4("172.16.129.1")));
		assertFalse(new IPv4("10.0.0.0/16").matches(new IPv4("10.1.0.1")));
		assertTrue(new IPv4("10.0.0.0/8").matches(new IPv4("10.1.1.1")));
	}

	@Test
	public void testPublicIpAddress() {
		assertTrue(new IPv4("177.45.108.97").isPublic());
		assertTrue(new IPv4("50.16.224.25").isPublic());
		assertFalse(new IPv4("127.0.0.1").isPublic());
		assertFalse(new IPv4("10.0.0.1").isPublic());
		assertFalse(new IPv4("172.16.129.8").isPublic());
		assertFalse(new IPv4("172.30.129.8").isPublic());
		assertTrue(new IPv4("172.48.129.8").isPublic());
	}
}
