package eu.atmosphere.tmaf.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.atmosphere.tmaf.admin.database.ActionManager;
import eu.atmosphere.tmaf.admin.database.ActuatorManager;
import eu.atmosphere.tmaf.admin.database.ResourceManager;

public class AdminConsole {

    // TODO: JoseP: this logger should be used for the Command Line Interface
    private static final Logger CLI_LOG = LoggerFactory.getLogger("tma-admin-cli-logger");
    //
    // TODO: JoseP: this name is not standart logging practice, is it?
    // TODO: JoseP: if you want to enforce the pattern, you should modify the log4j2.
    private static final Logger LOGGER = LoggerFactory.getLogger("[ATMOSPHERE]");
//    private static final Logger LOGGER = LoggerFactory.getLogger(AdminConsole.class);

    // TODO: JoseP: suggest refactoring for enumeration
    public enum AdminAction {
        GENERATE_KEY_PAIR,
        ENCRYPT_MESSAGE,
        DECRYPT_MESSAGE,
        ADD_ACTUATOR,
        ADD_RESOURCE,
        CONFIGURE_ACTIONS,
        DECRYPT_SAMPLE_MESSAGE,
        EXIT_OPTION;

        @Override
        public String toString() {
            return Integer.toString(ordinal());
        }

        public static AdminAction valueOf(int ordinal) {
            return (ordinal < values().length) ? values()[ordinal]
                    : EXIT_OPTION;
        }
    }

    // TODO: JoseP: suggest refactoring for enumeration, as above
//    private static final int GENERATE_KEY_PAIR = 1;
//    private static final int ENCRYPT_MESSAGE = 2;
//    private static final int DECRYPT_MESSAGE = 3;
//    private static final int ADD_ACTUATOR = 4;
//    private static final int ADD_RESOURCE = 5;
//    private static final int CONFIGURE_ACTIONS = 6;
//    private static final int DECRYPT_SAMPLE_MESSAGE = 7;
//    private static final int EXIT_OPTION = 8;
    private static byte[] encMessage = null;
    private static String message = "Minha terra tem palmeiras onde canta o sabiá / "
            + "As aves que aqui gorgeiam não gorgeiam como lá";

    private static BufferedReader reader;

