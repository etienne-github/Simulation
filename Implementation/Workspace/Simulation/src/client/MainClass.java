package client;


import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import org.restlet.data.Status;

import server.SpeciesStats;

public class MainClass {

	public static void main(String[] args) throws URISyntaxException {
		RestFacilities servMes = new RestFacilities();
		
		/*
		//Récupération d'une description d'espèce
		String s =servMes.getSpeciesDescription("Loup");
		System.out.println(s);*/
		
		//Récupération d'un objet espèce
		SpeciesStats loup = servMes.getSpecies("Loup");
		System.out.println("Age max du loup :"+loup.getMaxLifetime());
		System.out.println("Le loup mange:"+loup.getEatableFoodList());
		SpeciesStats lapin = servMes.getSpecies("Lievre");
		System.out.println("Attaque du lièvre :"+lapin.getAttackPoint());
		System.out.println("Type du lapin :"+lapin.getType());
		//Récupération de la liste des toutes les espèces
		System.out.println("Liste de toutes les espèces :"+servMes.getSpeciesList());
		
		SpeciesStats poule = new SpeciesStats();
		poule.setNom("Poule");
		poule.setDescriptif("Carnivore méchant");
		poule.setIsHerbivorious(false);
		poule.setType("Canis Rufus");
		poule.setSmellPoint(270.0);
		poule.setVisionPoint(45.0);
		poule.setMovePoint(120000.0);
		poule.setMaxLifetime(11.0*365.25);
		poule.setMinimumWeightToDeath(25.0);
		poule.setWeightConsumeByDay(4.5);
		poule.setMaxNbDaySafe(7.0);
		poule.setAttackPoint(80.0);
		poule.setDefendPoint(50.0);
		poule.setIsUseHiddenDefense(false);
		poule.setInitWeight(40.0);
		poule.setInitAge(22.0 * 30.5);
		poule.setBirthRateByDay(6.0/365.25);
		poule.getEatableFoodList().add("Lepus Europaeus");
		
		if (servMes.createSpecies(poule)) System.out.println("Espèce créée");
		else System.out.println("Erreur lors de la création de l'espèce");
		
		SpeciesStats tmp = servMes.getSpecies("Poule");
		System.out.println("Age :"+tmp.getInitAge());
		
		poule.setInitAge(22d);
		if (servMes.updateSpecies(poule)) System.out.println("Espèce mise à jour");
		else System.out.println("Erreur lors de la mise à jour de l'espèce");
		tmp = servMes.getSpecies("Poule");
		System.out.println("Age :"+tmp.getInitAge());
		
		//Suppression d'un espèce :
		if (servMes.deleteSpecies("bla")) System.out.println("Espèce supprimée");
		else System.out.println("Erreur lors de la suppression de l'espèce"); 
		
	}
		

}
