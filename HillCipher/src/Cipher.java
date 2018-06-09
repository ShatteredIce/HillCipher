import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Cipher {
	
	String key = "GYBNQKURPASDFASS";
	int sqrKeyLength = 0;
	int[][] keyMatrix;
	Map<Character, Integer> charToInt = new HashMap<Character, Integer>();
	Map<Integer, Character> intToChar = new HashMap<Integer, Character>();
	
	Scanner scanner = new Scanner(System.in);
	
	public Cipher() {
		if(Math.round(Math.sqrt(key.length())) != Math.sqrt(key.length()) || key.length() == 0) {
			System.out.println("Invalid Key! Key length has to be a perfect square.");
			System.exit(0);
		}
		sqrKeyLength = (int) Math.sqrt(key.length());
		createMapping();
		createKeyMatrix();
		printKeyMatrix();
		System.out.println(encrypt(scanner.nextLine()));
	}
	
	public void createMapping() {
		for (int i = 65; i <= 90; i++) {
			charToInt.put((char) i, i - 65); 
			intToChar.put(i - 65, (char) i);
		}
		charToInt.put(' ', 27);
		intToChar.put(27, ' ');
	}
	
	public void createKeyMatrix() {
		keyMatrix = new int[sqrKeyLength][sqrKeyLength]; 
		int keyIndex = 0;
		for (int i = 0; i < keyMatrix.length; i++) {
			for (int j = 0; j < keyMatrix[0].length; j++) {
				if(charToInt.get(key.charAt(keyIndex)) == null) {
					System.out.println("Unidentified character at key position " + keyIndex);
					return;
				}
				keyMatrix[i][j] = charToInt.get(key.charAt(keyIndex));
				keyIndex++;
			}
		}
	}
	
	public void printKeyMatrix() {
		for (int i = 0; i < keyMatrix.length; i++) {
			System.out.print("[ ");
			for (int j = 0; j < keyMatrix[0].length; j++) {
				System.out.print(keyMatrix[i][j] + " ");
				if(keyMatrix[i][j] < 9) {
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
		return cipherText;
	}
	
	public int[] multiplyMatrices(int[] fragment) {
		int[] result = new int[sqrKeyLength];
		for (int i = 0; i < sqrKeyLength; i++) {
			int total = 0;
			for (int j = 0; j < sqrKeyLength; j++) {
				total += keyMatrix[i][j] * fragment[j];
			}
			result[i] = total % 27;
		}
		return result;
	}
	
	public static void main(String[] args) {
		new Cipher();
	}

}
