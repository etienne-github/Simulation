package com.ia04.species.server;

import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.util.Series;

public class SpeciesServiceResource extends BaseResource {
	

	@Get
	public void retrieve() {
		// Récupérer l’attribut "id"
		Map<String,Object> attributes = getRequest().getAttributes();
		String speciesId = (String) attributes.get("id");
		System.err.println("L'id récupéré est : "+ speciesId);
		// Récupérer l’attribut header "Accept"
		Series<Header> serie= (Series<Header>) attributes.get("org.restlet.http.headers");
		String s = serie.getValues("Accept");
		
		// si l’attribut id n’existe pas erreur
		if (!getStats().containsKey(speciesId)) {
			error(speciesId);
		return;
		}
		
		SpeciesStats speciesDoc = getStats().get(speciesId);
		
		// si Accept = species/text
		if (s.equals("species/text")) {
			
		//retourner une StringRepresentation
		StringRepresentation entity =	new StringRepresentation(speciesDoc.getDescriptif(),MediaType.TEXT_PLAIN);
				getResponse().setEntity(entity);
				System.out.println("Entity :"+entity);
				//mettre le Status à SUCCESS_OK
				getResponse().setStatus(Status.SUCCESS_OK);
		return;
		}

		// si Accept = text/json
		if (s.equals("text/json")){
		
			ObjectMapper om = new ObjectMapper();
			StringWriter sw = new StringWriter();
			try {
			om.writeValue(sw,speciesDoc);
			} 
			catch (Exception e) {
				System.err.println("Erreur écriture Json");
				}
			getResponse().setEntity(
					//retourner une StringRepresentation
					new StringRepresentation(
					sw.toString(),MediaType.TEXT_PLAIN));
					//mettre le Status à SUCCESS_OK
					getResponse().setStatus(Status.SUCCESS_OK);

		}
		



	}
	
	@Post
	public void update(Representation entity) {
	
	}
	
	
	private void error(String speciesId) {
		getResponse().setEntity(
		new StringRepresentation(
				"{\"status\" : \"Parameter Error\"}",
				MediaType.TEXT_PLAIN));
				getResponse().setStatus(
				Status.CLIENT_ERROR_BAD_REQUEST);
		}



}

