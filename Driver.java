package lse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Enter txt file to feed noise words");
		Scanner userInput = new Scanner(System.in);
		String userSubmittedTxtFile = userInput.next();
		Scanner noiseWordReader = new Scanner(new File(userSubmittedTxtFile));
		LittleSearchEngine mySearch = new LittleSearchEngine();
		
		while(noiseWordReader.hasNext()) {
			mySearch.noiseWords.add(noiseWordReader.next());
		}
		
		while(true) {
			String input = userInput.next();
			System.out.println("Output: " + mySearch.getKeyword(input));
		}
		
	}

}
