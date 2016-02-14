package com.andreev.rental.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.resources.IResources;
import com.andreev.rental.resources.CommandResources;

public abstract class AbstractAction {

	private static final String PARAM_MESSAGE = "message";
	private static final String PARAM_TOKEN = "token";
	private static final String JSP_ERROR = "jsp.error";

	protected final static IResources resources = CommandResources
			.getInstance();
	private EUserStatus STATUS = EUserStatus.ALL;

	public abstract String execute(HttpServletRequest request);

	public EUserStatus getStatus() {
		return STATUS;
	}

	protected final void setStatus(EUserStatus status) {
		this.STATUS = status;
	}

	protected void setMessages(HttpServletRequest request,
			MessageManager messageManager) {
		request.setAttribute(PARAM_MESSAGE, messageManager.getMessages());
	}

	protected String getForwardError() {
		return resources.getString(JSP_ERROR);
	}

	protected boolean isValidToken(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		String saved = (String) session.getAttribute(PARAM_TOKEN);
		if (saved == null) {
			return false;
		}
		String token = request.getParameter(PARAM_TOKEN);
		if (token == null) {
			return false;
		}
		return saved.equals(token);
	}

}
