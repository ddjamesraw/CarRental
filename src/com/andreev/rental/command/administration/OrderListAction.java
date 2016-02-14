package com.andreev.rental.command.administration;

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

public class OrderListAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(OrderListAction.class);

	private static final String PARAM_ORDER_LIST = "orderList";
	private static final String JSP_ORDER_LIST = "jsp.admin.order.list";

	public OrderListAction() {
		setStatus(EUserStatus.ADMIN);
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
		try {
			List<Order> list = orderService.list(message);
			request.setAttribute(PARAM_ORDER_LIST, list);
		} catch (LogicException e) {
			LOG.error("Order list getting failed", e);
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_ORDER_LIST);
		return forward;
	}
}
