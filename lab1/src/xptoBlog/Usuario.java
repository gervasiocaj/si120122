package xptoBlog;

import java.util.LinkedList;

public class Usuario {
	
	private String nome, nick;
	private int senhaHash;
	private LinkedList<Link> links, usedLinks;
	private final int LINK_AMOUNT = 10;
	
	public Usuario(String nick, String senha) {
		this.nick = nick;
		this.senhaHash = senha.hashCode();
		this.links = new LinkedList<Link>();
		this.usedLinks = new LinkedList<Link>();
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

	public int getSenhaHash() {
		return senhaHash;
	}
	
	public boolean hasMoreLinks() {
		return !links.isEmpty();
	}
	
	public LinkedList<Link> getSomeLinks() {
		for (int i = 0; i < LINK_AMOUNT && hasMoreLinks(); i++)
			usedLinks.add(links.removeFirst());
		return usedLinks;
	}
	
	protected void close() {
		links.addAll(usedLinks);
		usedLinks.removeAll(links);
	}

}
