package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.CarBuilder;
import com.andreev.rental.logic.service.CarService;
import com.andreev.rental.logic.service.DescriptionService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.CarDescription;

public class CarAddAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(CarAddAction.class);

	private static final String PARAM_CAR_LIST = "carList";
	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String PARAM_CAR_AVAILABLE = "available";
	private static final String PARAM_CAR_DESCRIPTION = "description";
	private static final String PARAM_DESCRIPTION_ID = "descriptionId";
	private static final String JSP_CAR_ADD = "jsp.admin.car.add";
	private static final String JSP_CAR_LIST = "jsp.admin.car.list";

	public CarAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Car> carService = null;
		IService<CarDescription> descriptionService = null;
		try {
			carService = CarService.getInstance();
			descriptionService = DescriptionService.getInstance();
		} catch (LogicException e) {
			LOG.error("Car add action failed", e);
			return getForwardError();
		}
		String forward = null;
		String available = request.getParameter(PARAM_CAR_AVAILABLE);
		String description = request.getParameter(PARAM_CAR_DESCRIPTION);
		String descriptionId = request.getParameter(PARAM_DESCRIPTION_ID);
		CarBuilder builder = new CarBuilder(message);
		if (!isValidToken(request)) {
			forward = toCarList(request, carService, message);
		} else if (builder.available(available).description(description)
				.carDescription(descriptionId).build()) {
			Car car = (Car) builder.getEntity();
			try {
				carService.save(car, message);
				forward = toCarList(request, carService, message);
			} catch (LogicException e) {
				LOG.error("Car saving failed", e);
				forward = toCarAdd(request, descriptionService, message);
			}
		} else {
			forward = toCarAdd(request, descriptionService, message);
		}
		setMessages(request, message);
		return forward;
	}

	private String toCarAdd(HttpServletRequest request,
			IService<CarDescription> descriptionService, MessageManager message) {
		try {
			List<CarDescription> list = descriptionService.list(message);
			request.setAttribute(PARAM_DESCRIPTION_LIST, list);
		} catch (LogicException e) {
			LOG.error("Car description list getting failed", e);
		}
		return resources.getString(JSP_CAR_ADD);
	}

	private String toCarList(HttpServletRequest request,
			IService<Car> carService, MessageManager message) {
		try {
			List<Car> list = carService.list(message);
			request.setAttribute(PARAM_CAR_LIST, list);
		} catch (LogicException e) {
			LOG.error("Car list getting failed", e);
		}
		return resources.getString(JSP_CAR_LIST);
	}

}
