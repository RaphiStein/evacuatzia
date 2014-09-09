package evacuatzia_proj;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.Ignore;
import org.junit.Test;

import evacuatzia_proj.utils.StringHashingUtils;

public class ChecksumTest {

	@Test
	public void testMD5Encoder() throws NoSuchAlgorithmException {
		assertEquals("8d074e173ea899bc8441d97341ae638a", StringHashingUtils.toMD5("someStupidString"));
	}
	
	@Ignore
	@Test
	public void createKey() {
		System.out.println(StringHashingUtils.toMD5("cs236369z"));
	}
}
