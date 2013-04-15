package br.edu.ufcg.spaghettistories.recombining;

import java.util.List;
import java.util.Random;

import br.edu.ufcg.spaghettistories.spaghettistories.Text;

public class RandomMultipleText implements Recombination {
	
	private List<String> temp, extra;
	private Random random;
	
	public RandomMultipleText(Text original) {
		this.temp = original.splitText();
		this.random = new Random();
	}

	@Override
	public void addLine() {
		int position = random.nextInt(temp.size());
		extra.add(temp.get(position));
	}

	@Override
	public boolean isDone() {
		return false; // Não há limitação de tamanho nesta recombinação 
	}

	@Override
	public Text createText(String criador) {
		return new Text(Text.joinLines(extra), criador);
	}

	@Override
	public boolean isEmpty() {
		return extra.isEmpty();
	}
	
	@Override
	public String getPreviewText() {
		return Text.joinLines(extra);
	}

}
