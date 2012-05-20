package client;

import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.InputRepresentation;

import server.SpeciesStats;


public class RestFacilities {

	public String getSpeciesDescription(String species) {
		URI uri; 
		String s = null;
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			uri = new URI("http://species-ia04.appspot.com/species/"+species);
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader("Accept", "species/text");
			HttpResponse res = client.execute(httpget);
			HttpEntity entity = res.getEntity();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				s = EntityUtils.toString(entity);
			}
				
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return s;
		}
	
	
	public SpeciesStats getSpecies(String species) {
		URI uri; 
		InputStream data;
		DefaultHttpClient client = new DefaultHttpClient();
		SpeciesStats ret = null;
		try {
			uri = new URI("http://species-ia04.appspot.com/species/"+species);
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader("Accept", "text/json");
			HttpResponse res = client.execute(httpget);
			HttpEntity entity = res.getEntity();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				data = entity.getContent();
				InputRepresentation repr = new InputRepresentation(data);
				JacksonRepresentation<SpeciesStats> jrepr = 
						new JacksonRepresentation<SpeciesStats>(repr,SpeciesStats.class);
				ret = jrepr.getObject();
			}
				
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return ret;
		}
	
	
	public String getSpeciesList() {
		URI uri; 
		String s = null;
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			uri = new URI("http://species-ia04.appspot.com/species/index/");
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader("Accept", "species/text");
			HttpResponse res = client.execute(httpget);
			HttpEntity entity = res.getEntity();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				s = EntityUtils.toString(entity);
			}
				
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return s;
		}

}
