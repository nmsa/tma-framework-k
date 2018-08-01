package pt.uc.dei.eubr.atmosphere.tma.admin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyManager {
	
	public static KeyPair generateKeyPair() {
		KeyPair keyPair = null;
		try {
			keyPair = JavaPGP.generateKeyPair();
			System.out.println(keyPair);
			System.out.println(keyPair.getPublic());
			System.out.println(keyPair.getPrivate());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("[ATMOSPHERE] NoSuchAlgorithmException");
			e.printStackTrace();
		}
		
		return keyPair;
	}

	/*static void savePublicKey(PublicKey pub) {
		// TODO Auto-generated method stub
		// save the public key in a file
		byte[] key = pub.getEncoded();
		FileOutputStream keyfos;
		try {
			keyfos = new FileOutputStream("josepk");
			keyfos.write(key);
			keyfos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	static void savePrivateKey(PrivateKey privateKey) {
		/* save the public key in a file */
    	byte[] key = privateKey.getEncoded();
    	FileOutputStream keyfos;
		try {
			keyfos = new FileOutputStream("/home/virt-atm/my_private_key");
			keyfos.write(key);
	    	keyfos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void savePublicKey(PublicKey publicKey) {
		/* save the public key in a file */
    	byte[] key = publicKey.getEncoded();
    	FileOutputStream keyfos;
		try {
			keyfos = new FileOutputStream("/home/virt-atm/my_public_key");
			keyfos.write(key);
	    	keyfos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
