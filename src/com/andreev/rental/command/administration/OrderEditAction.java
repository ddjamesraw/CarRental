package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.OrderBuilder;
import com.andreev.rental.logic.service.CarService;
import com.andreev.rental.logic.service.DetailService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.OrderService;
import com.andreev.rental.logic.service.UserService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.EOrderStatus;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.OrderDetail;
import com.andreev.rental.model.User;

public class OrderEditAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(OrderEditAction.class);

	private static final String PARAM_ORDER_GET = "orderById";
	private static final String PARAM_ORDER_ID = "orderId";
	private static final String PARAM_CAR_LIST = "carList";
	private static final String PARAM_USER_LIST = "userList";
	private static final String PARAM_ORDER_STATUS_LIST = "statusList";
	private static final String PARAM_DETAIL_LIST = "detailList";
	private static final String PARAM_ORDER_STATUS = "status";
	private static final String PARAM_ORDER_FROM = "from";
	private static final String PARAM_ORDER_TO = "to";
	private static final String PARAM_ORDER_INFO = "info";
	private static final String PARAM_USER_ID = "userId";
	private static final String PARAM_CAR_ID = "carId";
	private static final String JSP_ORDER_EDIT = "jsp.admin.order.edit";
	private static final String JSP_ORDER_GET = "jsp.admin.order.get";

	private RegexValidator idValidator;

	public OrderEditAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Order> orderService = null;
		IService<User> userService = null;
		IService<Car> carService = null;
		IService<OrderDetail> detailService = null;
		try {
			orderService = OrderService.getInstance();
			userService = UserService.getInstance();
			carService = CarService.getInstance();
			detailService = DetailService.getInstance();
		} catch (LogicException e) {
			LOG.error("Order edit action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_ORDER_ID);
		String status = request.getParameter(PARAM_ORDER_STATUS);
		String from = request.getParameter(PARAM_ORDER_FROM);
		String to = request.getParameter(PARAM_ORDER_TO);
		String info = request.getParameter(PARAM_ORDER_INFO);
		String userId = request.getParameter(PARAM_USER_ID);
		String carId = request.getParameter(PARAM_CAR_ID);
		if (!isValidToken(request)) {
			forward = toOrderGet(request, orderService, detailService, message,
					idString);
		} else if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Order order = orderService.get(id, message);
				OrderBuilder builder = new OrderBuilder(order, message);
				if (builder.status(status).from(from).to(to).info(info)
						.user(userId).car(carId).build()) {
					order = (Order) builder.getEntity();
					orderService.edit(order, message);
					forward = toOrderGet(request, detailService, message, order);
				} else {
					forward = toOrderEdit(request, carService, userService,
							message, order);
				}
			} catch (LogicException e) {
				LOG.error("Order editing failed", e);
				forward = toOrderEdit();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toOrderEdit();
		}
		setMessages(request, message);
		return forward;
	}

	private String toOrderGet() {
		return resources.getString(JSP_ORDER_GET);
	}

	private String toOrderGet(HttpServletRequest request,
			IService<OrderDetail> detailService, MessageManager message,
			Order order) {
		request.setAttribute(PARAM_ORDER_GET, order);
		try {
			List<OrderDetail> list = detailService.list(order, message);
			request.setAttribute(PARAM_DETAIL_LIST, list);
		} catch (LogicException e) {
			LOG.error("Order list getting failed", e);
		}
		return toOrderGet();
	}

	private String toOrderGet(HttpServletRequest request,
			IService<Order> orderService, IService<OrderDetail> detailService,
			MessageManager message, String orderId) {
		if (idValidator.isValid(orderId)) {
			long id = Long.valueOf(orderId);
			try {
				Order order = orderService.get(id, message);
				return toOrderGet(request, detailService, message, order);
			} catch (LogicException e) {
				LOG.error("Order getting failed", e);
			}
		}
		message.addMessage(idValidator.getMessage());
		return toOrderGet();
	}

	private String toOrderEdit() {
		return resources.getString(JSP_ORDER_EDIT);
	}

	private String toOrderEdit(HttpServletRequest request,
			IService<Car> carService, IService<User> userService,
			MessageManager message, Order order) {
		request.setAttribute(PARAM_ORDER_GET, order);
		try {
			EOrderStatus[] statusList = EOrderStatus.values();
			request.setAttribute(PARAM_ORDER_STATUS_LIST, statusList);
			List<Car> carList = carService.list(message);
			request.setAttribute(PARAM_CAR_LIST, carList);
			List<User> userList = userService.list(message);
			request.setAttribute(PARAM_USER_LIST, userList);
		} catch (LogicException e) {
			LOG.error("To order edit failed", e);
		}
		return toOrderEdit();
	}

}
