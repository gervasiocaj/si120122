package xptoBlog;

import java.util.LinkedList;
import java.util.List;

public class Blog {
	
	private Usuario user;
	private List<Usuario> database;
	
	public Blog() {
		this.database = new LinkedList<Usuario>(); 
	}
	
	public Blog(List<Usuario> database){
		this.database = database;
	}
	
	public void login(String nick, String password) throws Exception {
		for (Usuario u : database)
			if (u.getNick().equals(nick))
				if (u.getSenhaHash() == password.hashCode())
					user = u;
		if (user == null)
			throw new Exception("Usuário ou senha incorretos");
	}
	
}
