package com.remainsoftware.gravity.internal.server.admin.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestServlet extends AbstractServlet {

	private static final long serialVersionUID = 672864964579366536L;

	public RestServlet(AbstractComponent component) {
		super(component);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		StringBuffer response = new StringBuffer();
		response.append("<html>").append("<body><h1><center>");
		response.append("<br/><br/><br/><br/>Remain Software and Industrial-TSI say Hello<br/><br/><br/>");
		response.append("You can learn this!!<br/><br/>");
		response.append("Let us teach you how: <b>+31 (0)30-600 50 10</b>");
		response.append("<p/>Path = " + req.getPathInfo());
		response.append("</center></h1></body></html>");
		resp.setContentType("text/html");
		resp.getWriter().write(response.toString());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuffer response = new StringBuffer();
		response.append("<html>").append("<body>");

		response.append("</body></html>");
		resp.setContentType("text/html");
		resp.getWriter().write(response.toString());
	}
}
