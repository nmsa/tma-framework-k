package pt.uc.dei.eubr.atmosphere.tma.admin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class KeyManager {

    public static final String directory =
            "/Users/josealexandredabruzzopereira/Projects/tma-framework-k/";

    static PublicKey pub = null;
    static PrivateKey priv = null;

    /**
     * String to hold name of the encryption algorithm.
     */
    public static final String ALGORITHM = "RSA";

    public static KeyPair generateKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(4096, SecureRandom.getInstance("SHA1PRNG"));
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("[ATMOSPHERE] NoSuchAlgorithmException");
            e.printStackTrace();
        }

        return keyPair;
    }

    static void savePrivateKey(PrivateKey privateKey) {
        /* save the public key in a file */
        byte[] key = privateKey.getEncoded();
        FileOutputStream keyfos;
        try {
            keyfos = new FileOutputStream(directory + "my_private_key");
            keyfos.write(key);
            priv = privateKey;
            keyfos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void savePublicKey(PublicKey publicKey) {
        /* save the public key in a file */
        byte[] key = publicKey.getEncoded();
        FileOutputStream keyfos;
        try {
            keyfos = new FileOutputStream(directory + "my_public_key");
            keyfos.write(key);
            pub = publicKey;
            keyfos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

     static byte[] encryptMessage(String message) {
         return encrypt(message, pub);
     }

     public static byte[] encrypt(String text, PublicKey key) {
         byte[] cipherText = null;
         try {
             // get an RSA cipher object and print the provider
             final Cipher cipher = Cipher.getInstance(ALGORITHM);
             // encrypt the plain text using the public key
             cipher.init(Cipher.ENCRYPT_MODE, key);
             cipherText = cipher.doFinal(text.getBytes());
           } catch (Exception e) {
             e.printStackTrace();
           }
           return cipherText;
    }

     static String decryptMessage(byte[] encMessage) {
         return decrypt(encMessage, priv);
     }

    public static String decrypt(byte[] text, PrivateKey key) {
        byte[] dectyptedText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);

         } catch (Exception ex) {
             ex.printStackTrace();
         }
         return new String(dectyptedText);
       }
}
