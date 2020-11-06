package tools;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.*;

/**
 * File Name : PasswordEncoder.java 
 * Created on: 1 Jul 2010 
 * Created by @author Ashish Shukla 
 * Orange Hut Solution Limited. 
 * http://www.orangehut.com
 * Modifications made by @author Shawn Francis 
 */
public class PasswordEncoder {
    /**
     * Count for the number of time to hash. The more you hash,
     * the more difficult it would be for the attacker to
     * determine the password. 
     */
    private final static int ITERATION_COUNT = 5;

    /**
	 * This class is only created once when the program is started.
	 * Once it has been created it can be called at any time during the course
	 * of the program cycle.  
	 */
    private PasswordEncoder() {}
    
    /**
     * This method will encode a password one way.  Using also a key to hash the password further
     * so it is hard to decipher.
     * @param password
     * @param saltKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String encode(String password, String saltKey) throws NoSuchAlgorithmException, IOException {
    	String encodedPassword = null;
    	byte[] salt = base64ToByte(saltKey);
    	
    	MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	digest.reset();
    	digest.update(salt);
    	
    	byte[] bytePass = digest.digest(password.getBytes("UTF-8"));
    	
    	for (int i = 0; i < ITERATION_COUNT; i++) {
    		digest.reset();
    		bytePass = digest.digest(bytePass);
    	}

    	encodedPassword = byteToBase64(bytePass);
    	return encodedPassword;
    }

    /**
     * This method will convert Base 64 String to a byte array.
     * @param string
     * @return byte[]
     * @throws IOException
     */
    private static byte[] base64ToByte(String string) throws IOException {
    	byte[] byteArray = Base64.decodeBase64(string);
    	return byteArray;
    }

    /**
     * This method will convert a byte array to Base 64 String.
     * @param byteArray
     * @return String
     * @throws IOException
     */
    private static String byteToBase64(byte[] byteArray) {
    	String returnString = new String(Base64.encodeBase64(byteArray));
		return returnString;
    }

}
