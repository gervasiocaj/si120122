package br.edu.ufcg.spaghettistories.webui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RegisterPropertiesBean implements Serializable{

	private static final long serialVersionUID = 5641777580929596547L;
	protected static String autor = "Anonymous", texto = "-";

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
