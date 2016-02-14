package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.OrderBuilder;
import com.andreev.rental.logic.service.CarService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.OrderService;
import com.andreev.rental.logic.service.UserService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.EOrderStatus;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.User;

public class OrderAddAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(OrderAddAction.class);

	private static final String PARAM_ORDER_LIST = "orderList";
	private static final String PARAM_CAR_LIST = "carList";
	private static final String PARAM_USER_LIST = "userList";
	private static final String PARAM_ORDER_STATUS_LIST = "statusList";
	private static final String PARAM_ORDER_STATUS = "status";
	private static final String PARAM_ORDER_FROM = "from";
	private static final String PARAM_ORDER_TO = "to";
	private static final String PARAM_ORDER_INFO = "info";
	private static final String PARAM_USER_ID = "userId";
	private static final String PARAM_CAR_ID = "carId";
	private static final String JSP_ORDER_ADD = "jsp.admin.order.add";
	private static final String JSP_ORDER_LIST = "jsp.admin.order.list";

	public OrderAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Order> orderService = null;
		IService<User> userService = null;
		IService<Car> carService = null;
		try {
			orderService = OrderService.getInstance();
			userService = UserService.getInstance();
			carService = CarService.getInstance();
		} catch (LogicException e) {
			LOG.error("Order add action failed", e);
			return getForwardError();
		}
		String forward = null;
		String status = request.getParameter(PARAM_ORDER_STATUS);
		String from = request.getParameter(PARAM_ORDER_FROM);
		String to = request.getParameter(PARAM_ORDER_TO);
		String info = request.getParameter(PARAM_ORDER_INFO);
		String userId = request.getParameter(PARAM_USER_ID);
		String carId = request.getParameter(PARAM_CAR_ID);
		OrderBuilder builder = new OrderBuilder(message);
		if(!isValidToken(request)) {
			forward = toOrderList(request, orderService, message);
		} else if (builder.status(status).from(from).to(to).info(info).user(userId)
				.car(carId).build()) {
			Order order = (Order) builder.getEntity();
			try {
				orderService.save(order, message);
				forward = toOrderList(request, orderService, message);
			} catch (LogicException e) {
				LOG.error("Order saving or list getting failed", e);
				forward = toOrderAdd(request, carService, userService, message);
			}
		} else {
			forward = toOrderAdd(request, carService, userService, message);
		}
		setMessages(request, message);
		return forward;
	}
	
	private String toOrderAdd(HttpServletRequest request,
			IService<Car> carService, IService<User> userService, MessageManager message) {
		try {
			EOrderStatus[] statusList = EOrderStatus.values();
			request.setAttribute(PARAM_ORDER_STATUS_LIST, statusList);
			List<Car> carList = carService.list(message);
			request.setAttribute(PARAM_CAR_LIST, carList);
			List<User> userList = userService.list(message);
			request.setAttribute(PARAM_USER_LIST, userList);
		} catch (LogicException e) {
			LOG.error("To edit order failed", e);
		}
		return resources.getString(JSP_ORDER_ADD);
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

}
