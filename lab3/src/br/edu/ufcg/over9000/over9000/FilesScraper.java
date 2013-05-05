package br.edu.ufcg.over9000.over9000;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

import br.edu.ufcg.over9000.util.Constants;

/**
 * @author Junior
 */
public class FilesScraper {

	private static final int RESULTADOS_POR_LINHA = 4;
	private static Iterator<File> single;
	private static ThreadPoolExecutor pool;
	private static boolean hasDir = false;
	private static Map<String, Integer> mapaPalavras;
	private static List<WordScrap> arquivosLidos;
	private long tempoInicial, tempoFinal = -1;
	private int qtd;

	public FilesScraper() {
		mapaPalavras = new Hashtable<>();
		arquivosLidos = new LinkedList<WordScrap>();
		resetarMapa(mapaPalavras);
		resetarPool();
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
		((FileExplorer) single).start();
		findFiles();
	}

	/**
	 * @return Quantidade total de arquivos
	 */
	public long getFileAmount() {
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
		qtd = quantity;
		try {
			pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(qtd);
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
	public boolean hasFinishedCounting() {
		return getThreadsFinishedAmount() == getFileAmount();
		// se o numero de threads que acabaram é o número total de threads
	}
	
	/**
	 * @return Se a contagem de arquivos foi concluída
	 */
	public boolean hasFinishedScraping() {
		if (single == null)
			return false;
		return ((FileExplorer) single).hasFinished();
	}

	/**
	 * @return A quantidade de arquivos processados
	 */
	public long getThreadsFinishedAmount() {
		return pool.getCompletedTaskCount();
	}

	/**
	 * Executa a contagem das palavras nos arquivos
	 */
	public void runWordCount() {
		if (!hasDir())
			return;

		if (pool.getCompletedTaskCount() != 0)
			setThreadAmount(qtd);
		
		tempoFinal = -1;
			
		tempoInicial = System.currentTimeMillis();
		
		for (WordScrap w : arquivosLidos)
			pool.submit(w);

		ExecutorService finaliza = Executors.newSingleThreadExecutor();
		finaliza.execute(new Runnable() {
			@Override
			public void run() {
				while (!hasFinishedCounting()) {
					// TODO ver se essa bagaça funciona
				}
				pool.shutdown();
				tempoFinal = System.currentTimeMillis();
			}
		});

		generateMessage();
	}

	/**
	 * @return O resultado da contagem em texto
	 */
	public String generateMessage() {
		synchronized (mapaPalavras) {
			int pos = 0;
			String message = "";
			for (String w : mapaPalavras.keySet()) {
				if (pos == RESULTADOS_POR_LINHA) {
					message = message.concat("\n");
					pos = 0;
				}
				message = message.concat(w + " " + mapaPalavras.get(w) + "\t");
				pos++;
			}
			return message;
		}
	}
	
	/**
	 * @return O tempo (em milisegundos) gasto na contagem 
	 */
	public long getTimeSpent() {
		if (tempoFinal == -1)
			return 0;
		return tempoFinal - tempoInicial;
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
