package server;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class ImageMainServer {
	public static void main(String[] args) {
		startImageServer();
	}
	
	public static void startImageServer() {
		try { // Create a new Component.
			Component component = new Component();
			// Add a new HTTP server listening on port 8182.
			component.getServers().add(Protocol.HTTP, 8182);
			component.getDefaultHost().attach(
					new SpeciesServerApplication());
			component.start(); // Start the component.
			} 
		catch(Exception ex) 
		{
			System.err.println("Echec création nouveau composant");
		}
	}
	



}
