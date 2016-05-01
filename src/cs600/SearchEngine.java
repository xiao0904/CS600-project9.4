package cs600;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//  P-9.4
//  Implement the simpliefied search ingine described in section 9.2.4 for the page
//  of a small web site. Use all the words in the pages of the site as index term,
//  excluding stop words such as articles, prepositions and pronouns.
//

public class SearchEngine {
	
	private final String splittingRegex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	private final String stopwordsFilename = "stopwords.txt";
	private Trie<ArrayList<Integer>> trie;
	private String [] pagesArray;
	
	// constructor
	public SearchEngine(String websiteFilename) {
		// trie for mapping words to references
		this.trie = new Trie<ArrayList<Integer>>();
		
		HashSet<String> stopWords = readAndStore(stopwordsFilename);
		
		// this file contains all the pages for a selected website
		// since I was not able to do it by code, I did it using https://www.xml-sitemaps.com
		// professor Peyrovian said it was OK to do this
		HashSet<String> temp = readAndStore(websiteFilename);
		this.pagesArray = temp.toArray(new String[0]);
		
		temp = null;
		String text;
		String word;
		String[] words;
		Iterator<String> iter = null;
		for (int pgidx = 0; pgidx < this.pagesArray.length; ++pgidx) {
			try {
				text = readUrl(this.pagesArray[pgidx]);
				text = text.toLowerCase();
				words = text.split(splittingRegex);
				
				temp = new HashSet<String>(Arrays.asList(words));
				temp.removeAll(stopWords); // remove all stop words from the page
				
				iter = temp.iterator();
				while(iter.hasNext()) {
					word = (String) iter.next();
					ArrayList<Integer> array = this.trie.search(word);
					if (array == null) {
						// insert a new word referencing the current page
						this.trie.insert(word, new ArrayList<Integer>(Arrays.asList(pgidx)));
					} else {
						array.add(pgidx);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // for loop
		System.out.println("The trie has " + this.trie.size + " entries.");
	}
	
	// read any file and put the lines in a hashset
	private HashSet<String> readAndStore(String filename) {
		HashSet<String> hash = new HashSet<String>();
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				hash.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}
	
	// read a url and return its text
	private String readUrl(String url) throws Exception {	
		Document document = Jsoup.connect(url).get();
		String text = document.body().text();
		return text;
	}
	
	public String[] search (String[] query) {
		int[] votes = new int[this.pagesArray.length];
		ArrayList<Integer> temp = null;
		for (int i = 0; i < query.length; ++i) {
			temp = this.trie.search(query[i].toLowerCase());
			if (temp != null) {
				for (int k = 0; k < temp.size(); k++) {
					votes[temp.get(k)]++;
				}
			} else {
				System.out.println("The word <" + query[i] + "> is not in any file!");
				return null;
			}
		}
		
		// answers stores the indexes of the pages
		ArrayList<String> pages = new ArrayList<String>();
		for (int p = 0; p < votes.length; ++p) {
			if (votes[p] == query.length) {
//				System.out.println(p);
				pages.add(this.pagesArray[p]);
			}
		}

		return pages.toArray(new String[0]);
	}
}
