package server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class SpeciesServerApplication extends Application{
	
    // Liste des espèces persistente en mémoire
	private final ConcurrentMap<String, SpeciesStats> speciesMap = new ConcurrentHashMap<String, SpeciesStats>(); 
    
	SpeciesServerApplication(){
		SpeciesStats loup =  new SpeciesStats(
					"Le loup est une espèce fort sympatique vivant dans les forêts", 
					2.5f, //odorat
					3.1f, //vue
					10.5f, //attaquer
					8,	//ageMin
					12, //ageMax
					5.0f, //energie
					25.5f, //distanceDeplacement
					3.25f, //seReproduire
					0.34f, // gestation
					8.0f, // poidsMin
					12.0f); //poidsMax)
			
			getStats().put("Loup", loup);
			
		SpeciesStats lapin =  new SpeciesStats(
					"Le lapin sauvage est une espèce qui prolifère dans nos contrées", 
					2.5f, //odorat
					3.1f, //vue
					10.5f, //attaquer
					8,	//ageMin
					12, //ageMax
					5.0f, //energie
					25.5f, //distanceDeplacement
					3.25f, //seReproduire
					0.34f, // gestation
					8.0f, // poidsMin
					12.0f); //poidsMax)
			
			getStats().put("Lapin", lapin);
			System.out.println("Constructeur appelé");
	}
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach(
				"/species/{id}",
				SpeciesServiceResource.class);
		
		router.attach(
			"/species/index/",
			SpeciesListServiceResource.class);
		return router;
	}
	
	//retourne la table
    public ConcurrentMap<String, SpeciesStats> getStats() {  
        return speciesMap;
    }
    
}

