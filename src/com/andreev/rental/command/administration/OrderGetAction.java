package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.DetailService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.OrderService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.OrderDetail;

public class OrderGetAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(OrderGetAction.class);

	private static final String PARAM_ORDER_GET = "orderById";
	private static final String PARAM_ORDER_ID = "orderId";
	private static final String PARAM_DETAIL_LIST = "detailList";
	private static final String JSP_ORDER_GET = "jsp.admin.order.get";

	private RegexValidator idValidator;

	public OrderGetAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Order> orderService = null;
		IService<OrderDetail> detailService = null;
		try {
			orderService = OrderService.getInstance();
			detailService = DetailService.getInstance();
		} catch (LogicException e) {
			LOG.error("Order get action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_ORDER_ID);
		if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Order order = orderService.get(id, message);
				request.setAttribute(PARAM_ORDER_GET, order);
				List<OrderDetail> list = detailService.list(order, message);
				request.setAttribute(PARAM_DETAIL_LIST, list);
			} catch (LogicException e) {
				LOG.error("Ordr getting failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_ORDER_GET);
		return forward;
	}
}
