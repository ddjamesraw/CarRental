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

public class DetailDeleteAction extends AbstractAction {

	private static final Logger LOG = Logger
			.getLogger(DetailDeleteAction.class);

	private static final String PARAM_ORDER_GET = "orderById";
	private static final String PARAM_DETAIL_LIST = "detailList";
	private static final String PARAM_DETAIL_ID = "detailId";
	private static final String JSP_ORDER_GET = "jsp.admin.order.get";

	private RegexValidator idValidator;

	public DetailDeleteAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<OrderDetail> detailService = null;
		IService<Order> orderService = null;
		try {
			detailService = DetailService.getInstance();
			orderService = OrderService.getInstance();
		} catch (LogicException e) {
			LOG.error("Detail delete action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_DETAIL_ID);
		if (!isValidToken(request)) {
			toOrderGer(request, detailService, orderService, message, idString);
		} else if (idValidator.isValid(idString)) {
			long id = Long.valueOf(idString);
			try {
				OrderDetail detail = detailService.get(id, message);
				long orderId = detail.getOrderId();
				detailService.delete(id, message);
				toOrderGet(request, orderService, detailService, message,
						orderId);
			} catch (LogicException e) {
				LOG.error("Detail getting failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_ORDER_GET);
		return forward;
	}

	private void toOrderGet(HttpServletRequest request,
			IService<Order> orderService, IService<OrderDetail> detailService,
			MessageManager message, long id) {
		try {
			Order order = orderService.get(id, message);
			request.setAttribute(PARAM_ORDER_GET, order);
			List<OrderDetail> list = detailService.list(order, message);
			request.setAttribute(PARAM_DETAIL_LIST, list);
		} catch (LogicException e) {
			LOG.error("Order getting failed", e);
		}
	}

	private void toOrderGer(HttpServletRequest request,
			IService<OrderDetail> detailService, IService<Order> orderService,
			MessageManager message, String detailId) {
		if (idValidator.isValid(detailId)) {
			long id = Long.valueOf(detailId);
			try {
				OrderDetail detail = detailService.get(id, message);
				toOrderGet(request, orderService, detailService, message,
						detail.getOrderId());
			} catch (LogicException e) {
				LOG.error("Order detail getting failed", e);
			}
		}
		message.addMessage(idValidator.getMessage());
	}

}
