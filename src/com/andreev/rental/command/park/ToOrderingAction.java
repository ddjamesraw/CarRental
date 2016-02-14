package com.andreev.rental.command.park;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.DescriptionService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.CarDescription;

public class ToOrderingAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(ToOrderingAction.class);

	private static final String PARAM_USER = "user";
	private static final String PARAM_DESCRIPTION_ID = "descriptionId";
	private static final String PARAM_DESCRIPTION_BY_ID = "descriptionById";
	private static final String PARAM_BODY = "body";
	private static final String JSP_ORDERING = "jsp.park.ordering";
	private static final String JSP_LOGIN = "jsp.authentication.login";
	private static final String JSP_MAIN = "jsp.main";
	
	private RegexValidator idValidator;

	public ToOrderingAction() {
		IValidatorFactory factory = ValidatorFactory.getInstance();
		idValidator = factory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarDescription> descriptionService = null;
		try {
			descriptionService = DescriptionService.getInstance();
		} catch (LogicException e) {
			LOG.error("To ordering action failed", e);
			return getForwardError();
		}
		String forward = null;		
		String idString = request.getParameter(PARAM_DESCRIPTION_ID);
		if(request.getSession().getAttribute(PARAM_USER) == null) {
			String body = resources.getString(JSP_LOGIN);
			request.setAttribute(PARAM_BODY, body);
			forward = resources.getString(JSP_MAIN);
		} else if(idValidator.isValid(idString)) {
			forward = resources.getString(JSP_ORDERING);
			long id = Long.valueOf(idString);
			try{
				CarDescription description = descriptionService.get(id, message);
				request.setAttribute(PARAM_DESCRIPTION_BY_ID, description);
			} catch (LogicException e) {
				LOG.error("Description finding failed", e);
			}
		} else {
			forward = resources.getString(JSP_ORDERING);
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		return forward;
	}

}
