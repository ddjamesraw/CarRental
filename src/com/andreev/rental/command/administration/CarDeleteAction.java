package com.andreev.rental.command.administration;

import java.util.List;

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

public class CarDeleteAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(CarDeleteAction.class);
	
	private static final String PARAM_CAR_ID = "carId";
	private static final String PARAM_CAR_LIST = "carList";
	private static final String JSP_CAR_LIST = "jsp.admin.car.list";
	private static final String JSP_CAR_GET = "jsp.admin.car.get";

	private RegexValidator idValidator;

	public CarDeleteAction() {
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
			LOG.error("Car delete action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_CAR_ID);
		if(!isValidToken(request)) {
			forward = toCarList(request, carService, message);
		} else if(idValidator.isValid(idString)) {
			long id = Long.valueOf(idString);
			try {
				carService.delete(id, message);
				forward = toCarList(request, carService, message);
			} catch (LogicException e) {
				LOG.error("Car deleting failed", e);
				forward = toCarGet();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toCarGet();
		}
		setMessages(request, message);
		return forward;
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
	
	private String toCarGet() {
		return resources.getString(JSP_CAR_GET);
	}

}
