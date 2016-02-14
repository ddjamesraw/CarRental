package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
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

public class CarToEditAction extends AbstractAction {
	
	private static final Logger LOG = Logger.getLogger(CarToEditAction.class);
	
	private static final String PARAM_CAR_GET = "carById";
	private static final String PARAM_CAR_ID = "carId";
	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String JSP_CAR_EDIT = "jsp.admin.car.edit";
	
	private RegexValidator idValidator;

	public CarToEditAction() {
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
			LOG.error("Car to edit action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_CAR_ID);
		if(idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Car car = carService.get(id, message);
				request.setAttribute(PARAM_CAR_GET, car);
				List<CarDescription> list = descriptionService.list(message);
				request.setAttribute(PARAM_DESCRIPTION_LIST, list);
			} catch (LogicException e) {
				LOG.error("Car to edit failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		String forward = resources.getString(JSP_CAR_EDIT);
		setMessages(request, message);
		return forward;
	}

}
