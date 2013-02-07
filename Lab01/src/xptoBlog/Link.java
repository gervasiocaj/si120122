package xptoBlog;

import java.security.InvalidParameterException;

public class Link {
	
	private String link;
	
	public Link(String link) {
		setLink(link);
	}
	
	private static boolean isValid(String link) {
		return false;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		if (Link.isValid(link))
			this.setLink(link);
		else
			throw new InvalidParameterException(link + " inválido");
	}

}
