package br.edu.ufcg.spaghettistories.spaghettistories;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Text {
	
	public static final int TITLE_SIZE = 12;
	public static final int LINE_SIZE = 10;
	
	protected String texto;
	private String criador;
	private UUID id;
	
	public Text(String texto, String criador) {
		this.texto = texto;
		this.criador = criador;
		this.id = UUID.randomUUID();
	}

	public String getTitle() {
		return texto.substring(0,TITLE_SIZE);
	}
	
	public UUID getId() {
		return id;
	}

	public String getTexto() {
		return texto;
	}

	public String getCriador() {
		return criador;
	}
	
	public List<String> splitText() {
		LinkedList<String> lines = new LinkedList<String>();
		String line = ""; 
		int lineSize = 0;
		
		for (String word : texto.split(" ")) {
			if (lineSize == LINE_SIZE) {
				lines.add(line.trim());
				line = "";
				lineSize = 0;
			}
			line.concat(word + " ");
			lineSize++;
		}
		
		if (lineSize > 0)
			lines.add(line.trim());
		
		return lines;
	}
	
	public static String joinLines(List<String> lines) {
		String result = "";
		for (String line : lines) {
			result.concat(line + " ");
		}
		return result.trim();
	}

	public boolean containsAllLines(Text other) {
		return this.splitText().containsAll(other.splitText());
	}
}
