package br.edu.ufcg.over9000.over9000;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import br.edu.ufcg.over9000.util.Constants;

/**
 * @author Junior
 */
public class FilesScraper {

	private static Iterator<File> single;
	private static ThreadPoolExecutor pool;
	private static boolean hasDir = false;
	private static Map<String, Integer> mapaPalavras;
	private static List<WordScrap> arquivosLidos;

	public FilesScraper() {
		mapaPalavras = new Hashtable<>();
		arquivosLidos = new LinkedList<WordScrap>();
		resetarMapa(mapaPalavras);
		resetarPool();
	}

	// public static void main(String[] args) {
	// new FilesScraper();
	// setDirectory("C:/Users/Junior/git");
	// setThreadAmount(4);
	// run();
	// }

	/**
	 * Indica o diretório a ser explorado
	 * 
	 * @param dir
	 *            - O diretório em formato de String
	 */
	public void setDirectory(String dir) {
		setDirectory(new File(dir));
	}

	/**
	 * Indica o diretório a ser explorado
	 * 
	 * @param dir
	 *            - O diretório desejado
	 */
	public void setDirectory(File dir) {
		single = new FileExplorer(dir);
		hasDir = true;
		findFiles();
	}

	/**
	 * @return Quantidade total de arquivos
	 */
	public int getFileAmount() {
		if (hasDir)
			return ((FileExplorer) single).getFileAmount();
		return 0;
	}

	/**
	 * @param quantity
	 *            Quantidade de threads a serem criadas (indefinidas se quantity
	 *            < 1)
	 */
	public void setThreadAmount(int quantity) {
		try {
			pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(quantity);
			pool.prestartAllCoreThreads();
		} catch (Exception e) {
			resetarPool();
		}
	}

	/**
	 * @return Se o diretório já foi especificado
	 */
	public boolean hasDir() {
		return hasDir;
	}

	/**
	 * @return Se todos os arquivos foram lidos
	 */
	public boolean hasFinished() {
		return getThreadsFinishedAmount() == getFileAmount();
		// se o numero de threads que acabaram é o número total de threads
	}

	public long getThreadsFinishedAmount() {
		return pool.getCompletedTaskCount();
	}

	/**
	 * Executa a contagem das palavras nos arquivos
	 */
	public void runWordCount() {
		if (!hasDir())
			return;

		for (WordScrap w : arquivosLidos)
			pool.submit(w);

		ExecutorService finaliza = Executors.newSingleThreadExecutor();
		finaliza.execute(new Runnable() {
			@Override
			public void run() {
				while (!hasFinished()) {
					// TODO ver se essa bagaça funciona
				}
				pool.shutdown();
			}
		});

		for (String w : mapaPalavras.keySet())
			// TODO remover syso
			System.out.println(w + " " + mapaPalavras.get(w));
	}

	private void findFiles() {
		while (single.hasNext()) {
			arquivosLidos.add(new WordScrap(single.next(), mapaPalavras));
		}
	}

	protected void resetarMapa(Map<String, Integer> mapaPalavras) {
		for (String word : Constants.RESERVED_WORDS) {
			mapaPalavras.put(word, 0);
		}
	}

	private void resetarPool() {
		pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	}

}
