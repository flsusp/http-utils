package br.com.http.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents an IPv4 address and uses 32-bit (four-byte) addresses representation. IPv4 addresses may be written in any
 * notation expressing a 32-bit integer value, but for human convenience this class uses the dot-decimal notation, which
 * consists of four octets of the address expressed individually in decimal and separated by periods (from 0.0.0.0 to
 * 255.255.255.255).
 *
 * This class also supports the CIDR notation, which is a compact representation of an IP address and its associated
 * routing prefix. The notation is constructed from the IP address and the prefix size, the latter being equivalent to
 * the number of leading 1 bits in the routing prefix mask. The IP address is expressed according to the standards of
 * IPv4. It is followed by a separator character, the slash ('/') character, and the prefix size expressed as a decimal
 * number. The address may denote a single, distinct interface address or the beginning address of an entire network.
 * The maximum size of the network is given by the number of addresses that are possible with the remaining,
 * least-significant bits below the prefix. This is often called the host identifier. For example:
 *
 * <ul>
 * <li>192.168.100.0/24 represents the given IPv4 address and its associated routing prefix 192.168.100.0, or
 * equivalently, its subnet mask 255.255.255.0.</li>
 * <li>the IPv4 block 192.168.100.0/22 represents the 1,024 IPv4 addresses from 192.168.100.0 to 192.168.103.255.</li>
 * </ul>
 *
 * @author flsusp
 */
public class IPv4 implements IP {

	private static final Pattern IPv4_PATTERN = Pattern
			.compile("\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}(\\/(3[0-2]|2[0-9]|1[0-9]|[1-9]))?\\z");
	private static final Pattern IPv4_CLASS_PATTERN = Pattern.compile("\\/(3[0-2]|2[0-9]|1[0-9]|[1-9])");

	private static final Set<IPv4> privateIPAddresses = new HashSet<>();

	static {
		privateIPAddresses.add(new IPv4("127.0.0.1"));
		privateIPAddresses.add(new IPv4("10.0.0.0/8"));
		privateIPAddresses.add(new IPv4("172.16.0.0/12"));
		privateIPAddresses.add(new IPv4("192.168.0.0/16"));
	}

	private final String address;
	private final long rangeStart;
	private final long rangeEnd;

	public IPv4(String address) {
		super();
		if (StringUtils.isEmpty(address)) {
			throw new InvalidIPv4Exception(address);
		}

		if (!isIPv4(address)) {
			throw new InvalidIPv4Exception(address);
		}

		this.address = address;
		this.rangeStart = getAddressAsLong(removeIPClass(address));
		this.rangeEnd = getAddressAsLong(toString(calculateHigherIPAddressAccordingToClass(toBytes(),
				extractIPClass(address))));
	}

	public static boolean isIPv4(String address) {
		return IPv4_PATTERN.matcher(address).matches();
	}

	protected static String toString(byte[] bytes) {
		int i = 4;
		String ipAddress = "";
		for (byte raw : bytes) {
			ipAddress += (raw & 0xFF);
			if (--i > 0) {
				ipAddress += ".";
			}
		}
		return ipAddress;
	}

	protected static int extractIPClass(String address) {
		Matcher matcher = IPv4_CLASS_PATTERN.matcher(address);
		if (matcher.find()) {
			return Integer.valueOf(matcher.group().replace("/", ""));
		} else {
			return 32;
		}
	}

	protected static byte[] calculateHigherIPAddressAccordingToClass(byte[] bytes, int ipClass) {
		boolean[] bits = toBits(bytes);

		bits = calculateHigherIPAddressAccordingToClass(bits, ipClass);

		return toBytes(bits);
	}

	protected static boolean[] calculateHigherIPAddressAccordingToClass(boolean[] bits, int ipClass) {
		for (int i = 0; i < bits.length; i++) {
			if (ipClass > 0) {
				ipClass--;
			} else {
				bits[i] = true;
			}
		}
		return bits;
	}

	private static byte[] toBytes(boolean[] bits) {
		byte[] result = new byte[(bits.length + Byte.SIZE - 1) / Byte.SIZE];
		for (int i = 0; i < bits.length; i++) {
			if (bits[i])
				result[i / Byte.SIZE] = (byte) (result[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
		}

		return result;
	}

	private static boolean[] toBits(byte[] bytes) {
		boolean[] bits = new boolean[bytes.length * Byte.SIZE];
		for (int i = 0; i < Byte.SIZE * bytes.length; i++) {
			bits[i] = (bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? false : true;
		}
		return bits;
	}

	protected static String removeIPClass(String address) {
		return IPv4_CLASS_PATTERN.matcher(address).replaceFirst("");
	}

	public static long getAddressAsLong(String address) {
		String[] parts = address.split("[.]");
		return Long.valueOf(parts[3]) + 256l * Long.valueOf(parts[2]) + 256l * 256l * Long.valueOf(parts[1]) + 256l
				* 256l * 256l * Long.valueOf(parts[0]);
	}

	@Override
	public boolean isPrivate() {
		for (IPv4 privateIPAddress : privateIPAddresses) {
			if (privateIPAddress.matches(this)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPublic() {
		return !isPrivate();
	}

	@Override
	public long toLong() {
		return rangeStart;
	}

	protected static byte[] toBytes(String address) {
		String[] parts = address.split("\\.");
		byte[] bytes = new byte[4];

		for (int i = 0; i < 4; i++) {
			int value = Integer.valueOf(parts[i]);
			bytes[i] = (byte) value;
		}

		return bytes;
	}

	@Override
	public byte[] toBytes() {
		return toBytes(removeIPClass(address));
	}

	@Override
	public boolean matches(IP ip) {
		return ip.toLong() >= rangeStart && ip.toLong() <= rangeEnd;
	}

	@Override
	public boolean isIPv4() {
		return true;
	}

	@Override
	public boolean isIPv6() {
		return false;
	}

	@Override
	public String toString() {
		return address;
	}
}
