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

public class ModelListAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(ModelListAction.class);

	private static final String PARAM_MODEL_LIST = "modelList";
	private static final String JSP_BRAND_LIST = "jsp.admin.model.list";

	public ModelListAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarModel> modelService = null;
		try {
			modelService = ModelService.getInstance();
		} catch (LogicException e) {
			LOG.error("User list action failed", e);
		}
		try {
			List<CarModel> list = modelService.list(message);
			request.setAttribute(PARAM_MODEL_LIST, list);
		} catch (LogicException e) {
			LOG.error("Model list getting failed", e);
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_BRAND_LIST);
		return forward;
	}

}
