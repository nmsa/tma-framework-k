package pt.uc.dei.eubr.atmosphere.tma.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;

public class AdminConsole {
	
	private static final int GENERATE_KEY_PAIR = 1;
	private static final int ENCRYPT_MESSAGE = 2;
	private static final int DECRYPT_MESSAGE = 3;
	private static final int EXIT_OPTION = 4;

	private static byte[] encMessage = null;
	private static String message = "Minha terra tem palmeiras onde canta o sabiá / "
			+ "As aves que aqui gorgeiam não gorgeiam como lá";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try {
			readUserOption(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("[ATMOSPHERE] There was a problem reading your option!");
			e.printStackTrace();
		}
		
	}

	private static void readUserOption(BufferedReader reader) throws IOException {
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

			String pathPublicKey = getPathPublicKey();
			String pathPrivateKey = getPathPrivateKey();
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
		System.out.println(EXIT_OPTION + " - Exit");
	}

	private static String getPathPublicKey() {
		return "/Users/josealexandredabruzzopereira/Projects/tma-framework-k/public";
	}

	private static String getPathPrivateKey() {
		return "/Users/josealexandredabruzzopereira/Projects/tma-framework-k/private";
	}

}
