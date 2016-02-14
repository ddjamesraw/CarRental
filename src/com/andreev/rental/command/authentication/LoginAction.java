package com.andreev.rental.command.authentication;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.logic.Authentication;
import com.andreev.rental.logic.IAuthentication;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.UserBuilder;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.User;

public class LoginAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(LoginAction.class);

	private static final String PARAM_BODY = "body";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_USER = "user";
	private static final String JSP_LOGIN = "jsp.authentication.login";
	private static final String JSP_MAIN = "jsp.main";

	public LoginAction() {
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IAuthentication authentication = null;
		try {
			authentication = Authentication.getInstance();
		} catch (LogicException e) {
			LOG.error("Login action failed", e);
			return getForwardError();
		}
		String body = null;
		String email = request.getParameter(PARAM_EMAIL);
		String password = request.getParameter(PARAM_PASSWORD);
		UserBuilder builder = new UserBuilder(message);
		if (isValidToken(request)) {
			if (builder.email(email).password(password).build()) {
				try {
					User user = (User) builder.getEntity();
					if (authentication.login(message, user)) {
						user = authentication.getUser(message, user.getEmail());
						request.getSession().setAttribute(PARAM_USER, user);
					} else {
						body = toLogin();
					}
				} catch (LogicException e) {
					LOG.error("Getting user failed", e);
					body = toLogin();
				}
			} else {
				body = toLogin();
			}
		}
		setMessages(request, message);
		request.setAttribute(PARAM_BODY, body);
		String forward = resources.getString(JSP_MAIN);
		return forward;
	}

	private String toLogin() {
		return resources.getString(JSP_LOGIN);
	}
}
