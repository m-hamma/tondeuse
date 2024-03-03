package com.mhamma.tondeuse.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.mhamma.tondeuse.enums.Orientation;
import com.mhamma.tondeuse.model.Tondeuse;
import com.mhamma.tondeuse.service.TondeuseService;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * Classe qui implemente TondeuseService.
 */
public class TondeuseServiceImpl implements TondeuseService {
	/* Logger **/
	// private static final Log log = LogFactory.getLog(TondeuseServiceImpl.class);

	public String piloterTondeuse() {
		String filePath = "C:\\fichiers\\tondeuses.txt";
		String retour = "";
		try (FileReader fileReader = new FileReader(filePath);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			String line;
			int compteur = 0;
			while ((line = bufferedReader.readLine()) != null) {
				final Tondeuse tondeuse = new Tondeuse();
				int xMax;
				int yMax;
				if (compteur == 0) {
					/*
					 * La première ligne correspond aux coordonnées du coin supérieur droit de la
					 * pelouse, celles du coin inférieur gauche sont supposées être (0,0)
					 */
					xMax = line.charAt(0);
					yMax = line.charAt(2);
				} else {
					// premiere ligne
					// la position initiale de la tondeuse
					/*
					 * La suite du fichier permet de piloter toutes les tondeuses qui ont été
					 * déployées. Chaque tondeuse a deux lignes la concernant : o la première ligne
					 * donne la position initiale de la tondeuse, ainsi que son orientation. La
					 * position et l'orientation sont fournies sous la forme de 2 chiffres et d’une
					 * lettre, séparés par un espace o la seconde ligne est une série d'instructions
					 * ordonnant à la tondeuse d'explorer la pelouse. Les instructions sont une
					 * suite de caractères sans espaces.
					 */
					// : 5 5 1 2 N GAGAGAGAA
					tondeuse.setX(line.charAt(0));
					tondeuse.setY(line.charAt(2));
					tondeuse.setOrientation(String.valueOf(line.charAt(4)));
				}
				compteur++;
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		return retour;
	}

	/**
	 * Permet de gérer le déplacement d'une tondeuse selon les instructions reçues.
	 * 
	 * @param tondeuse
	 * @param instructions
	 * @param xMax
	 * @param yMAx
	 * @return
	 */
	private String bouger(Tondeuse tondeuse, String instructions, int xMax, int yMAx) {
		for (int i = 0; i < instructions.length(); i++) {
			if (tondeuse.getX() <= xMax && tondeuse.getY() <= yMAx) {
				char instruction = instructions.charAt(i);
				switch (instruction) {
				case 'D', 'G' -> changerDirection(tondeuse);
				case 'A' -> avancer(tondeuse);
				default -> throw new IllegalStateException("Invalid instruction: " + instruction);
				}
			} else {
				/*
				 * Si la position après mouvement est en dehors de la pelouse, la tondeuse ne
				 * bouge pas, conserve son orientation et traite la commande suivante
				 */
				continue;
			}

		}
		// sinon rien faire
		return tondeuse.toString();
	}

	/**
	 * Permet de faire avance la tondeuse.
	 * 
	 * @param tondeuse
	 */
	private void avancer(Tondeuse tondeuse) {
		String direction = tondeuse.getOrientation();
		switch (direction) {
		case "N" -> tondeuse.setX(tondeuse.getX() + 1);
		case "E" -> tondeuse.setY(tondeuse.getX() + 1);
		case "W" -> tondeuse.setY(tondeuse.getX() - 1);
		case "S" -> tondeuse.setX(tondeuse.getX() - 1);

		default -> throw new IllegalStateException("Invalid direction: " + direction);
		}
		;
	}

	/**
	 * Permet de changer de direction de la tondeuse.
	 * 
	 * @param tondeuse
	 */
	private void changerDirection(Tondeuse tondeuse) {
		// direction initiale
		String directionInuitiale = tondeuse.getOrientation();
		switch (directionInuitiale) {
		case "N" -> tondeuse.setOrientation(Orientation.W.toString());
		case "E" -> tondeuse.setOrientation(Orientation.N.toString());
		case "W" -> tondeuse.setOrientation(Orientation.S.toString());
		case "S" -> tondeuse.setOrientation(Orientation.E.toString());

		default -> throw new IllegalStateException("Invalid direction: " + directionInuitiale);
		}

	}

}
