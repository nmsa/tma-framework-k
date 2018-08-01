package pt.uc.dei.eubr.atmosphere.tma.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;

public class AdminConsole {
	
	private static final int GENERATE_KEY_PAIR = 1;
	private static final int EXIT_OPTION = 2;

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
			
			KeyPair keyPair = KeyManager.generateKeyPair();
			//JavaPGP.generateSignature("/home/virt-atm/", keyPair.getPrivate());
			KeyManager.savePublicKey(keyPair.getPublic());
			KeyManager.savePrivateKey(keyPair.getPrivate());
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
		System.out.println(EXIT_OPTION + " - Exit");
	}

}
