package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.CarDescriptionBuilder;
import com.andreev.rental.logic.service.DescriptionService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.service.ModelService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.CarModel;

public class DescriptionAddAction extends AbstractAction {

	private static final Logger LOG = Logger
			.getLogger(DescriptionAddAction.class);

	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String PARAM_MODEL_LIST = "modelList";
	private static final String PARAM_DESCRIPTION_PRICE = "price";
	private static final String PARAM_DESCRIPTION_DOORS = "doors";
	private static final String PARAM_DESCRIPTION_SEATS = "seats";
	private static final String PARAM_DESCRIPTION_CONSUMPTION = "consumption";
	private static final String PARAM_DESCRIPTION_AIR_CONDITION = "airCondition";
	private static final String PARAM_DESCRIPTION_AIR_BAGS = "airBags";
	private static final String PARAM_DESCRIPTION_AUTOMATIC = "automatic";
	private static final String PARAM_DESCRIPTION_DESCRIPTION = "description";
	private static final String PARAM_DESCRIPTION_IMG = "imgUrl";
	private static final String PARAM_MODEL_ID = "modelId";
	private static final String JSP_DESCRIPTION_ADD = "jsp.admin.description.add";
	private static final String JSP_DESCRIPTION_LIST = "jsp.admin.description.list";

	public DescriptionAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarDescription> descriptionService = null;
		IService<CarModel> modelService = null;
		try {
			descriptionService = DescriptionService.getInstance();
			modelService = ModelService.getInstance();
		} catch (LogicException e) {
			LOG.error("Description add action failed", e);
			return getForwardError();
		}
		String forward = null;
		String price = request.getParameter(PARAM_DESCRIPTION_PRICE);
		String doors = request.getParameter(PARAM_DESCRIPTION_DOORS);
		String seats = request.getParameter(PARAM_DESCRIPTION_SEATS);
		String consumption = request
				.getParameter(PARAM_DESCRIPTION_CONSUMPTION);
		String airCondition = request
				.getParameter(PARAM_DESCRIPTION_AIR_CONDITION);
		String airBags = request.getParameter(PARAM_DESCRIPTION_AIR_BAGS);
		String automatic = request.getParameter(PARAM_DESCRIPTION_AUTOMATIC);
		String description = request
				.getParameter(PARAM_DESCRIPTION_DESCRIPTION);
		String imgUrl = request.getParameter(PARAM_DESCRIPTION_IMG);
		String modelId = request.getParameter(PARAM_MODEL_ID);
		CarDescriptionBuilder builder = new CarDescriptionBuilder(message);
		if (!isValidToken(request)) {
			forward = toDescriptionList(request, descriptionService, message);
		} else if (builder.price(price).doors(doors).seats(seats)
				.consumption(consumption).airCondition(airCondition)
				.airBags(airBags).automatic(automatic).description(description)
				.imgUrl(imgUrl).model(modelId).build()) {
			CarDescription carDescription = (CarDescription) builder
					.getEntity();
			try {
				descriptionService.save(carDescription, message);
				forward = toDescriptionList(request, descriptionService, message);
			} catch (LogicException e) {
				LOG.error("Description add failed", e);
				forward = toDescriptionAdd(request, modelService, message);
			}
		} else {
			forward = toDescriptionAdd(request, modelService, message);
		}
		setMessages(request, message);
		return forward;
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

	private String toDescriptionAdd(HttpServletRequest request,
			IService<CarModel> modelService, MessageManager message) {
		try {
			List<CarModel> list = modelService.list(message);
			request.setAttribute(PARAM_MODEL_LIST, list);
		} catch (LogicException e) {
			LOG.error("Car model list getting failed", e);
		}
		return resources.getString(JSP_DESCRIPTION_ADD);
	}

}
