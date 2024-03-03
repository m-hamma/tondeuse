package com.mhamma.tondeuse.service.impl;

import java.beans.JavaBean;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.mhamma.tondeuse.enums.Orientation;
import com.mhamma.tondeuse.model.Tondeuse;
import com.mhamma.tondeuse.service.TondeuseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe qui implemente TondeuseService.
 */
@JavaBean
public class TondeuseServiceImpl implements TondeuseService {
	/* Logger **/
	private static final Log log = LogFactory.getLog(TondeuseServiceImpl.class);

	/**
	 * Methode principale qui permet de lire le fichier et piloter les tondeuses.
	 */
	public String piloterTondeuse() {
		String filePath = "C:/fichiers/tondeuse.txt";
		String retour = "";
		try (FileReader fileReader = new FileReader(filePath);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			String line;
			int compteur = 0;
			int xMax = 0;
			int yMax = 0;
			while ((line = bufferedReader.readLine()) != null) {
				final Tondeuse tondeuse = new Tondeuse();

				if (compteur == 0) {
					xMax = Character.getNumericValue(line.charAt(0));
					yMax = Character.getNumericValue(line.charAt(2));
				} else {
					// : 5 5 1 2 N GAGAGAGAA
					tondeuse.setX(Character.getNumericValue(line.charAt(0)));
					tondeuse.setY(Character.getNumericValue(line.charAt(2)));
					tondeuse.setOrientation(String.valueOf(line.charAt(4)));
					retour += " " + bouger(tondeuse, line.substring(5, line.length()), xMax, yMax);
				}

				compteur++;
			}
		} catch (IOException e) {
			log.debug("Error reading file: " + e.getMessage());
		}
		return retour.trim();
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
		for (int i = 1; i < instructions.trim().length()+1; i++) {
			if (tondeuse.getX() <= xMax && tondeuse.getY() <= yMAx) {
				var instr = instructions.charAt(i);
				switch (instr) {
				case 'D' -> changerDirection(tondeuse);
				case 'G' -> changerDirection(tondeuse);
				case 'A' -> avancer(tondeuse);
				default -> throw new IllegalStateException("Invalid instruction: " + instr);
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
		case "N" -> tondeuse.setY(tondeuse.getY() + 1);
		case "S" -> tondeuse.setY((tondeuse.getY() - 1));
		case "E" -> tondeuse.setX((tondeuse.getX() + 1));
		case "W" -> tondeuse.setX(tondeuse.getX() - 1);

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
		case "N" -> tondeuse.setOrientation(Orientation.E.toString());
		case "E" -> tondeuse.setOrientation(Orientation.S.toString());
		case "S" -> tondeuse.setOrientation(Orientation.W.toString());
		case "W" -> tondeuse.setOrientation(Orientation.N.toString());

		default -> throw new IllegalStateException("Invalid direction: " + directionInuitiale);
		}

	}

}
