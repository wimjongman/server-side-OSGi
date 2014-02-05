package com.remainsoftware.gravity.restlet.lib;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;

/**
 * Activator will enable the restlet slf4j facade.
 * 
 */
public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	private Server server;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					server = new Server(Protocol.HTTP, 8554, new Restlet() {
						@Override
						public void handle(Request request, Response response) {
							response.setEntity("Hello world!",
									MediaType.TEXT_PLAIN);
						}
					});

					server.start();
				} catch (Exception e) {
					System.err.println("server start error: " + e.getMessage());
				}
			}
		});

		thread.start();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		server.stop();
	}

}
