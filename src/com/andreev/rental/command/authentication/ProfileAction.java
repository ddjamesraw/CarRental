package com.andreev.rental.command.authentication;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.OrderService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.User;

public class ProfileAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(ProfileAction.class);

	private static final String PARAM_USER = "user";
	private static final String PARAM_ORDER_LIST = "orderList";
	private static final String JSP_PROFILE = "jsp.authentication.profile";

	public ProfileAction() {
		setStatus(EUserStatus.REGISTERED);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Order> orderService = null;
		try {
			orderService = OrderService.getInstance();
		} catch (LogicException e) {
			LOG.error("Order list action failed", e);
			return getForwardError();
		}
		User user = (User) request.getSession().getAttribute(PARAM_USER);
		try {
			List<Order> list = orderService.list(user, message);
			request.setAttribute(PARAM_ORDER_LIST, list);
		} catch (LogicException e) {
			LOG.error("Order list getting failed", e);
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_PROFILE);
		return forward;
	}
}
