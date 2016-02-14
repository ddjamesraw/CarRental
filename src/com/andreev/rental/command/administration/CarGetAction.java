package com.andreev.rental.command.administration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.CarService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Car;

public class CarGetAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(CarGetAction.class);

	private static final String PARAM_car_GET = "carById";
	private static final String PARAM_car_ID = "carId";
	private static final String JSP_CAR_GET = "jsp.admin.car.get";

	private RegexValidator idValidator;

	public CarGetAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Car> carService = null;
		try {
			carService = CarService.getInstance();
		} catch (LogicException e) {
			LOG.error("Car get action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_car_ID);
		if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Car car = carService.get(id, message);
				request.setAttribute(PARAM_car_GET, car);
			} catch (LogicException e) {
				LOG.error("Car getting failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_CAR_GET);
		return forward;
	}

}
