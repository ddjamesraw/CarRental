package com.andreev.rental.command.administration;

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

public class DetailToAddAction extends AbstractAction {
	
	private static final Logger LOG = Logger.getLogger(DetailToAddAction.class);
	
	private static final String PARAM_ORDER_GET = "orderById";
	private static final String PARAM_ORDER_ID = "orderId";
	private static final String JSP_DETAIL_ADD = "jsp.admin.detail.add";
	
	private RegexValidator idValidator;

	public DetailToAddAction() {
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
			LOG.error("Detail to edit action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_ORDER_ID);
		if(idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Order order = orderService.get(id, message);
				request.setAttribute(PARAM_ORDER_GET, order);
			} catch (LogicException e) {
				LOG.error("Order finding failed", e);
				setMessages(request, message);
			}
		} else {
			message.addMessage(idValidator.getMessage());
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_DETAIL_ADD);
		return forward;
	}

}
