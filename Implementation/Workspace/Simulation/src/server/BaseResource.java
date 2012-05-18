package server;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.restlet.resource.ServerResource;

public class BaseResource extends ServerResource{
	
	// Retourne la liste des espèces du serveur
    protected ConcurrentMap<String, SpeciesStats> getStats() {  
        return ((SpeciesServerApplication) getApplication()).getStats();  
    }  
    
    //retourne la liste de toutes les espèces de la map
    protected String getAllSpecies()
    {
    	String ret;
    	Set<String> set = getStats().keySet();
    	ret = set.toString();
    	return ret;
    }
}
