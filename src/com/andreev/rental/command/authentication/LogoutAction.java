package com.andreev.rental.command.authentication;

import javax.servlet.http.HttpServletRequest;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.resources.IResources;
import com.andreev.rental.resources.CommandResources;

public class LogoutAction extends AbstractAction {
	
	private static final String PARAM_USER = "user";
	private static final String JSP_MAIN = "jsp.main";

	public LogoutAction() {
		setStatus(EUserStatus.REGISTERED);
	}

	@Override
	public String execute(HttpServletRequest request) {
		IResources resources = CommandResources.getInstance();
		String forward = resources.getString(JSP_MAIN);
		request.getSession().removeAttribute(PARAM_USER);
		return forward;
	}

}
