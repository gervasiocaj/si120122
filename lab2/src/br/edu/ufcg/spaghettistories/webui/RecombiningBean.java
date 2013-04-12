package br.edu.ufcg.spaghettistories.webui;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.*;
import org.jboss.logging.Logger;
import org.primefaces.event.FlowEvent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.edu.ufcg.spaghettistories.recombining.*;
import br.edu.ufcg.spaghettistories.spaghettistories.SessionController;
import br.edu.ufcg.spaghettistories.spaghettistories.Text;

@ManagedBean
@SessionScoped
public class RecombiningBean implements Serializable {

	private static final long serialVersionUID = -9097465625563709755L;
	private Text selectedText = null;
	private String autor = "";
	private Recombination tempText;
	private AvailableRecombinations selectedRecomb;
	private boolean isRecombSelected = false;

	private static Logger logger = Logger.getLogger(RecombiningBean.class.getName());

	public Text getSelectedText() {
		return selectedText;
	}

	public void setSelectedText(Text selectedText) {
		this.selectedText = selectedText;
	}

	public boolean isTextUnselected() {
		return selectedText == null;
	}

	public List<Text> getTexts() {
		return SessionController.getTexts();
	}

	public void chooseRecombination(AvailableRecombinations recomb) {
		switch (recomb) {
		case INVERSE_TEXT:
			tempText = new InverseText(getSelectedText());
			break;
		case RANDOM_SINGLE_TEXT:
			tempText = new RandomSingleText(getSelectedText());
			break;
		case RANDOM_MULTIPLE_TEXT:
			tempText = new RandomMultipleText(getSelectedText());
			break;
		default:
			throw new NotImplementedException();
		}
	}

	public void addLine() {
		tempText.addLine();
	}

	public String recombinationPreview() {
		return tempText.getPreviewText();
	}
	
	public boolean isRecombFull() {
		return tempText.isDone();
	}
	
	public AvailableRecombinations getSelectedRecombination() {
		return selectedRecomb;
	}
	
	public void setSelectedRecombination(AvailableRecombinations recomb) {
		this.selectedRecomb = recomb;
	}
	
	public AvailableRecombinations[] getRecombinations() {
		isRecombSelected = true;
		return AvailableRecombinations.values();
	}
	
    public String onFlowProcess(FlowEvent event) {  
        logger.info("Current wizard step:" + event.getOldStep());  
        logger.info("Next step:" + event.getNewStep());
        
        if (isRecombSelected)
        	chooseRecombination(selectedRecomb);
        
        return event.getNewStep();
    }  
    
    public String saveText() {
    	Text textoNovo = tempText.createText(autor);
    	SessionController.createText(textoNovo);
    	return "index";
    }
}

