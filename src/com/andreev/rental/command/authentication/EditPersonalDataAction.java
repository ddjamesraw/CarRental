package com.andreev.rental.command.authentication;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.Authentication;
import com.andreev.rental.logic.IAuthentication;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.UserBuilder;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.OrderService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.User;

public class EditPersonalDataAction extends AbstractAction {

	private static final Logger LOG = Logger
			.getLogger(EditPersonalDataAction.class);

	private static final String PARAM_BODY = "body";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_SURNAME = "surname";
	private static final String PARAM_ADDRESS = "address";
	private static final String PARAM_PHONE = "phone";
	private static final String PARAM_PASSPORT = "passport";
	private static final String PARAM_USER = "user";
	private static final String PARAM_ORDER_LIST = "orderList";
	private static final String JSP_BODY_EDITPROFILE = "jsp.authentication.editprofile";
	private static final String JSP_BODY_PROFILE = "jsp.authentication.profile";
	private static final String JSP_MAIN = "jsp.main";

	public EditPersonalDataAction() {
		setStatus(EUserStatus.REGISTERED);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IAuthentication authentication = null;
		IService<Order> orderService = null;
		try {
			authentication = Authentication.getInstance();
			orderService = OrderService.getInstance();
		} catch (LogicException e) {
			LOG.error("Edit profile action failed", e);
			return getForwardError();
		}
		String forward = null;
		String name = request.getParameter(PARAM_NAME);
		String surname = request.getParameter(PARAM_SURNAME);
		String address = request.getParameter(PARAM_ADDRESS);
		String phone = request.getParameter(PARAM_PHONE);
		String passport = request.getParameter(PARAM_PASSPORT);
		User user = (User) request.getSession().getAttribute(PARAM_USER);
		UserBuilder builder = new UserBuilder(user, message);
		if (!isValidToken(request)) {
			forward = toProfile(request, orderService, message, user);
		} else if (builder.name(name).surname(surname).address(address)
				.phone(phone).passportNumber(passport).build()) {
			if (authentication.edit(message, user)) {
				request.getSession().setAttribute(PARAM_USER, user);
				forward = toProfile(request, orderService, message, user);
			} else {
				forward = toEditProfile(request);
			}
		} else {
			forward = toEditProfile(request);
		}
		setMessages(request, message);
		return forward;
	}

	private String toProfile(HttpServletRequest request,
			IService<Order> orderService, MessageManager message, User user) {
		try {
			List<Order> list = orderService.list(user, message);
			request.setAttribute(PARAM_ORDER_LIST, list);
		} catch (LogicException e) {
			LOG.error("Order list getting failed", e);
		}
		return resources.getString(JSP_BODY_PROFILE);
	}

	private String toEditProfile(HttpServletRequest request) {
		String body = resources.getString(JSP_BODY_EDITPROFILE);
		request.setAttribute(PARAM_BODY, body);
		return resources.getString(JSP_MAIN);
	}
}
