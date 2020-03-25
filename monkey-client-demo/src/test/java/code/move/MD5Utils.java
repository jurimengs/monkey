package code.move;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    
    public static String Md5(String input) {
    	MessageDigest m = null;
    	try {
    		m = MessageDigest.getInstance("MD5");
    	} catch (NoSuchAlgorithmException e) {
    		e.printStackTrace();
    	}
    	m.update(input.getBytes(), 0, input.length());
    	byte p_md5Data[] = m.digest();
    
    	String mOutput = new String();
    	for (int i = 0; i < p_md5Data.length; i++) {
    		int b = (0xFF & p_md5Data[i]);
    		// if it is a single digit, make sure it have 0 in front (proper
    		// padding)
    		if (b <= 0xF)
    			mOutput += "0";
    		// add number to string
    		mOutput += Integer.toHexString(b);
    	}
    	// hex string to uppercase
    	return mOutput.toLowerCase();
    }
    
    private static MessageDigest md = null;
    
    public static String md5Encrypt(String input) {
    	try {
    		if (md == null) {
    			md = MessageDigest.getInstance("MD5");
    		}
    		byte buffer[] = input.getBytes();
    		md.update(buffer);
    		byte bDigest[] = md.digest();
    		md.reset();
    		BigInteger bi = new BigInteger(1, bDigest);
    		return bi.toString(16);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    
    
}

