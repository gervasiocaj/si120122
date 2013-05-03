package br.edu.ufcg.over9000.over9000;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.*;

import br.edu.ufcg.over9000.util.Constants;

public class FilesScraper {

	private static Iterator<File> single;
	private static ThreadPoolExecutor pool;
	private static boolean hasThreadAmount = false, hasDir = false;
	private static Map<String, Integer> mapaPalavras;
	
	public FilesScraper() {
		mapaPalavras = new Hashtable<>();
		resetarMapa(mapaPalavras);
	}

//	public static void main(String[] args) {
//		new FilesScraper();
//		setDirectory("C:/Users/Junior/git");
//		setThreadAmount(4);
//		run();
//	}

	public void setDirectory(String dir) {
		try {
			single = new FileExplorer(new File(dir));
			hasDir = true;
		} catch (Exception e) {
			// TODO tratar diretorio invalido
		}
	}
	
	public void setDirectory(File dir) {
		try {
			single = new FileExplorer(dir);
			hasDir = true;
		} catch (Exception e) {
			// TODO tratar diretorio invalido
		}
	}
	
	public void setThreadAmount(int quantity) {
		try {
			pool = (ThreadPoolExecutor) Executors
					.newFixedThreadPool(quantity);
			pool.prestartAllCoreThreads();
			hasThreadAmount = true;
		} catch (Exception e) {
			// TODO tratar qtd invalida
		}
	}
	
	public boolean isReady() {
		return (hasDir && hasThreadAmount);
	}
	
	public boolean hasDir() {
		return hasDir;
	}
	
	public boolean hasThreadAmount() {
		return hasThreadAmount;
	}
	
	public void run() {
		if (!isReady())
			return;
		
		while (single.hasNext()) {
			WordScrap a = new WordScrap(single.next(), mapaPalavras);
			pool.submit(a);
		}
		
		// TODO consertar wait till end
		try {
			pool.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (String w : mapaPalavras.keySet())
			System.out.println(w + " " + mapaPalavras.get(w));
	}
	
	protected void resetarMapa(Map<String, Integer> mapaPalavras) {
		for (String word : Constants.RESERVED_WORDS) {
			mapaPalavras.put(word, 0);
		}
	}

}
