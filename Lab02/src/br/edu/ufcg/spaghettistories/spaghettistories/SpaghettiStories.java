package br.edu.ufcg.spaghettistories.spaghettistories;

import java.util.Stack;

public class SpaghettiStories {
	
	private Stack<Text> txts;
	//private List<Recombination> recombs;
	
	public SpaghettiStories() {
		this.txts = new Stack<Text>();
		//this.recombs = new LinkedList<Recombination>();
		//recombs.add(new InverseText(null));
	}
	
	public void addText(Text txt) {
		if (txt != null)
			txts.push(txt);
	}

	public Stack<Text> getTexts() {
		return txts;
	}

//	public List<Recombination> getRecombinations() {
//		return recombs;
//	}

}
