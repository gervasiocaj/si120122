package br.edu.ufcg.over9000.util;

import java.util.*;

public class Constants {

	public static final Set<String> RESERVED_WORDS = new TreeSet<String>(
			Arrays.asList(("abstract continue for new switch assert default if package" +
					" synchronized boolean do goto private this break double implements" +
					" protected throw byte else import public throws case enum instanceof" +
					" return transient catch extends int short try char final interface" +
					" static void class finally long strictfp volatile const float native" +
					" super while").split(" ")));
	
}
