package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.OrderService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Order;

public class OrderDeleteAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(OrderDeleteAction.class);

	private static final String PARAM_ORDER_ID = "orderId";
	private static final String PARAM_ORDER_LIST = "orderList";
	private static final String JSP_ORDER_LIST = "jsp.admin.order.list";
	private static final String JSP_ORDER_GET = "jsp.admin.order.get";

	private RegexValidator idValidator;

	public OrderDeleteAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Order> orderService = null;
		try {
			orderService = OrderService.getInstance();
		} catch (LogicException e) {
			LOG.error("Order delete action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_ORDER_ID);
		if (!isValidToken(request)) {
			forward = toOrderList(request, orderService, message);
		} else if (idValidator.isValid(idString)) {
			long id = Long.valueOf(idString);
			try {
				orderService.delete(id, message);
				forward = toOrderList(request, orderService, message);
			} catch (LogicException e) {
				LOG.error("Order deleting failed", e);
				forward = toOrderGet();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toOrderGet();
		}
		setMessages(request, message);
		return forward;
	}

	private String toOrderList(HttpServletRequest request,
			IService<Order> orderService, MessageManager message) {
		try {
			List<Order> list = orderService.list(message);
			request.setAttribute(PARAM_ORDER_LIST, list);
		} catch (LogicException e) {
			LOG.error("Order list getting failed", e);
		}
		return resources.getString(JSP_ORDER_LIST);
	}

	private String toOrderGet() {
		return resources.getString(JSP_ORDER_GET);
	}

}
