package com.andreev.rental.command.administration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.DescriptionService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.CarDescription;

public class DescriptionGetAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(DescriptionGetAction.class);
	
	private static final String PARAM_description_GET = "descriptionById";
	private static final String PARAM_description_ID = "descriptionId";
	private static final String JSP_description_GET = "jsp.admin.description.get";

	private RegexValidator idValidator;

	public DescriptionGetAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarDescription> descriptionService = null;
		try {
			descriptionService = DescriptionService.getInstance();			
		} catch (LogicException e) {
			LOG.error("Car description get action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_description_ID);
		if(idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				CarDescription description = descriptionService.get(id, message);
				request.setAttribute(PARAM_description_GET, description);
			} catch (LogicException e) {
				LOG.error("Description getting failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_description_GET);
		return forward;
	}

}
