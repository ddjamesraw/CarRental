package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.OrderDetailBuilder;
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

public class DetailAddAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(DetailAddAction.class);

	private static final String PARAM_ORDER_GET = "orderById";
	private static final String PARAM_DETAIL_LIST = "detailList";
	private static final String PARAM_ORDER_ID = "orderId";
	private static final String PARAM_DETAIL_PRICE = "price";
	private static final String PARAM_DETAIL_DESCRIPTION = "description";
	private static final String JSP_DETAIL_ADD = "jsp.admin.detail.add";
	private static final String JSP_ORDER_GET = "jsp.admin.order.get";

	private RegexValidator idValidator;

	public DetailAddAction() {
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
			LOG.error("Detail add action failed", e);
			return getForwardError();
		}
		String forward = null;
		String orderId = request.getParameter(PARAM_ORDER_ID);
		String price = request.getParameter(PARAM_DETAIL_PRICE);
		String description = request.getParameter(PARAM_DETAIL_DESCRIPTION);
		OrderDetailBuilder builder = new OrderDetailBuilder(message);
		if (!isValidToken(request)) {
			forward = toOrderGer(request, detailService, orderService, message,
					orderId);
		} else if (builder.orderId(orderId).price(price)
				.description(description).build()) {
			OrderDetail detail = (OrderDetail) builder.getEntity();
			try {
				detailService.save(detail, message);
				forward = toOrderGet(request, orderService, detailService,
						message, detail.getOrderId());
			} catch (LogicException e) {
				LOG.error("Detail saving failed", e);
				forward = toDetailAdd(request, orderService, message,
						detail.getOrderId());
			}
		} else {
			forward = toDetailAdd(request, orderService, message, orderId);
		}
		setMessages(request, message);
		return forward;
	}

	private String toDetailAdd() {
		return resources.getString(JSP_DETAIL_ADD);
	}

	private String toDetailAdd(HttpServletRequest request,
			IService<Order> orderService, MessageManager message, long id) {
		try {
			Order order = orderService.get(id, message);
			request.setAttribute(PARAM_ORDER_GET, order);
		} catch (LogicException e) {
			LOG.error("Order finding failed", e);
			setMessages(request, message);
		}
		return toDetailAdd();
	}

	private String toDetailAdd(HttpServletRequest request,
			IService<Order> orderService, MessageManager message, String orderId) {
		if (idValidator.isValid(orderId)) {
			long id = Long.valueOf(orderId);
			return toDetailAdd(request, orderService, message, id);
		}
		message.addMessage(idValidator.getMessage());
		return toDetailAdd();
	}

	private String toOrderGet() {
		return resources.getString(JSP_ORDER_GET);
	}

	private String toOrderGet(HttpServletRequest request,
			IService<Order> orderService, IService<OrderDetail> detailService,
			MessageManager message, long orderId) {
		try {
			Order order = orderService.get(orderId, message);
			request.setAttribute(PARAM_ORDER_GET, order);
			List<OrderDetail> list = detailService.list(order, message);
			request.setAttribute(PARAM_DETAIL_LIST, list);
		} catch (LogicException e) {
			LOG.error("Order getting failed", e);
		}
		return toOrderGet();
	}

	private String toOrderGer(HttpServletRequest request,
			IService<OrderDetail> detailService, IService<Order> orderService,
			MessageManager message, String orderId) {
		if (idValidator.isValid(orderId)) {
			long id = Long.valueOf(orderId);
			return toOrderGet(request, orderService, detailService, message, id);
		}
		message.addMessage(idValidator.getMessage());
		return toOrderGet();
	}

}
