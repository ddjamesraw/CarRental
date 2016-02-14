package com.andreev.rental.command.administration;

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

public class ModelGetAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(ModelGetAction.class);
	
	private static final String PARAM_model_GET = "modelById";
	private static final String PARAM_model_ID = "modelId";
	private static final String JSP_model_GET = "jsp.admin.model.get";
	
	private RegexValidator idValidator;

	public ModelGetAction() {
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
			LOG.error("Model get action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_model_ID);
		if(idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				CarModel model = modelService.get(id, message);
				request.setAttribute(PARAM_model_GET, model);
			} catch (LogicException e) {
				LOG.error("Model getting failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_model_GET);
		return forward;
	}
}
