package com.remainsoftware.gravity.internal.server.admin.ui;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

public class AdminComponent {

	public static final String ALIAS = "/remain/software";

	class AdminHttpContext implements HttpContext {
		public URL getResource(String name) {
			URL url = context.getBundle().getEntry(name);
			return url;
		}

		public boolean handleSecurity(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			return true;
		}

		public String getMimeType(String name) {
			return null;
		}
	}

	private BundleContext context;
	private HttpService httpService;
	private HttpContext httpContext;

	void bindHttpService(HttpService httpService) {
		this.httpService = httpService;
		this.httpContext = new AdminHttpContext();
	}

	void unbindHttpService(HttpService httpService) {
		this.httpService = null;
		this.httpContext = null;
	}

	void activate(BundleContext context) throws Exception {
		this.context = context;
		this.httpService.registerServlet(ALIAS, new AdminServlet(this), null,
				httpContext);
	}

	void deactivate() {
		this.httpService.unregister(ALIAS);
		this.context = null;
	}
}
