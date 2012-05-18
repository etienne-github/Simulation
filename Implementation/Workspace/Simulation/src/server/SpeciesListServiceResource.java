package server;

import java.util.Map;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public class SpeciesListServiceResource extends BaseResource {
	

	@Get
	public void retrieve() {
		// Récupérer les attributs
		Map<String,Object> attributes = getRequest().getAttributes();
		// Récupérer l’attribut header "Accept"
		Form form = (Form) attributes.get("org.restlet.http.headers");
		String s = form.getValues("Accept");
		
		// si Accept = species/text
		if (s.equals("species/text")) {
			
		//retourner une StringRepresentation
		StringRepresentation entity =	new StringRepresentation(getAllSpecies(),MediaType.TEXT_PLAIN);
				getResponse().setEntity(entity);
				System.out.println("Entity :"+entity);
				//mettre le Status à SUCCESS_OK
				getResponse().setStatus(Status.SUCCESS_OK);
		return;
		}

	}

}

