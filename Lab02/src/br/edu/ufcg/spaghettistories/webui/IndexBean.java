package br.edu.ufcg.spaghettistories.webui;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.*;
import br.edu.ufcg.spaghettistories.spaghettistories.SessionController;
import br.edu.ufcg.spaghettistories.spaghettistories.Text;

@ManagedBean
@SessionScoped
public class IndexBean implements Serializable {

	private static final long serialVersionUID = -897641479914519748L;

	public IndexBean() {
		SessionController.init();
	}

	public List<Text> getTexts() {
		return SessionController.getTexts();
	}

	public boolean isEmpty() {
		return SessionController.getTexts().isEmpty();
	}

}
