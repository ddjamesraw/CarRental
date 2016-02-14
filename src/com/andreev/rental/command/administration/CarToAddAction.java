package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.DescriptionService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.CarDescription;

public class CarToAddAction extends AbstractAction {
	
	private static final Logger LOG = Logger.getLogger(CarToAddAction.class);
	
	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String JSP_CAR_ADD = "jsp.admin.car.add";

	public CarToAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarDescription> descriptionService = null;
		try {
			descriptionService = DescriptionService.getInstance();
		} catch (LogicException e) {
			LOG.error("Car to add action failed", e);
			return getForwardError();
		}
		try {
			List<CarDescription> list = descriptionService.list(message);
			request.setAttribute(PARAM_DESCRIPTION_LIST, list);
		} catch (LogicException e) {
			LOG.error("Car description list getting failed", e);
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_CAR_ADD);
		return forward;
	}

}
