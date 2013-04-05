package br.edu.ufcg.spaghettistories.webui;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.edu.ufcg.spaghettistories.spaghettistories.SessionController;

@ManagedBean
@SessionScoped
public class RegisterBean implements Serializable {

	private static final long serialVersionUID = -1412427102670625886L;

	public String salvar(String txt, String criador) {
			if (criador.isEmpty())
				criador = "Anonymous";
			SessionController.createText(txt,criador);
			return "index";
	}

	public String cancelar() {
		RegisterPropertiesBean.autor = "Anonymous";
		RegisterPropertiesBean.texto = "-";
		return "index";
	}

}
