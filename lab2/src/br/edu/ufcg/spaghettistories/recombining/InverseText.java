package br.edu.ufcg.spaghettistories.recombining;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import br.edu.ufcg.spaghettistories.spaghettistories.Text;

public class InverseText implements Recombination {

	private List<String> temp, extra;
	private int position;

	public InverseText(Text original) {
		this.temp = original.splitText();
		this.extra = new LinkedList<String>();
		this.position = temp.size();
	}

	@Override
	public void addLine() {
		if (isDone())
			throw new NoSuchElementException();
		extra.add(0, temp.get(position));
		position--;
	}

	@Override
	public boolean isDone() {
		return position == 0;
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
