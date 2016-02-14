package com.andreev.rental.command.authentication;

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
import com.andreev.rental.resources.IResources;
import com.andreev.rental.resources.CommandResources;

public class OrderDetailAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(OrderDetailAction.class);
	
	private static final String PARAM_ORDER_GET = "orderById";
	private static final String PARAM_ORDER_ID = "orderId";
	private static final String PARAM_DETAIL_LIST = "detailList";
	private static final String JSP_ORDER_DETAIL = "jsp.authentication.detail";
	
	private RegexValidator idValidator;

	public OrderDetailAction() {
		setStatus(EUserStatus.REGISTERED);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		IResources resources = CommandResources.getInstance();
		IService<Order> orderService = null;
		IService<OrderDetail> detailService = null;
		try {
			orderService = OrderService.getInstance();	
			detailService = DetailService.getInstance();
		} catch (LogicException e) {
			LOG.error("Order detail action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_ORDER_ID);
		MessageManager message = new MessageManager();
		if(idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Order order = orderService.get(id, message);
				request.setAttribute(PARAM_ORDER_GET, order);
				List<OrderDetail> list = detailService.list(order, message);
				request.setAttribute(PARAM_DETAIL_LIST, list);
			} catch (LogicException e) {
				LOG.error("Order finding failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_ORDER_DETAIL);
		return forward;
	}
}
