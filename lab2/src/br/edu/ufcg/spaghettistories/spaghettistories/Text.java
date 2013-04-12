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
		if (criador == null || criador.isEmpty())
			criador = "Anonymous";
		this.texto = texto;
		this.fillLine();
		this.id = UUID.randomUUID();
		this.criador = criador;
	}
	
	private void fillLine() {
		List<String> temp = splitText();
		String temp2 = temp.get(temp.size() - 1);
		
		while (temp2.length() < LINE_SIZE) {
			this.texto.concat(" ");
			temp2.concat(" ");
		}
	}

	public String getTitle() {
		String[] temp = texto.split(" ");
		String result = "";
		
		if (temp.length <= TITLE_SIZE)
			return texto;
		
		for (int i = 0; i < TITLE_SIZE; i++)
			result.concat(temp[i]);
		
		return result;
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
