package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.UserService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.User;

public class UserListAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(UserListAction.class);

	private static final String PARAM_USER_LIST = "userList";
	private static final String JSP_USER_LIST = "jsp.admin.user.list";

	public UserListAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<User> userService = null;
		try {
			userService = UserService.getInstance();
		} catch (LogicException e) {
			LOG.error("User list action failed", e);
			return getForwardError();
		}
		try {
			List<User> list = userService.list(message);
			request.setAttribute(PARAM_USER_LIST, list);
		} catch (LogicException e) {
			LOG.error("Getting user list failed", e);
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_USER_LIST);
		return forward;
	}
}
