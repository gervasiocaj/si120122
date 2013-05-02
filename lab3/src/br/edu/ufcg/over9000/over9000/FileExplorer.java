package br.edu.ufcg.over9000.over9000;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class FileExplorer implements Iterator<File> {

	private LinkedList<File> files;
	private int position;
	private final String DESIRED_EXTENSION = ".java";

	public FileExplorer(File directory) {
		this.files = new LinkedList<File>();
		this.position = -1;
		explore(directory);
	}

	private void explore(File arch) {
		if (arch.isFile()) {
			if (arch.getAbsolutePath().endsWith(DESIRED_EXTENSION))
				files.add(arch);
		} else {
			for (File f : arch.listFiles())
				explore(f);
		}
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
}