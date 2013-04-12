package br.edu.ufcg.spaghettistories.webui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ufcg.spaghettistories.recombining.AvailableRecombinations;

@ManagedBean
@ViewScoped
public class AvailableRecombinationsBean {
	
	private AvailableRecombinations selectedRecomb;

	public AvailableRecombinations getSelectedRecombination() {
		return selectedRecomb;
	}

	public void setSelectedRecombination(AvailableRecombinations selectedRecomb) {
		this.selectedRecomb = selectedRecomb;
	}
	
	public AvailableRecombinations[] getRecombinations() {
		return AvailableRecombinations.values();
	}
	
	public boolean isRecombinationUnselected() {
		return selectedRecomb == null;
	}

}
