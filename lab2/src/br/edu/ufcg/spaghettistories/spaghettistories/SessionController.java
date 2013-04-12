package br.edu.ufcg.spaghettistories.spaghettistories;

import java.util.Stack;

import br.edu.ufcg.spaghettistories.recombining.Recombination;

public class SessionController {

	private static SpaghettiStories sys;
	private static boolean hasStarted = false;

	public SessionController() {
		sys = new SpaghettiStories();
	}

	public static void init() {
		if (!hasStarted) {
			new SessionController();
			hasStarted = true;
		}
	}

	public static Stack<Text> getTexts() {
		return sys.getTexts();
	}

	public static void createText(String texto, String autor) {
		sys.addText(new Text(texto, autor));
	}
	
	public static void recombineText(Recombination r, String autor) {
		sys.addText(r.createText(autor));
	}

}
