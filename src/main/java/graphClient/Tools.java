package graphClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

// import javax.xml.bind.DatatypeConverter;

public class Tools {

	// https://stackoverflow.com/questions/1515489/compute-sha-1-of-byte-array
	public static String SHAsum(byte[] convertme) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");

		// not support in Java 11: return
		// DatatypeConverter.printHexBinary(md.digest(convertme));
		return encodeHexString(md.digest(convertme));
	}

	// https://www.baeldung.com/java-byte-arrays-hex-strings
	public static String byteToHex(byte num) {
		char[] hexDigits = new char[2];
		hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
		hexDigits[1] = Character.forDigit((num & 0xF), 16);
		return new String(hexDigits);
	}

	// https://www.baeldung.com/java-byte-arrays-hex-strings
	public static String encodeHexString(byte[] byteArray) {
		StringBuffer hexStringBuffer = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			hexStringBuffer.append(byteToHex(byteArray[i]));
		}
		return hexStringBuffer.toString();
	}

	public static byte[] SHAbsum(byte[] convertme) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		return md.digest(convertme);
	}

	private static String byteArray2Hex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String str = formatter.toString();
		formatter.close();
		return str;
	}

	public static String getHexDigestOfObject(Object o) {
		// https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		String strHexDigest = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(o);
			out.flush();
			byte[] yourBytes = bos.toByteArray();
			strHexDigest = SHAsum(yourBytes);
		} catch (Exception ex) {
			System.err.println(ex); // ignore close exception
			System.exit(-1);
		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				// ignore close exception
			}
		}

		return strHexDigest;
	}

	public static byte[] getByteDigestOfObject(Object o) {
		// https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] byteDigest = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(o);
			out.flush();
			byte[] yourBytes = bos.toByteArray();
			byteDigest = SHAbsum(yourBytes);
		} catch (Exception ex) {
			System.err.println(ex); // ignore close exception
			System.exit(-1);
		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				// ignore close exception
			}
		}

		return byteDigest;
	}

	public static String getLogPrefix() {
		String prefix = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss.SSS");
		Date date = new Date(System.currentTimeMillis());
		prefix = formatter.format(date) + ", Thread: " + Thread.currentThread().getId();
		;

		return prefix;
	}

	public static <T> String arrayListToString(ArrayList<T> a) {
		String str = "";

		for (T v : a) {
			str += (v + ", ");
		}

		return str;
	}

}
