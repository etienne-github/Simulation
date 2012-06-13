package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.InputRepresentation;

import server.SpeciesStats;


public class RestFacilities {
	
	DefaultHttpClient client;
	URI uri;
	
	public RestFacilities()
	{
		client = new DefaultHttpClient();
		//HttpHost proxy = new HttpHost("proxyweb.utc.fr", 3128);
		//client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

	public String getSpeciesDescription(String species) {
		String s = null;
		try {
			uri = new URI("http://species-ia04.appspot.com/species/"+species);
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader("Accept", "species/text");
			HttpResponse res = client.execute(httpget);
			HttpEntity entity = res.getEntity();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				s = EntityUtils.toString(entity);
			}
			
			else if (res.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST)
			{
				System.err.println("La ressource n'existe pas sur le serveur");
			}
				
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return s;
		}
	
	
	public SpeciesStats getSpecies(String species) {
		InputStream data;
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
			
			else if (res.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST)
			{
				System.err.println("La ressource n'existe pas sur le serveur");
			}
				
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return ret;
		}
	
	
	public String getSpeciesList() { 
		String s = null;
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
		try { //Décodage des accents
			s = URLDecoder.decode(s, "UTF-8");
			return s;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		}
	
	public boolean createSpecies(SpeciesStats species)
	{
		HttpResponse res=null;
		try {
			uri = new URI("http://species-ia04.appspot.com/species/"+species.getNom());
			HttpPut httpput = new HttpPut(uri);
			httpput.addHeader("Accept", "application/json");
			httpput.addHeader("Content-Type", "application/json");
			 
			 //sérialisation de l'objet
			ObjectMapper om = new ObjectMapper();
			StringWriter sw = new StringWriter();

			om.writeValue(sw,species);

			StringEntity entity = new StringEntity(sw.toString(),"UTF-8");
			entity.setContentType("application/json");
			httpput.setEntity(entity);

			//envoi de la requête
			res = client.execute(httpput);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			}
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				try {
					EntityUtils.consume(res.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			
			else if (res.getStatusLine().getStatusCode() == HttpStatus.SC_CONFLICT)
			{
				System.err.println("La ressource existe déjà sur le serveur\n");
				try {
					//On consomme la ressource pour libérer la connexion
					EntityUtils.consume(res.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			else 
				{
				try {
					//On consomme la ressource pour libérer la connexion
					EntityUtils.consume(res.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
				}
		

	}
	
	public boolean deleteSpecies(String speciesName)
	{
		HttpResponse res=null;
		try {
			uri = new URI("http://species-ia04.appspot.com/species/"+speciesName);
			HttpDelete httpdelete = new HttpDelete(uri);
			httpdelete.addHeader("Accept", "application/delete");
			 
			//envoi de la requête
			res = client.execute(httpdelete);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			}
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				try {
					EntityUtils.consume(res.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			
			else if (res.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST)
			{
				System.err.println("La ressource n'existe pas sur le serveur\n");
				try {
					//On consomme la ressource pour libérer la connexion
					EntityUtils.consume(res.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			else 
				{
				try {
					//On consomme la ressource pour libérer la connexion
					EntityUtils.consume(res.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
				}
		

	}
	
	public boolean updateSpecies(SpeciesStats species)
	{
		HttpResponse res=null;
		try {
			uri = new URI("http://species-ia04.appspot.com/species/"+species.getNom());
			HttpPost httppost = new HttpPost(uri);
			httppost.addHeader("Accept", "application/json");
			httppost.addHeader("Content-Type", "application/json");
			 
			 //sérialisation de l'objet
			ObjectMapper om = new ObjectMapper();
			StringWriter sw = new StringWriter();

			om.writeValue(sw,species);

			StringEntity entity = new StringEntity(sw.toString(),"UTF-8");
			entity.setContentType("application/json");
			httppost.setEntity(entity);

			//envoi de la requête
			res = client.execute(httppost);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			}
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				//On consomme la ressource pour libérer la connexion
				try {
					EntityUtils.consume(res.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			
			else if (res.getStatusLine().getStatusCode() == HttpStatus.SC_CONFLICT)
			{
				System.err.println("La ressource n'existe pas sur le serveur\n");
				try {
					//On consomme la ressource pour libérer la connexion
					EntityUtils.consume(res.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			else 
				{
				try {
					//On consomme la ressource pour libérer la connexion
					EntityUtils.consume(res.getEntity());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
				}
		

	}
}
