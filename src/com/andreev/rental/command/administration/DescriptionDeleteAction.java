package com.andreev.rental.command.administration;

import java.util.List;

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

public class DescriptionDeleteAction extends AbstractAction {

	private static final Logger LOG = Logger
			.getLogger(DescriptionDeleteAction.class);

	private static final String PARAM_DESCRIPTION_ID = "descriptionId";
	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String JSP_DESCRIPTION_LIST = "jsp.admin.description.list";
	private static final String JSP_DESCRIPTION_GET = "jsp.admin.description.get";

	private RegexValidator idValidator;

	public DescriptionDeleteAction() {
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
			LOG.error("Description delete action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_DESCRIPTION_ID);
		if (!isValidToken(request)) {
			forward = toDescriptionList(request, descriptionService, message);
		} else if (idValidator.isValid(idString)) {
			long id = Long.valueOf(idString);
			try {
				descriptionService.delete(id, message);
				forward = toDescriptionList(request, descriptionService, message);
			} catch (LogicException e) {
				LOG.error("Description deleting failed", e);
				forward = toDescriptionGet();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toDescriptionGet();
		}
		setMessages(request, message);
		return forward;
	}

	private String toDescriptionGet() {
		return resources.getString(JSP_DESCRIPTION_GET);
	}

	private String toDescriptionList(HttpServletRequest request,
			IService<CarDescription> descriptionService, MessageManager message) {
		try {
			List<CarDescription> list = descriptionService.list(message);
			request.setAttribute(PARAM_DESCRIPTION_LIST, list);
		} catch (LogicException e) {
			LOG.error("Description list getting failed", e);
		}
		return resources.getString(JSP_DESCRIPTION_LIST);
	}

}
