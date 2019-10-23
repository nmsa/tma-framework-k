/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 *** <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Admin
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.admin.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.atmosphere.tma.admin.util.Constants;
import eu.atmosphere.tma.admin.util.PropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is a Rest Controller. It handles every request made to the
 * server related to the keys.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
@CrossOrigin
@RestController
public class KeysAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeysAdminController.class);
    
    public KeyPair generateKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(PropertiesManager.getInstance().getProperty("algorithm"));
            keyPairGenerator.initialize(4096, SecureRandom.getInstance("SHA1PRNG"));
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("[ATMOSPHERE] Error generating the KeyPair", e);
        }

        return keyPair;
    }

	@GetMapping("/generatekeys")
    public ResponseEntity<ByteArrayResource> generateKeyPair(HttpServletResponse response) {

        KeyPair keyPair = generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(baos);

        byte[] data = publicKey.getEncoded();
        ZipEntry zipEntry = new ZipEntry("publicKey");
        zipEntry.setSize(data.length);
        try {
            zipOut.putNextEntry(zipEntry);
        } catch (IOException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to create a new entry on the zip output stream ", ex);
            return null;
        }
        try {
            zipOut.write(data);
        } catch (IOException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to write the bytes of the public key to the zip output stream ", ex);
            return null;
        }
        try {
            zipOut.closeEntry();
        } catch (IOException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to close the zip output stream entry ", ex);
            return null;
        }

        data = privateKey.getEncoded();
        zipEntry = new ZipEntry("privateKey");
        try {
            zipOut.putNextEntry(zipEntry);
        } catch (IOException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to create a new entry on the zip output stream ", ex);
            return null;
        }
        try {
            zipOut.write(data);
        } catch (IOException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to write the bytes of the public key to the zip output stream ", ex);
            return null;
        }
        try {
            zipOut.closeEntry();
        } catch (IOException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to close the zip output stream entry ", ex);
            return null;
        }

        try {
            zipOut.close();
        } catch (IOException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to close the zip output stream ", ex);
            return null;
        }
        ByteArrayResource resourceBytes = new ByteArrayResource(baos.toByteArray());

        return ResponseEntity
                .status(Constants.HTTPSUCESSCREATED)
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resourceBytes);
    }

}
