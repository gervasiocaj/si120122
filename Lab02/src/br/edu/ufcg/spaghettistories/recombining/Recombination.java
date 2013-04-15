package br.edu.ufcg.spaghettistories.recombining;

import br.edu.ufcg.spaghettistories.spaghettistories.Text;

public interface Recombination {
	
	public void addLine();
	public boolean isEmpty();
	public boolean isDone();
	public Text createText(String criador);
	public String getPreviewText();

}
