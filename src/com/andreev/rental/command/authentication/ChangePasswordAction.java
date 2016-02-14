package com.andreev.rental.command.authentication;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.Authentication;
import com.andreev.rental.logic.IAuthentication;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.UserBuilder;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.User;

public class ChangePasswordAction extends AbstractAction {

	private static final Logger LOG = Logger
			.getLogger(ChangePasswordAction.class);

	private static final String PARAM_BODY = "body";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_NEW_PASSWORD = "newPassword";
	private static final String PARAM_CONFIRM_PASSWORD = "confirmPassword";
	private static final String PARAM_USER = "user";
	private static final String JSP_BODY_CHANGEPASSWORD = "jsp.authentication.changepassword";
	private static final String JSP_BODY_PROFILE = "jsp.authentication.profile";
	private static final String JSP_MAIN = "jsp.main";

	public ChangePasswordAction() {
		setStatus(EUserStatus.REGISTERED);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager messageManager = new MessageManager();
		IAuthentication authentication = null;
		try {
			authentication = Authentication.getInstance();
		} catch (LogicException e) {
			LOG.error("Change passwordd action failed", e);
			return getForwardError();
		}
		String body = null;
		String password = request.getParameter(PARAM_PASSWORD);
		String newPassword = request.getParameter(PARAM_NEW_PASSWORD);
		String confirmPassword = request.getParameter(PARAM_CONFIRM_PASSWORD);
		User user = (User) request.getSession().getAttribute(PARAM_USER);
		UserBuilder builder = new UserBuilder(user, messageManager);
		if (!isValidToken(request)) {
			body = toProfile();
		} else if (authentication.checkPasswords(messageManager, newPassword,
				confirmPassword)
				&& authentication.checkPasswords(messageManager, user,
						builder.encodePassword(password))
				&& builder.password(newPassword).build()) {
			if (authentication.edit(messageManager, user)) {
				request.getSession().setAttribute(PARAM_USER, user);
				body = toProfile();
			} else {
				body = toChangePassword();
			}
		} else {
			body = toChangePassword();
		}
		setMessages(request, messageManager);
		request.setAttribute(PARAM_BODY, body);
		String forward = resources.getString(JSP_MAIN);
		return forward;
	}

	private String toChangePassword() {
		return resources.getString(JSP_BODY_CHANGEPASSWORD);
	}

	private String toProfile() {
		return resources.getString(JSP_BODY_PROFILE);
	}
}
