package com.andreev.rental.command;

import javax.servlet.http.HttpServletRequest;

import com.andreev.rental.resources.IResources;
import com.andreev.rental.resources.CommandResources;

public class ToPageAction extends AbstractAction {

	private static final String PARAM_FORWARD = "forward";
	private static final String PARAM_BODY = "body";
	private static final String JSP_MAIN = "jsp.main";

	public ToPageAction() {
	}

	@Override
	public String execute(HttpServletRequest request) {
		IResources resources = CommandResources.getInstance();
		String forwardPath = resources.getString(JSP_MAIN);
		String forward = request.getParameter(PARAM_FORWARD);
		if (forward != null && resources.containsKey(forward)) {
			String bodyPath = resources.getString(forward);
			request.setAttribute(PARAM_BODY, bodyPath);
		}
		return forwardPath;
	}

}
