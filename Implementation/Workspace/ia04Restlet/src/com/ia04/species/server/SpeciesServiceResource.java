package com.ia04.species.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.util.Series;
import com.ia04.species.server.SpeciesStats;

public class SpeciesServiceResource extends BaseResource {
	

	@Get
	public void retrieve() {
		// Récupérer l’attribut "id"
		Map<String,Object> attributes = getRequest().getAttributes();
		String speciesId = (String) attributes.get("id");
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

    @Put  
    public void storeItem(Representation entity) throws IOException{  
		
    	// Récupérer l’attribut "id"
    			Map<String,Object> attributes = getRequest().getAttributes();
    			String speciesId = (String) attributes.get("id");
    			// Récupérer l’attribut header "Accept"
    			Series<Header> serie= (Series<Header>) attributes.get("org.restlet.http.headers");
    			String s = serie.getValues("Accept");
    			
				//on teste si l'espèce envoyée n'existe pas déjà sur le serveur
    			if (getStats().containsKey(speciesId)) {
    				error_create(speciesId);
    			return;
    			}
    			
    			// si Accept = application/json
    			if (s.equals("application/json")){
    				InputStream data;
    				data = entity.getStream();
    				InputRepresentation repr = new InputRepresentation(data);
    				JacksonRepresentation<SpeciesStats> jrepr = 
    						new JacksonRepresentation<SpeciesStats>(repr,SpeciesStats.class);
    				SpeciesStats recieved = new SpeciesStats();
    				recieved=jrepr.getObject();
   					getStats().put(speciesId, recieved);
    			}
		
	}
	
    @Post 
    public void updateItem(Representation entity) throws IOException{  
		
    	// Récupérer l’attribut "id"
    			Map<String,Object> attributes = getRequest().getAttributes();
    			String speciesId = (String) attributes.get("id");
    			// Récupérer l’attribut header "Accept"
    			Series<Header> serie= (Series<Header>) attributes.get("org.restlet.http.headers");
    			String s = serie.getValues("Accept");
    			
				//on teste si l'espèce envoyée existe déjà sur le serveur
    			if (getStats().containsKey(speciesId)) 
    			{
   			
	    			// si Accept = application/json
	    			if (s.equals("application/json")){
	    				//on supprime l'espèce côté serveur
	        			getStats().remove(speciesId);
	    				InputStream data;
	    				data = entity.getStream();
	    				InputRepresentation repr = new InputRepresentation(data);
	    				JacksonRepresentation<SpeciesStats> jrepr = 
	    						new JacksonRepresentation<SpeciesStats>(repr,SpeciesStats.class);
	    				SpeciesStats recieved = new SpeciesStats();
	    				recieved=jrepr.getObject();
	   					getStats().put(speciesId, recieved);
	    				}
    			}
    			
    			else 
    				{
    					error_update(speciesId);
    					return;
    				}
		
	}
    
	private void error(String speciesId) {
		getResponse().setEntity(
		new StringRepresentation(
				"{\"status\" : \"Parameter Error\"}",
				MediaType.TEXT_PLAIN));
				getResponse().setStatus(
				Status.CLIENT_ERROR_BAD_REQUEST);
		}
	
	private void error_create(String speciesId) {
		getResponse().setEntity(
		new StringRepresentation(
				"{\"status\" : \"Already existing species\"}",
				MediaType.TEXT_PLAIN));
				getResponse().setStatus(
				Status.CLIENT_ERROR_CONFLICT);
		}

	private void error_update(String speciesId) {
		getResponse().setEntity(
		new StringRepresentation(
				"{\"status\" : \"Species doesn't exists on the server\"}",
				MediaType.TEXT_PLAIN));
				getResponse().setStatus(
				Status.CLIENT_ERROR_CONFLICT);
		}


}

