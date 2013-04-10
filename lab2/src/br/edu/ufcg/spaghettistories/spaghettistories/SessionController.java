package br.edu.ufcg.spaghettistories.spaghettistories;

import java.util.Collection;

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

	public static Collection<Text> getTexts() {
		return sys.getTexts();
	}

	public static void createText(String texto, String autor) {
		sys.addText(new Text(texto, autor));
	}

}
