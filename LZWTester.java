import java.io.IOException;

public class LZWTester {
	public static void main (String [] args) throws IOException
	{
		Encoder en = new Encoder ();
		en.encode("input.txt");
	}
}
