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

public class RegisterAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(RegisterAction.class);

	private static final String PARAM_BODY = "body";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_CONFIRM_PASSWORD = "confirmPassword";
	private static final String PARAM_USER = "user";
	private static final String JSP_BODY_REGISTER = "jsp.authentication.register";
	private static final String JSP_MAIN = "jsp.main";

	public RegisterAction() {
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
		String confirmPassword = request.getParameter(PARAM_CONFIRM_PASSWORD);
		UserBuilder builder = new UserBuilder(message);
		if (isValidToken(request)) {
			if (authentication.checkPasswords(message, password,
					confirmPassword)
					&& builder.email(email).password(password).build()) {
				User user = (User) builder.getEntity();
				try {
					if (authentication.register(message, user)) {
						user = authentication.getUser(message, email);
						request.getSession().setAttribute(PARAM_USER, user);
					} else {
						body = resources.getString(JSP_BODY_REGISTER);
					}
				} catch (LogicException e) {
					LOG.error("Getting user failed", e);
					body = resources.getString(JSP_BODY_REGISTER);
				}
			} else {
				body = resources.getString(JSP_BODY_REGISTER);
			}
		}
		setMessages(request, message);
		request.setAttribute(PARAM_BODY, body);
		String forward = resources.getString(JSP_MAIN);
		return forward;
	}

}
