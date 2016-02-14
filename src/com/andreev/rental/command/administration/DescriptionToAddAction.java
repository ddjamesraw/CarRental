package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.ModelService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.CarModel;

public class DescriptionToAddAction extends AbstractAction {
	
	private static final Logger LOG = Logger.getLogger(DescriptionToAddAction.class);
	
	private static final String PARAM_MODEL_LIST = "modelList";
	private static final String JSP_DESCRIPTION_ADD = "jsp.admin.description.add";

	public DescriptionToAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarModel> modelService = null;
		try {
			modelService = ModelService.getInstance();
		} catch (LogicException e) {
			LOG.error("Description to add action failed", e);
			return getForwardError();
		}
		try {
			List<CarModel> list = modelService.list(message);
			request.setAttribute(PARAM_MODEL_LIST, list);
		} catch (LogicException e) {
			LOG.error("Car model list getting failed", e);
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_DESCRIPTION_ADD);
		return forward;
	}

}
