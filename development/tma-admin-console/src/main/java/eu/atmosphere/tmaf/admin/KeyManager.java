package eu.atmosphere.tmaf.admin;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;
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
            FileUtils.writeByteArrayToFile(new File(Paths.get("") + "encrypted-message"), cipherText);
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

    private static PrivateKey getPrivateKey(String filenamePrivKey) {
        try {
            File privKeyFile = new File(filenamePrivKey);

            // read private key DER file
            DataInputStream dis = new DataInputStream(new FileInputStream(privKeyFile));
            byte[] privKeyBytes = new byte[(int) privKeyFile.length()];
            dis.read(privKeyBytes);
            dis.close();

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

            // decode private key
            PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privKeyBytes);
            RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privSpec);

            return privKey;
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private static PublicKey getPublicKey(String filenamePubKey) {
        try {
            File pubKeyFile = new File(filenamePubKey);

            DataInputStream dis = new DataInputStream(new FileInputStream(pubKeyFile));
            byte[] pubKeyBytes = new byte[(int) pubKeyFile.length()];
            dis.readFully(pubKeyBytes);
            dis.close();

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

            // decode public key
            X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubKeyBytes);
            RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubSpec);

            return pubKey;
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    static String decryptMessage(String filename) {
        byte[] encMessage = null;
        String decryptedMessage = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String st;
            int i = 0;
            while ((st = br.readLine()) != null) {
                if (i == 0) {
                    // data
                    encMessage = Base64.getDecoder().decode(st);
                    System.out.println("byteArray.length: " + encMessage.length);
                    decryptedMessage = decrypt(encMessage, getPrivateKey(Paths.get("") + "priv-key-execute"));
                }
                if (i == 1) {
                    // signature
                    System.out.println("signature: " + st);
                    System.out.println(verifySignature(decryptedMessage.getBytes(UTF_8), Base64.getDecoder().decode(st), getPublicKey(Paths.get("") + "pub-key-again")));
                }

                i++;
            }
            br.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return decryptedMessage;
    }

    //Method for signature verification that initializes with the Public Key,
    //updates the data to be verified and then verifies them using the signature
    private static boolean verifySignature(byte[] data, byte[] signature, PublicKey key) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(key);
        sig.update(data);

        return sig.verify(signature);
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
