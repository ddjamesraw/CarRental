package com.andreev.rental.command.administration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.UserBuilder;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.UserService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.User;

public class UserEditAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(UserEditAction.class);

	private static final String PARAM_USER_GET = "userById";
	private static final String PARAM_USER_ID = "userId";
	private static final String PARAM_USER_EMAIL = "email";
	private static final String PARAM_USER_ADMIN = "admin";
	private static final String PARAM_USER_NAME = "name";
	private static final String PARAM_USER_SURNAME = "surname";
	private static final String PARAM_USER_PASSPORT = "passport";
	private static final String PARAM_USER_ADDRESS = "address";
	private static final String PARAM_USER_PHONE = "phone";
	private static final String JSP_USER_EDIT = "jsp.admin.user.edit";
	private static final String JSP_USER_GET = "jsp.admin.user.get";

	private RegexValidator idValidator;

	public UserEditAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<User> userService = null;
		try {
			userService = UserService.getInstance();
		} catch (LogicException e) {
			LOG.error("User edit action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_USER_ID);
		String email = request.getParameter(PARAM_USER_EMAIL);
		String admin = request.getParameter(PARAM_USER_ADMIN);
		String name = request.getParameter(PARAM_USER_NAME);
		String surname = request.getParameter(PARAM_USER_SURNAME);
		String passport = request.getParameter(PARAM_USER_PASSPORT);
		String address = request.getParameter(PARAM_USER_ADDRESS);
		String phone = request.getParameter(PARAM_USER_PHONE);
		if (!isValidToken(request)) {
			forward = toUserGet(request, userService, message, idString);
		} else if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				User user = userService.get(id, message);
				UserBuilder builder = new UserBuilder(user, message);
				if (builder.email(email).admin(admin).name(name)
						.surname(surname).passportNumber(passport)
						.address(address).phone(phone).build()) {
					user = (User) builder.getEntity();
					userService.edit(user, message);
					forward = toUserGet(request, user);
				} else {
					forward = toUserEdit(request, user);
				}
			} catch (LogicException e) {
				LOG.error("User editing failed", e);
				forward = toUserEdit();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toUserEdit();
		}
		setMessages(request, message);
		return forward;
	}

	private String toUserEdit() {
		return resources.getString(JSP_USER_EDIT);
	}

	private String toUserEdit(HttpServletRequest request, User user) {
		request.setAttribute(PARAM_USER_GET, user);
		return toUserEdit();
	}

	private String toUserGet() {
		return resources.getString(JSP_USER_GET);
	}

	private String toUserGet(HttpServletRequest request, User user) {
		request.setAttribute(PARAM_USER_GET, user);
		return toUserGet();
	}

	private String toUserGet(HttpServletRequest request,
			IService<User> userService, MessageManager message, String userId) {
		if (idValidator.isValid(userId)) {
			long id = Long.valueOf(userId);
			try {
				User user = userService.get(id, message);
				return toUserGet(request, user);
			} catch (LogicException e) {
				LOG.error("User getting failed", e);
				return toUserGet();
			}
		}
		message.addMessage(idValidator.getMessage());
		return toUserEdit();
	}

}
