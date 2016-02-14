package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.CarService;
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
import com.andreev.rental.model.User;

public class OrderToEditAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(OrderToEditAction.class);

	private static final String PARAM_ORDER_GET = "orderById";
	private static final String PARAM_ORDER_ID = "orderId";
	private static final String PARAM_CAR_LIST = "carList";
	private static final String PARAM_USER_LIST = "userList";
	private static final String PARAM_ORDER_STATUS_LIST = "statusList";
	private static final String JSP_ORDER_EDIT = "jsp.admin.order.edit";

	private RegexValidator idValidator;

	public OrderToEditAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Order> orderService = null;
		IService<Car> carService = null;
		IService<User> userService = null;
		try {
			orderService = OrderService.getInstance();
			carService = CarService.getInstance();
			userService = UserService.getInstance();
		} catch (LogicException e) {
			LOG.error("order to edit action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_ORDER_ID);
		if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Order order = orderService.get(id, message);
				request.setAttribute(PARAM_ORDER_GET, order);
				EOrderStatus[] statusList = EOrderStatus.values();
				request.setAttribute(PARAM_ORDER_STATUS_LIST, statusList);
				List<Car> carList = carService.list(message);
				request.setAttribute(PARAM_CAR_LIST, carList);
				List<User> userList = userService.list(message);
				request.setAttribute(PARAM_USER_LIST, userList);
			} catch (LogicException e) {
				LOG.error("To edit order failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_ORDER_EDIT);
		return forward;
	}

}
