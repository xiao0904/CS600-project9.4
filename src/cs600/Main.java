package cs600;

import java.util.Scanner;

public class Main {

	public static void print(String[] s) {
		for (int i = 0; i < s.length; ++i)
			System.out.println(s[i]);
		System.out.println();
	}
	
	public static void main(String[] args) {
		
//		String websiteFilename = "pages/saturday.txt";
//		SearchEngine engine = new SearchEngine(websiteFilename);
//		String[] query = {"words", "health"};
//		String[] pages = engine.search(query);
//		print(pages);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the Search Engine!\nType the name of the website file you wish to search");
		String websiteFilename = scanner.nextLine();
		System.out.println("Hold on while we prepare the structure...");
		SearchEngine engine = new SearchEngine("pages/"+websiteFilename+".txt");
		System.out.println("All set.\n");
		System.out.println("Type the words you want to search (separated by comma):");
		String query = scanner.nextLine();
		while (!query.equals("esc")) {
			String[] queryArray = query.split("[[,]*|[ ]*]+");
			String[] pages = engine.search(queryArray);
			System.out.println("These are the results of your query: ");
			print(pages);
			System.out.println("\nType the words you want to search (separated by comma - \"esc\" to end):");
			query = scanner.nextLine();
		}
		scanner.close();
		System.out.println("Goodbye");
		
	}

}
