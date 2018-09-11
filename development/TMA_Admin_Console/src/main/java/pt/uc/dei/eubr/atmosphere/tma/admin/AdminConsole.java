package pt.uc.dei.eubr.atmosphere.tma.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;

import pt.uc.dei.eubr.atmosphere.tma.admin.database.ActuatorManager;
import pt.uc.dei.eubr.atmosphere.tma.admin.database.ResourceManager;

public class AdminConsole {
	
	private static final int GENERATE_KEY_PAIR = 1;
	private static final int ENCRYPT_MESSAGE = 2;
	private static final int DECRYPT_MESSAGE = 3;
	private static final int ADD_ACTUATOR = 4;
	private static final int ADD_RESOURCE = 5;
	private static final int CONFIGURE_ACTIONS = 6;
	private static final int EXIT_OPTION = 7;

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
			System.err.println("[ATMOSPHERE] There was a problem reading your option!");
			e.printStackTrace();
		}
		
	}

	private static void readUserOption() throws IOException {
		int option = 0;
		do {
			printMenuOptions();
			String line = reader.readLine();
			try {
				option = Integer.parseInt(line);
				performAction(option);
			} catch (NumberFormatException nfe) {
				System.err.println("Not a valid number!\n");
			}	
			
		} while (option != EXIT_OPTION);
	}
	
	private static void performAction(int option) {
		switch (option) {
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
			System.out.println(encMessage);
			break;

		case DECRYPT_MESSAGE:
			System.out.println(KeyManager.decryptMessage(encMessage));
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

		case EXIT_OPTION:
			System.out.println("Bye!");
			break;

		default:
			System.out.println("Invalid Option!");
			break;
		}
	}
	
	private static void printMenuOptions() {
		System.out.println("Welcome to TMA-Admin!");
		System.out.println("---------------------");
		System.out.println("What do you want to do?");
		System.out.println(GENERATE_KEY_PAIR + " - Generate key-pair;");
		System.out.println(ENCRYPT_MESSAGE + " - Encrypt Message;");
		System.out.println(DECRYPT_MESSAGE + " - Decrypt Message;");
		System.out.println(ADD_ACTUATOR + " - Add new actuator;");
		System.out.println(ADD_RESOURCE + " - Add new resource;");
		System.out.println(CONFIGURE_ACTIONS + " - Configure actions;");
		System.out.println(EXIT_OPTION + " - Exit");
	}

	private static String getDirectory() {
		File file = null;
		do {
			try {
				System.out.println("Please, enter a valid directory: ");
				String line = reader.readLine();
				file = new File(line);
				if (file.isDirectory()) {
					return line;
				} else {
					System.err.println("The path is not valid!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (file != null && !file.isDirectory());
		return "";
	}

	private static String getPathPublicKey() {
		System.out.println("Please, enter the name of the public key: ");
		return readFromUser();
	}

	private static String getPathPrivateKey() {
		System.out.println("Please, enter the name of the private key: ");
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

	private static void addNewActuator() {
		System.out.println("Please, inform the address of the Actuator: ");
		String address = readFromUser();
		System.out.println("Please, inform the public key to be used: ");
		String pubKey = readFromUser();
		System.out.println("STILL PENDING: Validate if the endpoint is valid");
		ActuatorManager actuatorManager = new ActuatorManager();
		int actuatorId = actuatorManager.saveNewActuator(address, pubKey);
		System.out.println("The Resource Id is: " + actuatorId + "\n");
	}

	private static void addNewResource() {
		System.out.println("Please, inform the Resource Name: ");
		String name = readFromUser();
		System.out.println("Please, inform the resource type: ");
		String type = readFromUser();
		System.out.println("Please, inform the resource address: ");
		String address = readFromUser();
		ResourceManager resourceManager = new ResourceManager();
		int resourceId = resourceManager.saveNewResource(name, type, address);
		System.out.println("The Resource Id is: " + resourceId);
	}

	private static void configureActions() {
		System.out.println("Please, inform the actuatorId of the Actuator of the Actions: ");
		String actuatorIdString = readFromUser();
		Integer actuatorId = Integer.parseInt(actuatorIdString);
		System.out.println("Please, inform the path of the JSON file that contains the actions: ");
		String actionFile = readFromUser();
		System.out.println("STILL PENDING: Validate if the JSON file is valid");
		ActuatorManager actuatorManager = new ActuatorManager();
		actuatorManager.saveActions(actionFile, actuatorId);
	}
}
