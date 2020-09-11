import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Encoder {
	private ArrayList <String> dictionary = new ArrayList <String> (); 
	private String p = "";
	private char c = 0;
	private String pc = "";
	
	public Encoder ()
	{
	}

	
	public void encode (String fileName) throws IOException
	{
		try {
			//reading in a text file and creating print writer
			FileReader fr = new FileReader (fileName);
			BufferedReader br = new BufferedReader(fr);
			PrintWriter pw = new PrintWriter ("encoded.txt");
			while (br.ready())
			{
				c = (char)br.read();
				pc = p+c;
				//dictionary excludes characters 0-255 in the ascii table
				//if pc is already in the dictionary or if it's in the ascii table
				if (dictionary.indexOf(pc) >= 0 || pc.length() == 1)
				{
					p = pc;
				}
				else
				{
					
					//if p is already in the ascii table
					if ((int)p.charAt(0) <= 255)
					{
						pw.print((int)p.charAt(0));
					}
					else if (dictionary.size() <=350)
					{
						pw.print(256+dictionary.indexOf(p));
					}
					dictionary.add(pc);
					p= "" + c;
				}
				
			}
			pw.close();
			br.close();
			fr.close();
		}
		catch (IOException e)
		{
			System.out.println ("can't read");
		}
	}
}
