package br.edu.ufcg.spaghettistories.recombining;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import br.edu.ufcg.spaghettistories.spaghettistories.Text;

public class RandomSingleText implements Recombination {

	private List<String> temp, extra;
	private int position;

	public RandomSingleText(Text original) {
		this.extra = new LinkedList<String>();
		this.temp = original.splitText();
		Collections.shuffle(temp);
		this.position = 0;
	}

	@Override
	public void addLine() {
		if (isDone())
			throw new NoSuchElementException();
		extra.add(temp.get(position));
		position++;
	}

	@Override
	public boolean isDone() {
		return position + 1 == temp.size();
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
	public String getIncompleteText() {
		return Text.joinLines(extra);
	}

}
