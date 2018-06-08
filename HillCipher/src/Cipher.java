import java.util.HashMap;
import java.util.Map;

public class Cipher {
	
	String key = "GYBNQKURP";
	int sqrKeyLength = 0;
	String plainText = "";
	String cipherText = "";
	int[][] keyMatrix;
	Map<Character, Integer> map = new HashMap<Character, Integer>(); 
	
	public Cipher() {
		if(Math.round(Math.sqrt(key.length())) != Math.sqrt(key.length()) || key.length() == 0) {
			System.out.println("Invalid Key! Key length has to be a perfect square.");
			System.exit(0);
		}
		sqrKeyLength = (int) Math.sqrt(key.length());
		createMapping();
		createKeyMatrix();
		printKeyMatrix();
	}
	
	public void createMapping() {
		for (int i = 65; i <= 90; i++) {
			map.put((char) i, i - 65); 
		}
		map.put(' ', 27);
	}
	
	public void createKeyMatrix() {
		keyMatrix = new int[sqrKeyLength][sqrKeyLength]; 
		int keyIndex = 0;
		for (int i = 0; i < keyMatrix.length; i++) {
			for (int j = 0; j < keyMatrix[0].length; j++) {
				if(map.get(key.charAt(keyIndex)) == null) {
					System.out.println("Unidentified character at key position " + keyIndex);
					return;
				}
				keyMatrix[i][j] = map.get(key.charAt(keyIndex));
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
	
	public void encrypt() {
		
	}
	
	public static void main(String[] args) {
		new Cipher();
	}

}
