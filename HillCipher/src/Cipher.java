import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Cipher {
	
	String key = "placeholder";
	int sqrKeyLength;
	int[][] keyMatrix;
	Map<Character, Integer> charToInt = new HashMap<Character, Integer>();
	Map<Integer, Character> intToChar = new HashMap<Integer, Character>();
	int mod = 29;
	
	Scanner scanner = new Scanner(System.in);
	
	public Cipher() {
		System.out.println("-----Hill Cipher Encryption & Decryption-----");
		createMapping();
		createKey();
		createKeyMatrix();
		printKeyMatrix();
		
		String input = "placeholder";
		while(!input.equals("")) {
			System.out.print("\nEnter message to convert (enter '1' to change key): ");
			input = scanner.nextLine().toUpperCase();
			if(input.equals("1")) {
				createKey();
				createKeyMatrix();
				printKeyMatrix();
				continue;
			}
			System.out.println(encrypt(input));
		}
	}
	
	public void createMapping() {
		for (int i = 65; i <= 90; i++) {
			charToInt.put((char) i, i - 65); 
			intToChar.put(i - 65, (char) i);
		}
		charToInt.put(' ', 26);
		charToInt.put('?', 27);
		charToInt.put('!', 28);
		intToChar.put(26, ' ');
		intToChar.put(27, '?');
		intToChar.put(28, '!');
	}
	
	public void createKeyMatrix() {
		keyMatrix = new int[sqrKeyLength][sqrKeyLength]; 
		int keyIndex = 0;
		for (int i = 0; i < keyMatrix.length; i++) {
			for (int j = 0; j < keyMatrix[0].length; j++) {
				if(charToInt.get(key.charAt(keyIndex)) == null) {
					System.out.println("Error: Unidentified character at key position " + keyIndex);
					System.exit(0);
				}
				keyMatrix[i][j] = charToInt.get(key.charAt(keyIndex));
				keyIndex++;
			}
		}
	}
	
	public void createKey() {
		do {
			System.out.print("\nEnter mod: ");
			try {
				mod = Integer.parseInt(scanner.nextLine());
				if(mod < 26 || mod > charToInt.size()) {
					System.out.println("Error: mod must be in the range 26 - " + charToInt.size());
				}
			}
			catch(NumberFormatException e) {
				System.out.println("Error: Invalid value given for mod");
				mod = 0;
			}
		} while( mod < 26 || mod > charToInt.size());
		System.out.println();
		
		boolean valid = false;
		while(!valid || Math.round(Math.sqrt(key.length())) != Math.sqrt(key.length()) || key.length() == 0) {
			System.out.print("Enter key: ");
			key = scanner.nextLine().toUpperCase();
			if(Math.round(Math.sqrt(key.length())) != Math.sqrt(key.length())) {
				System.out.println("Error: Key length has to be a perfect square.");
			}
			else if(key.length() == 0) {
				System.out.println("Error: Key length cannot be zero");
			}
			for (int i = 0; i < key.length(); i++) {
				if(charToInt.get(key.charAt(i)) == null || charToInt.get(key.charAt(i)) >= mod) {
					System.out.println("Error: Unidentified character at key position " + i);
					valid = false;
					break;
				}
				if(i == key.length() - 1) {
					valid = true;
				}
			}
		}
		sqrKeyLength = (int) Math.sqrt(key.length());
	}
	
	public void printKeyMatrix() {
		System.out.println("\nKey: " + key + " (mod " + mod + ")");
		System.out.println("\nGenerated Key Matrix: ");
		for (int i = 0; i < keyMatrix.length; i++) {
			System.out.print("[ ");
			for (int j = 0; j < keyMatrix[0].length; j++) {
				System.out.print(keyMatrix[i][j] + " ");
				if(keyMatrix[i][j] <= 9) {
					System.out.print(" ");
				}
			}
			System.out.println("]");
		}
	}
	
	public String encrypt(String input) {
		String cipherText = "";
		for (int i = 0; i < input.length(); i+=sqrKeyLength) {
			int[] fragment = new int[sqrKeyLength];
			for (int j = 0; j < sqrKeyLength; j++) {
				if(i + j < input.length()) {
					if(charToInt.get(input.charAt(i + j)) == null) {
						return ("Error: Unidentified character at input position " + (i+j)); 
					}
					fragment[j] = charToInt.get(input.charAt(i + j));
				}
				else {
					fragment[j] = charToInt.get(' ');
				}
			}
			int[] encryptedFragment = multiplyMatrices(fragment);
			for (int j = 0; j < sqrKeyLength; j++) {
				cipherText += intToChar.get(encryptedFragment[j]);
			}
		}
		return "Converted message: " + cipherText;
	}
	
	public int[] multiplyMatrices(int[] fragment) {
		int[] result = new int[sqrKeyLength];
		for (int i = 0; i < sqrKeyLength; i++) {
			int total = 0;
			for (int j = 0; j < sqrKeyLength; j++) {
				total += keyMatrix[i][j] * fragment[j];
			}
			result[i] = total % mod;
		}
		return result;
	}
	
	public static void main(String[] args) {
		new Cipher();
	}

}
