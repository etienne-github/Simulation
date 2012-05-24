package client;


import java.net.URISyntaxException;
import server.SpeciesStats;

public class MainClass {

	public static void main(String[] args) throws URISyntaxException {
		RestFacilities servMes = new RestFacilities();
		
		//Récupération d'une description d'espèce
		String s =servMes.getSpeciesDescription("Loup");
		System.out.println(s);
		
		//Récupération d'un objet espèce
		SpeciesStats loup = servMes.getSpecies("Loup");
		System.out.println("Age max du loup :"+loup.getAgeMax());
		SpeciesStats lapin = servMes.getSpecies("Lapin");
		System.out.println("Distance de déplacement du lapin :"+lapin.getDistanceDeplacement());
	
		//Récupération de la liste des toutes les espèces
		System.out.println("Liste de toutes les espèces :");
		System.out.println(servMes.getSpeciesList());
	
	}

}
