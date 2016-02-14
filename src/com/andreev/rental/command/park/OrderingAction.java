package com.andreev.rental.command.park;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.OrderBuilder;
import com.andreev.rental.logic.service.BrandService;
import com.andreev.rental.logic.service.CarService;
import com.andreev.rental.logic.service.DescriptionService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.OrderService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.User;

public class OrderingAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(OrderingAction.class);

	private static final String PARAM_USER = "user";
	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String PARAM_BRAND_LIST = "criterionList";
	private static final String PARAM_DESCRIPTION_BY_ID = "descriptionById";
	private static final String PARAM_DESCRIPTION_ID = "descriptionId";
	private static final String PARAM_ORDER_FROM = "from";
	private static final String PARAM_ORDER_TO = "to";
	private static final String JSP_ORDERING = "jsp.park.ordering";
	private static final String JSP_PARK_LIST = "jsp.park.list";

	private RegexValidator idValidator;

	public OrderingAction() {
		setStatus(EUserStatus.REGISTERED);
		IValidatorFactory factory = ValidatorFactory.getInstance();
		idValidator = factory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Order> orderService = null;
		IService<Car> carService = null;
		IService<CarDescription> descriptionService = null;
		IService<Brand> brandService = null;
		try {
			orderService = OrderService.getInstance();
			carService = CarService.getInstance();
			descriptionService = DescriptionService.getInstance();
			brandService = BrandService.getInstance();
		} catch (LogicException e) {
			LOG.error("Ordering action failed", e);
			return getForwardError();
		}
		String forward = null;
		String from = request.getParameter(PARAM_ORDER_FROM);
		String to = request.getParameter(PARAM_ORDER_TO);
		String idString = request.getParameter(PARAM_DESCRIPTION_ID);
		User user = (User) request.getSession().getAttribute(PARAM_USER);
		OrderBuilder builder = new OrderBuilder(message);
		Order order = null;
		long descriptionId = 0l;
		if (!isValidToken(request)) {
			forward = toList(request, descriptionService, brandService, message);
		} else if (!idValidator.isValid(idString)) {
			message.addMessage(idValidator.getMessage());
			forward = toList(request, descriptionService, brandService, message);
		} else if (builder.from(from).to(to).build()) {
			order = (Order) builder.getEntity();
			order.setUser(user);
			descriptionId = Long.valueOf(idString);
			try {
				Car car = ((CarService) carService).findByOrderAndDescription(
						order, descriptionId, message);
				if (car != null) {
					order.setCar(car);
					orderService.save(order, message);
				}
				forward = toList(request, descriptionService, brandService,
						message);
			} catch (LogicException e) {
				LOG.error("Order saving failed", e);
				forward = toOrdering(request, descriptionService, message,
						descriptionId);
			}
		} else {
			forward = toOrdering(request, descriptionService, message,
					idString);
		}
		setMessages(request, message);
		return forward;
	}

	private String toOrdering(HttpServletRequest request,
			IService<CarDescription> descriptionService,
			MessageManager message, long descriptionId) {
		try {
			CarDescription description = descriptionService.get(descriptionId,
					message);
			request.setAttribute(PARAM_DESCRIPTION_BY_ID, description);
		} catch (LogicException e) {
			LOG.error("Description finding failed", e);
		}
		return resources.getString(JSP_ORDERING);
	}
	
	private String toOrdering(HttpServletRequest request,
			IService<CarDescription> descriptionService,
			MessageManager message, String descriptionId) {
		long id = Long.valueOf(descriptionId);
		return toOrdering(request, descriptionService, message, id);
	}

	private String toList(HttpServletRequest request,
			IService<CarDescription> descriptionService,
			IService<Brand> brandService, MessageManager message) {
		try {
			List<CarDescription> list = descriptionService.list(message);
			request.setAttribute(PARAM_DESCRIPTION_LIST, list);
		} catch (LogicException e) {
			LOG.error("Car description list getting failed", e);
		}
		try {
			List<Brand> brandList = brandService.list(message);
			request.setAttribute(PARAM_BRAND_LIST, brandList);
		} catch (LogicException e) {
			LOG.error("Brand list getting failed", e);
		}
		return resources.getString(JSP_PARK_LIST);
	}
}
