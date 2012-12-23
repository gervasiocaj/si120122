package xptoBlog;

import java.util.LinkedList;

public class Usuario {
	
	private String nome, nick, senhaHash;
	private LinkedList<Link> links;
	
	public Usuario(String nick) {
		this.nick = nick;
		this.links = new LinkedList<Link>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNick() {
		return nick;
	}

	public String getSenhaHash() {
		return senhaHash;
	}

}