    public static void main(String[] args) {

        reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            readUserOption();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOGGER.error("[ATMOSPHERE] There was a problem reading your option!");
            e.printStackTrace();
        }

    }

    private static void readUserOption() throws IOException {
        int input;
        AdminAction option = AdminAction.ADD_ACTUATOR; // we should add an harmless default option.
        do {
            printMenuOptions();
            String line = reader.readLine();
            try {
                input = Integer.parseInt(line);
                option = AdminAction.valueOf(input);
                performAction(option);
            } catch (NumberFormatException nfe) {
                CLI_LOG.error("Not a valid number! (DUP)\n"); //  TODO: JoseP: decide what to do with this boy over here
                LOGGER.error("Not a valid number!\n"); //  TODO: JoseP: decide what to do with this boy over here
            }

        } while (option != AdminAction.EXIT_OPTION);
    }

    private static void performAction(AdminAction option) { // TODO: JoseP: if you want, you can limit in method declaration

        switch (option) { // TODO: JoseP: suggested change
//        switch (option) { // TODO: JoseP: old vertion
            case GENERATE_KEY_PAIR:

                String directory = getDirectory();
                String pathPublicKey = directory + "/" + getPathPublicKey();
                String pathPrivateKey = directory + "/" + getPathPrivateKey();
                KeyPair keyPair = KeyManager.generateKeyPair();
                KeyManager.savePublicKey(keyPair.getPublic(), pathPublicKey);
                KeyManager.savePrivateKey(keyPair.getPrivate(), pathPrivateKey);
                break;

            case ENCRYPT_MESSAGE:
                encMessage = KeyManager.encryptMessage(message);
                LOGGER.info(new String(encMessage));
                break;

            case DECRYPT_MESSAGE:
                LOGGER.info(KeyManager.decryptMessage(encMessage));
                break;

            case ADD_ACTUATOR:
                addNewActuator();
                break;

            case ADD_RESOURCE:
                addNewResource();
                break;

            case CONFIGURE_ACTIONS:
                configureActions();
                break;

            case DECRYPT_SAMPLE_MESSAGE:
                String filenameMessage = getFilenameMessage();
                LOGGER.info("Decrypted: " + KeyManager.decryptMessage(filenameMessage));
                break;

            case EXIT_OPTION:
                CLI_LOG.info("Bye!");
                break;

            default:
                CLI_LOG.info("Invalid Option!");
                break;
        }
    }

    private static void printMenuOptions() {
        CLI_LOG.info("Welcome to TMA-Admin!");
        CLI_LOG.info("---------------------");
        CLI_LOG.info("What do you want to do?");
        // TODO: JoseP: suggest refactoring for enumeration, as above, as below
        CLI_LOG.info(AdminAction.GENERATE_KEY_PAIR + " - Generate key-pair;");
        CLI_LOG.info(AdminAction.ENCRYPT_MESSAGE + " - Encrypt Message;");
        CLI_LOG.info(AdminAction.DECRYPT_MESSAGE + " - Decrypt Message;");
        CLI_LOG.info(AdminAction.ADD_ACTUATOR + " - Add new actuator;");
        CLI_LOG.info(AdminAction.ADD_RESOURCE + " - Add new resource;");
        CLI_LOG.info(AdminAction.CONFIGURE_ACTIONS + " - Configure actions;");
        CLI_LOG.info(AdminAction.DECRYPT_SAMPLE_MESSAGE + " - Test Decrypt Message;");
        CLI_LOG.info(AdminAction.EXIT_OPTION + " - Exit");
    }

    private static String getDirectory() {
        File file = null;
        do {
            try {
                CLI_LOG.info("Please, enter a valid directory: ");
                String line = reader.readLine();
                file = new File(line);
                if (file.isDirectory()) {
                    return line;
                } else {
                    CLI_LOG.warn("The path is not valid!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (file != null && !file.isDirectory());
        return "";
    }

    private static String getPathPublicKey() {
        CLI_LOG.info("Please, enter the name of the public key: ");
        return readFromUser();
    }

    private static String getPathPrivateKey() {
        CLI_LOG.info("Please, enter the name of the private key: ");
        return readFromUser();
    }

    private static String readFromUser() {
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private static String getFilenameMessage() {
        return "/home/virt-atm/Documents/encrypted-response";
    }

    private static void addNewActuator() {
        CLI_LOG.info("Please, inform the address of the Actuator: ");
        String address = readFromUser();
        CLI_LOG.info("Please, inform the path of public key to be used: ");
        String pubKeyPath = readFromUser();
        CLI_LOG.info("STILL PENDING: Validate if the endpoint is valid");
        ActuatorManager actuatorManager = new ActuatorManager();
        int actuatorId = actuatorManager.saveNewActuator(address, pubKeyPath);
        CLI_LOG.info("The Actuator Id is: " + actuatorId + "\n");
    }

    private static void addNewResource() {
        CLI_LOG.info("Please, inform the Resource Name: ");
        String name = readFromUser();
        CLI_LOG.info("Please, inform the resource type: ");
        String type = readFromUser();
        CLI_LOG.info("Please, inform the resource address: ");
        String address = readFromUser();
        ResourceManager resourceManager = new ResourceManager();
        int resourceId = resourceManager.saveNewResource(name, type, address);
        CLI_LOG.info("The Resource Id is: " + resourceId);
    }

    private static void configureActions() {
        CLI_LOG.info("Please, inform the actuatorId of the Actuator of the Actions: ");
        String actuatorIdString = readFromUser();
        Integer actuatorId = Integer.parseInt(actuatorIdString);
        CLI_LOG.info("Please, inform the path of the JSON file that contains the actions: ");
        String actionFile = readFromUser();
        CLI_LOG.info("STILL PENDING: Validate if the JSON file is valid");
        ActionManager actionManager = new ActionManager();
        actionManager.saveNewActions(actionFile, actuatorId);
    }
}
