package com.ia04.species.server;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

public class SpeciesListServiceResource extends ServerResource {
	

	@Get
	public void retrieve() {
		// Récupérer les attributs
		Map<String,Object> attributes = getRequest().getAttributes();
		// Récupérer l’attribut header "Accept"
		Series<Header> serie= (Series<Header>) attributes.get("org.restlet.http.headers");
		String s = serie.getValues("Accept");
		
		// si Accept = species/text
		if (s.equals("species/text")) {
			
		//retourner une StringRepresentation
		ListIterator<SpeciesStats> it = DBManager.getAllSpecies().listIterator();
		ArrayList<String> listeSpecies = new ArrayList<String>();
		while(it.hasNext())
		{
			listeSpecies.add(it.next().getNom());
		}
		StringRepresentation entity =	new StringRepresentation(listeSpecies.toString(),MediaType.TEXT_PLAIN);
				getResponse().setEntity(entity);
				System.out.println("Entity :"+entity);
				//mettre le Status à SUCCESS_OK
				getResponse().setStatus(Status.SUCCESS_OK);
		return;
		}

	}

}

