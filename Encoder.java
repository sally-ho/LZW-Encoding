/*
* Praise be to Ms. Kaufman and Computer Science A teachers.
* They spoke the truth when they spoke of handwritten code and BlueJ. 
* Encoder by Jasmine and Sally
* Decoder by Akseli and Lyon
* Optimization by Anneliese and Maya*/

//import all required tools
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Encoder{

	// initialize dictionary and previous, current, and previous + current variables
	private ArrayList<String> dictionary = new ArrayList<String>();
	private String previousCharacter = "";
	private char currentCharacter = 0;
	private String previousPlusCurrentCharacter = "";

	private boolean dictionaryFull = false; // this is a boolean that we can use to prevent the
											// program from throwing the full dictionary error more
											// than once

	public Encoder(){
	}

	/*
	 * Encodes a .txt file given by the String input Outputs a .txt file of the dictionary and
	 * encoded text
	 */
	public void encode(String fileName) throws IOException{
		try{
			// reading in a text file and creating print writer
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			PrintWriter pw = new PrintWriter("encoded.txt");
			String encodedContent = ""; // large string holding everything before printed
			while (br.ready()){
				currentCharacter = (char) br.read();
				previousPlusCurrentCharacter = previousCharacter + currentCharacter;
				// dictionary excludes characters 0-255 in the ascii table
				// if previousPlusCurrentCharacter is already in the dictionary or if it's in the
				// ascii table
				if(dictionary.indexOf(previousPlusCurrentCharacter) >= 0
						|| previousPlusCurrentCharacter.length() == 1){
					previousCharacter = previousPlusCurrentCharacter;
				}
				// print out value for previous character
				else{
					// if previousCharacter is already in the ascii table
					if(previousCharacter.length() == 1){
						encodedContent += ((int) previousCharacter.charAt(0) + " ");
					}
					// if only in dictionary
					else{
						encodedContent += (256 + dictionary.indexOf(previousCharacter) + " ");
					}
					// Changed --> 512 is the maximum
					if(dictionary.size() < 512){
						dictionary.add(previousPlusCurrentCharacter);
					} else if(dictionaryFull == false){
						dictionaryFull = true;
						System.out.println(
								"Dictionary is full. :( File will probably not encode or decode correctly. This will not work.");
					}
					previousCharacter = "" + currentCharacter;
				}

			}
			// edge case
			// if previous is just one character then convert it to an int
			if(previousCharacter.length() == 1){
				encodedContent += ((int) previousCharacter.charAt(0) + " ");
			}
			// if previous is a longer String, then find it in the dictionary
			else{
				encodedContent += (256 + dictionary.indexOf(previousCharacter) + " ");
			}
			// Include the dictionary at the end of the encoded file
			// Print an Ŕ to represent the end of the code and the start of the dictionary
			encodedContent += ('Ŕ');
			// print each the index of each dictionary entry, then the length of the entry so when
			// reading it in, it is easy to know when to stop, then print the entry itself
			// these are delimited by a ":" between the index and the length and a "-" between the
			// length and the entry itself
			for(int i = 0; i < dictionary.size(); i++){
				encodedContent += ("" + (i + 256) + ":" + dictionary.get(i).length() + "-"
						+ dictionary.get(i));
			}

			// actually printing stuff
			// print dictionary first
			pw.print(encodedContent.substring((encodedContent.indexOf('Ŕ') + 1),
					encodedContent.length()));

			// print an Ŕ to separate the dictionary from the encoded message
			pw.print((char) ('Ŕ'));

			// then print the rest of the stuff
			pw.print(encodedContent.substring(0, (encodedContent.indexOf('Ŕ'))));

			// close all writers and readers
			pw.close();
			br.close();
			fr.close();
		} catch(IOException e){
			System.out.println("IOException.");
		}
	}
}