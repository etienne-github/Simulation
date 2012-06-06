package com.ia04.species.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


public class DBManager {
	
	public static List<SpeciesStats> getSpecies(String namePara) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    
	    Query query = pm.newQuery(SpeciesStats.class);
	    query.setFilter("nom == namePara");
	    query.declareParameters("String namePara");
	    return (List<SpeciesStats>) query.execute(namePara);
	}

	public static void save(SpeciesStats species) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	        pm.makePersistent(species);
	    } finally {
	        pm.close();
	    }
	}
	
	public static void delete(String namePara) {

	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    
	    Query query = pm.newQuery(SpeciesStats.class);
	    query.setFilter("nom == namePara");
	    query.declareParameters("String namePara");
	    
	    List<SpeciesStats> list = (List<SpeciesStats>) query.execute(namePara);
	    try {
	        pm.deletePersistent(list.get(0));
	    } finally {
	        pm.close();
	    }
	}
	
	public static List<SpeciesStats> getAllSpecies() {

	    PersistenceManager pm = PMF.get().getPersistenceManager();
	 
	    Query query = pm.newQuery(SpeciesStats.class);
	 
	    return (List<SpeciesStats>) query.execute();
	    
	}
}
