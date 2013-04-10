package br.edu.ufcg.spaghettistories.webui;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ufcg.spaghettistories.spaghettistories.SessionController;

@ManagedBean
@ViewScoped
public class RegisterBean implements Serializable {

	private static final long serialVersionUID = -1412427102670625886L;

	public String salvar() {
		SessionController.createText(RegisterPropertiesBean.texto,
				RegisterPropertiesBean.autor);
		return "index";
	}

	public String cancelar() {
		RegisterPropertiesBean.autor = "Anonymous";
		RegisterPropertiesBean.texto = "-";
		return "index";
	}

}
