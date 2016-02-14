package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.UserService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.User;

public class UserDeleteAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(UserDeleteAction.class);

	private static final String PARAM_USER_ID = "userId";
	private static final String PARAM_USER_LIST = "userList";
	private static final String JSP_USER_LIST = "jsp.admin.user.list";
	private static final String JSP_USER_GET = "jsp.admin.user.get";

	private RegexValidator idValidator;

	public UserDeleteAction() {
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
			LOG.error("User delete action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_USER_ID);
		if (!isValidToken(request)) {
			forward = toUserList(request, userService, message);
		} else if (idValidator.isValid(idString)) {
			long id = Long.valueOf(idString);
			try {
				userService.delete(id, message);
				forward = toUserList(request, userService, message);
			} catch (LogicException e) {
				LOG.error("Deleting user failed", e);
				forward = toUserGet();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toUserGet();
		}
		setMessages(request, message);
		return forward;
	}

	private String toUserGet() {
		return resources.getString(JSP_USER_GET);
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
