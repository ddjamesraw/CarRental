package com.andreev.rental.command.administration;

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

public class UserGetAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(UserGetAction.class);

	private static final String PARAM_USER_GET = "userById";
	private static final String PARAM_USER_ID = "userId";
	private static final String JSP_USER_GET = "jsp.admin.user.get";

	private RegexValidator idValidator;

	public UserGetAction() {
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
			LOG.error("User get action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_USER_ID);
		if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				User user = userService.get(id, message);
				request.setAttribute(PARAM_USER_GET, user);
			} catch (LogicException e) {
				LOG.error("USer getting failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_USER_GET);
		return forward;
	}
}
