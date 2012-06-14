package com.ia04.species;

import java.io.IOException;

@SuppressWarnings("serial")
public class Ia04RestletServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
