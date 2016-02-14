package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.CarModelBuilder;
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

public class ModelEditAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(ModelEditAction.class);

	private static final String PARAM_MODEL_GET = "modelById";
	private static final String PARAM_MODEL_ID = "modelId";
	private static final String PARAM_BRAND_LIST = "brandList";
	private static final String PARAM_MODEL_NAME = "name";
	private static final String PARAM_BRAND_ID = "brandId";
	private static final String JSP_MODEL_EDIT = "jsp.admin.model.edit";
	private static final String JSP_MODEL_GET = "jsp.admin.model.get";

	private RegexValidator idValidator;

	public ModelEditAction() {
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
			LOG.error("Model edit action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_MODEL_ID);
		String name = request.getParameter(PARAM_MODEL_NAME);
		String brandId = request.getParameter(PARAM_BRAND_ID);
		if(!isValidToken(request)) {
			forward = toModelGet(request, modelService, message, idString);
		} else if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				CarModel model = modelService.get(id, message);
				CarModelBuilder builder = new CarModelBuilder(model, message);
				if (builder.name(name).brand(brandId).build()) {
					model = (CarModel) builder.getEntity();
					modelService.edit(model, message);
					forward = toModelGet(request, model);
				} else {
					forward = toModelEdit(request, brandService, message, model);
				}
			} catch (LogicException e) {
				LOG.error("Model editing failed", e);
				forward = toModelEdit();
			}
		} else {
			forward = resources.getString(JSP_MODEL_EDIT);
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		return forward;
	}
	
	private String toModelGet() {
		return resources.getString(JSP_MODEL_GET);
	}

	private String toModelGet(HttpServletRequest request, CarModel model) {
		request.setAttribute(PARAM_MODEL_GET, model);
		return toModelGet();
	}

	private String toModelGet(HttpServletRequest request,
			IService<CarModel> modelService, MessageManager message, String modelId) {
		if (idValidator.isValid(modelId)) {
			long id = Long.valueOf(modelId);
			try {
				CarModel model = modelService.get(id, message);
				return toModelGet(request, model);
			} catch (LogicException e) {
				LOG.error("Model getting failed", e);
			}
		}
		message.addMessage(idValidator.getMessage());
		return toModelGet();
	}

	private String toModelEdit() {
		return resources.getString(JSP_MODEL_GET);
	}

	private String toModelEdit(HttpServletRequest request,
			IService<Brand> brandService,
			MessageManager message, CarModel model) {
		request.setAttribute(PARAM_MODEL_GET, model);
		try {
			List<Brand> list = brandService.list(message);
			request.setAttribute(PARAM_BRAND_LIST, list);
		} catch (LogicException e) {
			LOG.error("Brand list getting failed", e);
		}
		return toModelEdit();
	}

}
