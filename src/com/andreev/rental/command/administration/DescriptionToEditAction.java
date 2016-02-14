package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.DescriptionService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.ModelService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.CarModel;

public class DescriptionToEditAction extends AbstractAction {
	
	private static final Logger LOG = Logger.getLogger(DescriptionToEditAction.class);
	
	private static final String PARAM_DESCRIPTION_GET = "descriptionById";
	private static final String PARAM_DESCRIPTION_ID = "descriptionId";
	private static final String PARAM_MODEL_LIST = "modelList";
	private static final String JSP_DESCRIPTION_EDIT = "jsp.admin.description.edit";
	
	private RegexValidator idValidator;

	public DescriptionToEditAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarDescription> descriptionService = null;
		IService<CarModel> modelAdministration = null;
		try {
			descriptionService = DescriptionService.getInstance();
			modelAdministration = ModelService.getInstance();
		} catch (LogicException e) {
			LOG.error("Description to edit action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_DESCRIPTION_ID);
		if(idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				CarDescription description = descriptionService.get(id, message);
				request.setAttribute(PARAM_DESCRIPTION_GET, description);
				List<CarModel> list = modelAdministration.list(message);
				request.setAttribute(PARAM_MODEL_LIST, list);
			} catch (LogicException e) {
				LOG.error("Description getting failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_DESCRIPTION_EDIT);
		return forward;
	}

}
