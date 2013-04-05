package br.edu.ufcg.spaghettistories.recombining;

import java.util.List;

import br.edu.ufcg.spaghettistories.spaghettistories.Text;

public class InverseText implements Recombination {

	private Text original;
	private List<String> temp, extra;
	private int position;

	public InverseText(Text original) {
		this.original = original;
		this.temp = original.splitText();
		this.position = temp.size();
	}

	@Override
	public void addLine() {
		extra.add(0, temp.get(position));
		position--;
	}

	@Override
	public boolean isDone() {
		return position == 0;
	}

	@Override
	public Text createText(String criador) {
		return new Text(original.getTexto() + " " + Text.joinLines(extra),
				criador);
	}

}
