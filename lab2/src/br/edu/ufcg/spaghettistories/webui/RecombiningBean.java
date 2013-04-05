package br.edu.ufcg.spaghettistories.webui;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.edu.ufcg.spaghettistories.spaghettistories.Text;

@ManagedBean
@SessionScoped
public class RecombiningBean implements Serializable{

	private static final long serialVersionUID = -9097465625563709755L;
	
	private Text selectedText;

	public Text getSelectedText() {
		return selectedText;
	}

	public void setSelectedText(Text selectedText) {
		this.selectedText = selectedText;
	}

}
