package client;


import java.net.URISyntaxException;

import org.restlet.data.Status;

import server.SpeciesStats;

public class MainClass {

	public static void main(String[] args) throws URISyntaxException {
		RestFacilities servMes = new RestFacilities();
		
		/*
		//Récupération d'une description d'espèce
		String s =servMes.getSpeciesDescription("Loup");
		System.out.println(s);
		
		//Récupération d'un objet espèce
		SpeciesStats loup = servMes.getSpecies("Loup");
		System.out.println("Age max du loup :"+loup.getAgeMax());
		System.out.println("Le loup mange:"+loup.getMange());
		SpeciesStats lapin = servMes.getSpecies("Lapin");
		System.out.println("Distance de déplacement du lapin :"+lapin.getDistanceDeplacement());
	
		//Récupération de la liste des toutes les espèces
		System.out.println("Liste de toutes les espèces :"+servMes.getSpeciesList());
		*/
		
		SpeciesStats Poule =  new SpeciesStats(
					"Poule",
					"Le mouton est une espece typiquement caracterisée par son esprit de groupe", 
					2.5f, //odorat
					3.1f, //vue
					10.5f, //attaquer
					15, //ageMax
					5.0f, //energie
					25.5f, //distanceDeplacement
					3.25f, //seReproduire
					0.34f, // gestation
					8.0f, // poidsMin
					12.0f); //poidsMax)
			
		Poule.getMange().add("Herbe");
		if (servMes.createSpecies(Poule)) System.out.println("Espèce créée");
		else System.out.println("Erreur lors de la création de l'espèce"); 
		
		System.out.println("Liste de toutes les espèces :"+servMes.getSpeciesList());
		
		SpeciesStats tmp = servMes.getSpecies("Poule");
		System.out.println("Age :"+tmp.getAttaquer());
		
		Poule.setAttaquer(10f);
		if (servMes.updateSpecies(Poule)) System.out.println("Espèce mise à jour");
		else System.out.println("Erreur lors de la mise à jour de l'espèce");
		tmp = servMes.getSpecies("Poule");
		System.out.println("Age :"+tmp.getAttaquer());
		
	}
		

}
