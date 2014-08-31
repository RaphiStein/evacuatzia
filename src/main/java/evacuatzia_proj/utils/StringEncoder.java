package evacuatzia_proj.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringEncoder {
	public static String toMD5(String orig) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// will never happen
			return null;
		}
		byte[] digestedByte = md.digest(orig.getBytes());
		return String.format("%032X", new BigInteger(1, digestedByte)).toLowerCase();
	}
}
