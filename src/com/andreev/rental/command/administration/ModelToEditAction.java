package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.BrandService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.ModelService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarModel;

public class ModelToEditAction extends AbstractAction {
	
	private static final Logger LOG = Logger.getLogger(ModelToEditAction.class);
	
	private static final String PARAM_MODEL_GET = "modelById";
	private static final String PARAM_MODEL_ID = "modelId";
	private static final String PARAM_BRAND_LIST = "brandList";
	private static final String JSP_MODEL_EDIT = "jsp.admin.model.edit";
	
	private RegexValidator idValidator;

	public ModelToEditAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarModel> modelService = null;
		IService<Brand> brandService = null;
		try {
			modelService = ModelService.getInstance();
			brandService = BrandService.getInstance();
		} catch (LogicException e) {
			LOG.error("Model to edit action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_MODEL_ID);
		if(idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				CarModel model = modelService.get(id, message);
				request.setAttribute(PARAM_MODEL_GET, model);
				List<Brand> list = brandService.list(message);
				request.setAttribute(PARAM_BRAND_LIST, list);
			} catch (LogicException e) {
				LOG.error("Getting model or brand list failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_MODEL_EDIT);
		return forward;
	}

}
