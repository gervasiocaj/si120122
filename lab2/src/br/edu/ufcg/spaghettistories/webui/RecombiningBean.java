package br.edu.ufcg.spaghettistories.webui;

import java.io.Serializable;
import java.util.Collection;
import javax.faces.bean.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.edu.ufcg.spaghettistories.recombining.*;
import br.edu.ufcg.spaghettistories.spaghettistories.SessionController;
import br.edu.ufcg.spaghettistories.spaghettistories.Text;

@ManagedBean
@SessionScoped
public class RecombiningBean implements Serializable {

	private static final long serialVersionUID = -9097465625563709755L;

	private Text selectedText = null;
	private Recombination selectedRecombination;

	public Text getSelectedText() {
		return selectedText;
	}

	public void setSelectedText(Text selectedText) {
		this.selectedText = selectedText;
	}

	public boolean isTextUnselected() {
		return selectedText == null;
	}

	public Collection<Text> getTexts() {
		return SessionController.getTexts();
	}

	public void chooseRecombination(AvailableRecombinations recomb) {
		switch (recomb) {
		case INVERSE_TEXT:
			selectedRecombination = new InverseText(getSelectedText());
			break;
		case RANDOM_SINGLE_TEXT:
			selectedRecombination = new RandomSingleText(getSelectedText());
			break;
		case RANDOM_MULTIPLE_TEXT:
			selectedRecombination = new RandomMultipleText(getSelectedText());
			break;
		default:
			throw new NotImplementedException();
		}
	}

	public void addLine() {
		// if (selectedRecombination == null)
		// throw ex
		selectedRecombination.addLine();
	}

	public String getRecombinationText() {
		return selectedRecombination.getIncompleteText();
	}
}
