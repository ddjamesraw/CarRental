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

public class DescriptionListAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(DescriptionListAction.class);
	
	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String JSP_DESCRIPTION_LIST = "jsp.admin.description.list";

	public DescriptionListAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarDescription> descriptionService = null;
		try {
			descriptionService = DescriptionService.getInstance();
		} catch (LogicException e) {
			LOG.error("Description list action failed", e);
		}
		try {
			List<CarDescription> list = descriptionService.list(message);
			request.setAttribute(PARAM_DESCRIPTION_LIST, list);
		} catch (LogicException e) {
			LOG.error("Description list getting failed", e);
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_DESCRIPTION_LIST);
		return forward;
	}

}
