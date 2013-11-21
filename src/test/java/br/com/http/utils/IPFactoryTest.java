package br.com.http.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IPFactoryTest {

	@Test
	public void testBuildIPFromString() {
		assertTrue(IPFactory.buildIPFromString("0.0.0.0").isIPv4());
		assertTrue(IPFactory.buildIPFromString("0:0:0:0:0:0").isIPv6());
	}

	@Test(expected = InvalidIPFormatException.class)
	public void testBuildIPFromStringWithWrongFormat() {
		IPFactory.buildIPFromString("0.0.0.0.0");
	}
}
