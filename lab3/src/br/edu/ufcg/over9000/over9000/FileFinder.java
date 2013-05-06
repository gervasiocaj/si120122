package br.edu.ufcg.over9000.over9000;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class FileFinder implements Iterator<File> {

	private volatile LinkedList<File> files;
	private int position;
	private final String DESIRED_EXTENSION = ".java";
	private final File dir;
	private volatile boolean finished = false;

	public FileFinder(File directory) {
		this.files = new LinkedList<File>();
		this.dir = directory;
		this.position = -1;
	}
	
	public boolean hasFinished() {
		return finished;
	}

	private void explore(File arch) {
		if (arch.isFile()) { // arquivo
			if (arch.getAbsolutePath().endsWith(DESIRED_EXTENSION))
				files.add(arch);
		} else { // pasta
			try {
				for (File f : arch.listFiles())
					explore(f);
			} catch (Exception e) { // no listFiles
				System.err.println("Erro ao acessar a pasta: " + arch.getAbsolutePath());
			}
		}
		
		if (arch.equals(dir))
			finished = true;
	}

	public int getFileAmount() {
		return files.size();
	}

	public int getCurrentFileNumber() {
		return position + 1;
	}

	@Override
	public synchronized boolean hasNext() {
		return position + 1 < files.size();
	}

	@Override
	public synchronized File next() {
		position++;
		return files.get(position);
	}

	@Override
	public void remove() {
		return;
	}

	public void start() {
		explore(dir);
	}
}