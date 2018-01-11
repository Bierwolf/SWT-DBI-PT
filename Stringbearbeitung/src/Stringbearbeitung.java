import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Stringbearbeitung {
	public static void main(String[] args)
	{
		String fileName = "text.txt";

		try
		{
			String contents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
			
			contents = contents.replaceAll("[^A-Za-z0-9_]", " ");
			String[] words = contents.split(" ");
			System.out.println(words.length);
			Set<String> differentWords = new HashSet<String>();
			for(String s : words)
			{
				differentWords.add(s);
			}
			System.out.println(differentWords.size());
			ArrayList<String> sortedWords = new ArrayList<String>();
			for(String s : differentWords)
			{
				sortedWords.add(s);
			}
			Collections.sort(sortedWords);
			for(String s : sortedWords)
			{
				System.out.println(s);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
