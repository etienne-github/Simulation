package server;

import java.util.concurrent.ConcurrentMap;

import org.restlet.resource.ServerResource;

public class BaseResource extends ServerResource{
	
	// Retourne la liste des espèces du serveur
    protected ConcurrentMap<String, SpeciesStats> getStats() {  
        return ((SpeciesServerApplication) getApplication()).getStats();  
    }  
}
