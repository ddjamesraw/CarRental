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
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarModel;

public class ModelAddAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(ModelAddAction.class);

	private static final String PARAM_MODEL_LIST = "modelList";
	private static final String PARAM_BRAND_LIST = "brandList";
	private static final String PARAM_MODEL_NAME = "name";
	private static final String PARAM_BRAND_ID = "brandId";
	private static final String JSP_MODEL_ADD = "jsp.admin.model.add";
	private static final String JSP_MODEL_LIST = "jsp.admin.model.list";

	public ModelAddAction() {
		setStatus(EUserStatus.ADMIN);
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
			LOG.error("Model add action failed", e);
			return getForwardError();
		}
		String forward = null;
		String name = request.getParameter(PARAM_MODEL_NAME);
		String brandId = request.getParameter(PARAM_BRAND_ID);
		CarModelBuilder builder = new CarModelBuilder(message);
		CarModel model = null;
		if (!isValidToken(request)) {
			forward = toModelList(request, modelService, message);
		} else if (builder.name(name).brand(brandId).build()) {
			model = (CarModel) builder.getEntity();
			try {
				modelService.save(model, message);
				forward = toModelList(request, modelService, message);
			} catch (LogicException e) {
				LOG.error("Model saving failed", e);
				forward = toModelAdd(request, brandService, message);
			}
		} else {
			forward = toModelAdd(request, brandService, message);
		}
		setMessages(request, message);
		return forward;
	}

	private String toModelAdd(HttpServletRequest request,
			IService<Brand> brandService, MessageManager message) {
		try {
			List<Brand> list = brandService.list(message);
			request.setAttribute(PARAM_BRAND_LIST, list);
		} catch (LogicException e) {
			LOG.error("Brand list getting failed", e);
		}
		return resources.getString(JSP_MODEL_ADD);
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

}
