package com.hamma.tondeuse.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mhamma.tondeuse.service.TondeuseService;

/**
 * Classe de test pour le pilotage de la tondeuse.
 * an entrée, nous un fichier avec le contecnu :
 * 
 * '5 5 1 2 N GAGAGAGAA 3 3 E AADAADADDA'
 * Resultat attendu : 1 3 N 5 1 E
 * 
 * @author mhamma
 */
@ExtendWith(MockitoExtension.class)
public class TondeuseServiceTest {
	@Spy
	TondeuseService tondeuseService;
	
	@BeforeEach
	public void setup() {
	  MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testerPpilotertondeuse() {
		// Assert
		assertEquals("1 3 N 5 1 E", tondeuseService.piloterTondeuse("C:\\fichiers\\"));
	}
}
