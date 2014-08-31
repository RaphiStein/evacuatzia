package evacuatzia_proj;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import evacuatzia_proj.utils.StringEncoder;

public class ChecksumTest {

	@Test
	public void testMD5Encoder() throws NoSuchAlgorithmException {
		assertEquals("8d074e173ea899bc8441d97341ae638a", StringEncoder.toMD5("someStupidString"));
	}

}
