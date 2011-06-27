package com.remainsoftware.gravity.internal.server.admin.ui;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.core.runtime.IStatus;

public abstract class AbstractAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 879814437592383904L;

	private AdminComponent adminComponent;
	
	public AbstractAdminServlet(AdminComponent adminComponent) {
		this.adminComponent = adminComponent;
	}
	
	AdminComponent getAdminComponent() {
		return adminComponent;
	}
	
	
	abstract String getAdminAlias();

	protected void addReturnLink(StringBuffer response) {
		response.append("<p>" + createLink(getAdminAlias(),"Click here to return to admin page"));
	}

	protected String createLink(String alias, String text) {
		return "<a href=\""+ getServletContext().getContextPath() + alias + "\">" + text + "</a>";
	}
	
	protected void printExceptionToResponse(Throwable e, StringBuffer response) {
		if (e == null)
			return;
		StackTraceElement[] stack = e.getStackTrace();
		if (stack != null) {
			for (int i = 0; i < stack.length; i++) {
				response.append(stack[i].toString() + "<br>");
			}
		}
		Throwable cause = e.getCause();
		if (cause != null)
			printExceptionToResponse(cause, response);
	}

	@SuppressWarnings("rawtypes")
	protected Map getPropertiesFromRequest(HttpServletRequest request) {
		Map<String, String> result = new Hashtable<String, String>();
		Enumeration e = request.getParameterNames();
		for (; e.hasMoreElements();) {
			String name = (String) e.nextElement();
			String val = request.getParameter(name);
			result.put(name, val);
		}
		return result;
	}

	protected void showStatus(IStatus status, String message,
			StringBuffer response) {
		if (status.isOK()) {
			response.append(message + " successfull!<br>");
		} else {
			printMultiStatusToResponse(status, response);
		}
		response.append("<hr>");
	}

	private void printMultiStatusToResponse(IStatus status,
			StringBuffer response) {
		printStatusToResponse(status, response);
		printExceptionToResponse(status.getException(), response);
		if (status.isMultiStatus()) {
			IStatus[] children = status.getChildren();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					printMultiStatusToResponse(children[i], response);
				}
			}
		}
	}

	private void printStatusToResponse(IStatus status, StringBuffer response) {
		response.append("<br>Status: ").append("code=")
				.append(status.getCode()).append(";message=")
				.append(status.getMessage());
		printExceptionToResponse(status.getException(), response);
	}

	protected void showManagerError(String errorMessage, StringBuffer response) {
		response.append(errorMessage);
		response.append("<hr>");
	}

	protected String printStringArray(String[] stringArray) {
		StringBuffer result = new StringBuffer();
		if (stringArray == null)
			return result.toString();
		for (int i = 0; i < stringArray.length; i++) {
			if (i > 0)
				result.append(",<br>");
			result.append(stringArray[i]);
		}
		return result.toString();
	}

	protected String printLongArray(long[] usingBundles) {
		StringBuffer result = new StringBuffer();
		if (usingBundles == null)
			return result.toString();
		for (int i = 0; i < usingBundles.length; i++) {
			if (i > 0)
				result.append(",");
			result.append(usingBundles[i]);
		}
		return result.toString();
	}

	@SuppressWarnings("rawtypes")
	protected String printProperties(Map serviceProperties) {
		StringBuffer result = new StringBuffer();
		if (serviceProperties == null)
			return result.toString();
		for (Iterator i = serviceProperties.keySet().iterator(); i.hasNext();) {
			Object key = i.next();
			Object val = serviceProperties.get(key);
			result.append(key).append("=").append(val);
			if (i.hasNext())
				result.append(",");
		}
		return result.toString();
	}

}

