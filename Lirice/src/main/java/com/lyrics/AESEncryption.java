package com.lyrics;

import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

public class AESEncryption {

	 private static SecretKeySpec secretKey;
	   
	    private static byte[] key;
	 
	    public static void setKey(String myKey)
	    {
	            try {
	            key = myKey.getBytes("UTF-8");
	          
	            secretKey = new SecretKeySpec(key, "AES");//Rijndael algorithm can also used with this ie; new SecretKeySpec(key, "Rijndael");
	        }

	        catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	    }
	 
	    public String encrypt(String movies, String secret)
	    {
	        try
	        {
	            setKey(secret);
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	               
	          // return Base64.getEncoder().encodeToString(cipher.doFinal(movies.getBytes("UTF-8")));
	           byte[] encryptedVal = cipher.doFinal(movies.toString().getBytes()) ;
	            String encValue = new BASE64Encoder().encode(encryptedVal);
	            return encValue;
	        }
	        catch (Exception e)
	        {
	            System.out.println("Error while encrypting: " + e.toString());
	        }
	        return null;
	    }
	 
	    public String decrypt(String strToDecrypt, String secret)
	    {
	        try
	        {
	            setKey(secret);
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            byte[] decValue = new BASE64Decoder().decodeBuffer(strToDecrypt);
	            byte[] decode = cipher.doFinal(decValue);
	            String decrypt = new String(decode);
	            return decrypt;
	         
	          /* return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt.getBytes())));*/
	        }
	        catch (Exception e)
	        {
	            System.out.println("Error while decrypting: " + e.toString());
	        }
	        return null;
	    }
}