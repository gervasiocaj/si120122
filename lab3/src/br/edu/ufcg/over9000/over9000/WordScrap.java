package br.edu.ufcg.over9000.over9000;

import java.io.*;
import java.util.Map;

import br.edu.ufcg.over9000.util.Constants;

public class WordScrap implements Runnable {

	private BufferedReader reader;
	private Map<String, Integer> wordMap;

	public WordScrap(File file, Map<String, Integer> wordMap) {
		try {
			this.reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo " + file.getName()
					+ "nao pode ser lido");
			e.printStackTrace();
		}
		this.wordMap = wordMap;
	}

	@Override
	public void run() {
		try {
			while (reader.ready()) 
				for (String word : reader.readLine().split(" ")) 
					if (Constants.RESERVED_WORDS.contains(word))
						synchronized (wordMap) {
							wordMap.put(word, wordMap.get(word)+1);
						}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
