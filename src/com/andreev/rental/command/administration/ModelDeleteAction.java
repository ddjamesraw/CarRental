package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.ModelService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.CarModel;

public class ModelDeleteAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(ModelDeleteAction.class);
	
	private static final String PARAM_MODEL_ID = "modelId";
	private static final String PARAM_MODEL_LIST = "modelList";
	private static final String JSP_MODEL_LIST = "jsp.admin.model.list";
	private static final String JSP_MODEL_GET = "jsp.admin.model.get";

	private RegexValidator idValidator;

	public ModelDeleteAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarModel> modelService = null;
		try {
			modelService = ModelService.getInstance();		
		} catch (LogicException e) {
			LOG.error("Model delete action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_MODEL_ID);
		if(!isValidToken(request)) {
			forward = toModelList(request, modelService, message);
		} else if(idValidator.isValid(idString)) {
			long id = Long.valueOf(idString);
			try {
				modelService.delete(id, message);
				forward = toModelList(request, modelService, message);
			} catch (LogicException e) {
				LOG.error("Model finding failed", e);
				forward = toModelGet();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toModelGet();
		}
		setMessages(request, message);
		return forward;
	}
	
	private String toModelList(HttpServletRequest request,
			IService<CarModel> modelService, MessageManager message) {
		try {
			List<CarModel> list = modelService.list(message);
			request.setAttribute(PARAM_MODEL_LIST, list);
		} catch (LogicException e) {
			LOG.error("Car model list getting failed", e);
		}
		return resources.getString(JSP_MODEL_LIST);
	}
	
	private String toModelGet() {
		return resources.getString(JSP_MODEL_GET);
	}

}
