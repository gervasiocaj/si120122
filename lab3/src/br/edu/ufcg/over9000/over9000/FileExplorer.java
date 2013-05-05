package br.edu.ufcg.over9000.over9000;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class FileExplorer implements Iterator<File> {

	private LinkedList<File> files;
	private int position;
	private final String DESIRED_EXTENSION = ".java";
	private final File dir;
	private boolean finished = false;

	public FileExplorer(File directory) {
		this.files = new LinkedList<File>();
		this.dir = directory;
		this.position = -1;
		finished = true;
	}
	
	public boolean hasFinished() {
		return finished;
	}

	private void explore(File arch) {
		if (arch.isFile()) {
			if (arch.getAbsolutePath().endsWith(DESIRED_EXTENSION))
				files.add(arch);
		} else {
			try {
				for (File f : arch.listFiles())
					explore(f);
			} catch (Exception e) {
				System.err.println("Erro ao acessar a pasta: " + arch.getAbsolutePath());
			}
		}
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