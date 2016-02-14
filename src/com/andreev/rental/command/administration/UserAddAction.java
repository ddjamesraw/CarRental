package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.UserBuilder;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.UserService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.User;

public class UserAddAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(UserAddAction.class);

	private static final String PARAM_USER_LIST = "userList";
	private static final String PARAM_USER_EMAIL = "email";
	private static final String PARAM_USER_PASSWORD = "password";
	private static final String PARAM_USER_ADMIN = "admin";
	private static final String PARAM_USER_NAME = "name";
	private static final String PARAM_USER_SURNAME = "surname";
	private static final String PARAM_USER_PASSPORT = "passport";
	private static final String PARAM_USER_ADDRESS = "address";
	private static final String PARAM_USER_PHONE = "phone";
	private static final String JSP_USER_ADD = "jsp.admin.user.add";
	private static final String JSP_USER_LIST = "jsp.admin.user.list";

	public UserAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<User> userService = null;
		try {
			userService = UserService.getInstance();
		} catch (LogicException e) {
			LOG.error("User add action failed", e);
			return getForwardError();
		}
		String forward = null;
		String email = request.getParameter(PARAM_USER_EMAIL);
		String password = request.getParameter(PARAM_USER_PASSWORD);
		String admin = request.getParameter(PARAM_USER_ADMIN);
		String name = request.getParameter(PARAM_USER_NAME);
		String surname = request.getParameter(PARAM_USER_SURNAME);
		String passport = request.getParameter(PARAM_USER_PASSPORT);
		String address = request.getParameter(PARAM_USER_ADDRESS);
		String phone = request.getParameter(PARAM_USER_PHONE);
		UserBuilder builder = new UserBuilder(message);
		if (!isValidToken(request)) {
			forward = toUserList(request, userService, message);
		} else if (builder.email(email).password(password).admin(admin)
				.name(name).surname(surname).passportNumber(passport)
				.address(address).phone(phone).build()) {
			User user = (User) builder.getEntity();
			try {
				userService.save(user, message);
				forward = toUserList(request, userService, message);
			} catch (LogicException e) {
				LOG.error("User savinf failed", e);
				forward = toUserAdd();
			}
		} else {
			forward = toUserAdd();
		}
		setMessages(request, message);
		return forward;
	}

	private String toUserAdd() {
		return resources.getString(JSP_USER_ADD);
	}

	private String toUserList(HttpServletRequest request,
			IService<User> userService, MessageManager message) {
		try {
			List<User> list = userService.list(message);
			request.setAttribute(PARAM_USER_LIST, list);
		} catch (LogicException e) {
			LOG.error("User list getting failed", e);
		}
		return resources.getString(JSP_USER_LIST);
	}
}
