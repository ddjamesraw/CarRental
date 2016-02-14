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
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.CarDescription;

public class CarEditAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(CarEditAction.class);

	private static final String PARAM_CAR_GET = "carById";
	private static final String PARAM_CAR_ID = "carId";
	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String PARAM_CAR_AVAILABLE = "available";
	private static final String PARAM_CAR_DESCRIPTION = "description";
	private static final String PARAM_DESCRIPTION_ID = "descriptionId";
	private static final String JSP_CAR_EDIT = "jsp.admin.car.edit";
	private static final String JSP_CAR_GET = "jsp.admin.car.get";

	private RegexValidator idValidator;

	public CarEditAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
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
			LOG.error("Car edit action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_CAR_ID);
		String available = request.getParameter(PARAM_CAR_AVAILABLE);
		String description = request.getParameter(PARAM_CAR_DESCRIPTION);
		String descriptionId = request.getParameter(PARAM_DESCRIPTION_ID);
		if (!isValidToken(request)) {
			forward = toCarGet(request, carService, message, idString);
		} else if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Car car = carService.get(id, message);
				CarBuilder builder = new CarBuilder(car, message);
				if (builder.available(available).description(description)
						.carDescription(descriptionId).build()) {
					car = (Car) builder.getEntity();
					carService.edit(car, message);
					forward = toCarGet(request, car);
				} else {
					forward = toCarEdit(request, descriptionService, message, car);
				}
			} catch (LogicException e) {
				LOG.error("Car editing failed");
				forward = toCarEdit();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toCarEdit();
		}
		setMessages(request, message);
		return forward;
	}

	private String toCarGet() {
		return resources.getString(JSP_CAR_GET);
	}

	private String toCarGet(HttpServletRequest request, Car car) {
		request.setAttribute(PARAM_CAR_GET, car);
		return toCarGet();
	}

	private String toCarGet(HttpServletRequest request,
			IService<Car> carService, MessageManager message, String carId) {
		if (idValidator.isValid(carId)) {
			long id = Long.valueOf(carId);
			try {
				Car car = carService.get(id, message);
				return toCarGet(request, car);
			} catch (LogicException e) {
				LOG.error("Car getting failed", e);
			}
		}
		message.addMessage(idValidator.getMessage());
		return toCarGet();
	}

	private String toCarEdit() {
		return resources.getString(JSP_CAR_EDIT);
	}

	private String toCarEdit(HttpServletRequest request,
			IService<CarDescription> descriptionService,
			MessageManager message, Car car) {
		request.setAttribute(PARAM_CAR_GET, car);
		try {
			List<CarDescription> list = descriptionService.list(message);
			request.setAttribute(PARAM_DESCRIPTION_LIST, list);
		} catch (LogicException e) {
			LOG.error("Car description list getting failed", e);
		}
		return toCarEdit();
	}
}
