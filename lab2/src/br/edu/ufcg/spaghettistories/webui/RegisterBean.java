package br.edu.ufcg.spaghettistories.webui;

import java.io.Serializable;
import javax.faces.bean.*;
import br.edu.ufcg.spaghettistories.spaghettistories.SessionController;

@ManagedBean
@SessionScoped
public class RegisterBean implements Serializable {

	private static final long serialVersionUID = -1412427102670625886L;
	private static String autor = "", texto = "";

	public String salvar() {
		SessionController.createText(texto, autor);
		return "index";
	}

	public String cancelar() {
		autor = "";
		texto = "";
		return "index";
	}
	

	public String getAutor() {
		return autor;
	}

	public void setAutor(String aut) {
		autor = aut;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String txt) {
		texto = txt;
	}

}
