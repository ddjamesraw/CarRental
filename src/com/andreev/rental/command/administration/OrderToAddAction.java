package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.CarService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.UserService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.EOrderStatus;
import com.andreev.rental.model.User;

public class OrderToAddAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(OrderToAddAction.class);

	private static final String PARAM_CAR_LIST = "carList";
	private static final String PARAM_USER_LIST = "userList";
	private static final String PARAM_ORDER_STATUS_LIST = "statusList";
	private static final String JSP_ORDER_ADD = "jsp.admin.order.add";

	public OrderToAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Car> carService = null;
		IService<User> userService = null;
		try {
			carService = CarService.getInstance();
			userService = UserService.getInstance();
		} catch (LogicException e) {
			LOG.error("Order to add action failed", e);
			return getForwardError();
		}
		try {
			EOrderStatus[] statusList = EOrderStatus.values();
			request.setAttribute(PARAM_ORDER_STATUS_LIST, statusList);
			List<Car> carList = carService.list(message);
			request.setAttribute(PARAM_CAR_LIST, carList);
			List<User> userList = userService.list(message);
			request.setAttribute(PARAM_USER_LIST, userList);
		} catch (LogicException e) {
			LOG.error("To add order failed", e);
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_ORDER_ADD);
		return forward;
	}

}
