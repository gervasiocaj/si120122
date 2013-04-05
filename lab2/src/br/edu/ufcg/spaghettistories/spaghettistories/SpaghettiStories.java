package br.edu.ufcg.spaghettistories.spaghettistories;

import java.util.Collection;
import java.util.Stack;

public class SpaghettiStories {
	
	private Stack<Text> txts;
	
	public SpaghettiStories() {
		txts = new Stack<Text>();
	}
	
	public void addText(Text txt) {
		if (txt != null)
			txts.push(txt);
	}

	public Collection<Text> getTexts() {
		return txts;
	}

}
