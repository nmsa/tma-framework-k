package eu.atmosphere.tmaf.admin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyManager.class);
    private static final Logger CLI_LOG = LoggerFactory.getLogger("tma-admin-cli-logger");

    static PublicKey pub = null;
    static PrivateKey priv = null;

    /**
     * String to hold name of the encryption algorithm.
     */
    public static final String ALGORITHM = "RSA";

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static KeyPair generateKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(2048, SecureRandom.getInstance("SHA1PRNG"));
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return keyPair;
    }

    static void savePrivateKey(PrivateKey privateKey, String pathPrivateKey) {
        /* save the public key in a file */
        byte[] key = privateKey.getEncoded();
        FileOutputStream keyfos;
        try {
            keyfos = new FileOutputStream(pathPrivateKey);
            keyfos.write(key);
            priv = privateKey;
            keyfos.close();
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    static void savePublicKey(PublicKey publicKey, String pathPublicKey) {
        /* save the public key in a file */
        byte[] key = publicKey.getEncoded();
        FileOutputStream keyfos;
        try {
            keyfos = new FileOutputStream(pathPublicKey);
            keyfos.write(key);
            pub = publicKey;
            keyfos.close();
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    static byte[] encryptMessage(String message) {
        byte[] result = null;
        if (pub == null) {
            CLI_LOG.error("You need to define a key before encrypting the message!\n");
        } else  {
            result = encrypt(message, pub);
            LOGGER.info(new String(result));
        }
        return result;
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
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return cipherText;
    }

    static String decryptMessage(byte[] encMessage) {
        String result = "";
        if (priv == null) {
            CLI_LOG.error("You need to define a key before decrypting the message!\n");
        } else  {
            result = decrypt(encMessage, priv);
            LOGGER.info(result);
        }
        return result;
    }

    public static String decrypt(byte[] text, Key key) {
        byte[] dectyptedText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new String(dectyptedText);
    }
}
