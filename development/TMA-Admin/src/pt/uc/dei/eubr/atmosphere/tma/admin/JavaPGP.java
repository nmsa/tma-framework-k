package pt.uc.dei.eubr.atmosphere.tma.admin;

import javax.crypto.Cipher;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * An implementation of PGP encryption (PGP = Pretty Good Privacy)
 */
public final class JavaPGP {

    /**
     * The message is created like so:
     *    - Generates a random KeyPair
     *    - Encrypt the message with the private key from the generated key pair
     *    - Encrypt the generated public key with given public key
     *
     * @param message The message to encrypt
     * @param key The key to encrypt with
     * @return The encrypted message
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] message, PublicKey key) throws GeneralSecurityException {
        KeyPair pair = generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] encryptedMessage = cipher.doFinal(message);

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedPublicKey = cipher.doFinal(pair.getPublic().getEncoded());

        ByteBuffer buffer = ByteBuffer.allocate((encryptedPublicKey.length + encryptedMessage.length) + 4);
        buffer.putInt(encryptedPublicKey.length);
        buffer.put(encryptedPublicKey);
        buffer.put(encryptedMessage);
        return buffer.array();
    }

    /**
     * The message is decrypted like so:
     *    - Read the encrypted public key
     *    - Decrypt the public key with the private key
     *    - Read the encrypted message
     *    - Use the decrypted public key to decrypt the encrypted message
     *    
     * @param message The encrypted message
     * @param key The private key
     * @return The decrypted message
     * @throws GeneralSecurityException
     */
    public static byte[] decrypt(byte[] message, PrivateKey key) throws GeneralSecurityException {
        ByteBuffer buffer = ByteBuffer.wrap(message);
        int keyLength = buffer.getInt();
        byte[] encyptedPublicKey = new byte[keyLength];
        buffer.get(encyptedPublicKey);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encodedPublicKey = cipher.doFinal(encyptedPublicKey);

        PublicKey publicKey = getPublicKey(encodedPublicKey);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] encryptedMessage = new byte[buffer.remaining()];
        buffer.get(encryptedMessage);

        return cipher.doFinal(encryptedMessage);
    }


    protected static PublicKey getPublicKey(byte[] encodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(encodedKey);
        return factory.generatePublic(encodedKeySpec);
    }

    protected static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // SHOULD IT BE 4096 ???
        keyPairGenerator.initialize(1024, SecureRandom.getInstance("SHA1PRNG"));
        return keyPairGenerator.generateKeyPair();
    }
    
    protected static byte[] generateSignature(String name, PrivateKey priv) {
    	
    	Signature dsa;
    	byte[] realSig = null;
		try {
			dsa = Signature.getInstance("SHA1withDSA", "SUN");
			
	    	dsa.initSign(priv);
	    	
	    	FileInputStream fis = new FileInputStream(name);
	    	BufferedInputStream bufin = new BufferedInputStream(fis);
	    	byte[] buffer = new byte[1024];
	    	int len;
	    	while ((len = bufin.read(buffer)) >= 0) {
	    	    dsa.update(buffer, 0, len);
	    	};
	    	bufin.close(); 	
	    	realSig = dsa.sign();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return realSig;
    }


}