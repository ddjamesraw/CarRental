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
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.CarModel;

public class DescriptionEditAction extends AbstractAction {

	private static final Logger LOG = Logger
			.getLogger(DescriptionEditAction.class);

	private static final String PARAM_DESCRIPTION_GET = "descriptionById";
	private static final String PARAM_DESCRIPTION_ID = "descriptionId";
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
	private static final String JSP_DESCRIPTION_EDIT = "jsp.admin.description.edit";
	private static final String JSP_DESCRIPTION_GET = "jsp.admin.description.get";

	private RegexValidator idValidator;

	public DescriptionEditAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
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
			LOG.error("Description edit action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_DESCRIPTION_ID);
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
		if (!isValidToken(request)) {
			forward = toDescriptionGet(request, descriptionService, message,
					idString);
		} else if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				CarDescription carDescription = descriptionService.get(id,
						message);
				CarDescriptionBuilder builder = new CarDescriptionBuilder(
						carDescription, message);
				if (builder.price(price).doors(doors).seats(seats)
						.consumption(consumption).airCondition(airCondition)
						.airBags(airBags).automatic(automatic)
						.description(description).imgUrl(imgUrl).model(modelId)
						.build()) {
					carDescription = (CarDescription) builder.getEntity();
					descriptionService.edit(carDescription, message);
					forward = toDescriptionGet(request, carDescription);
				} else {
					forward = toDescriptionEdit(request, modelService, message,
							carDescription);
				}
			} catch (LogicException e) {
				LOG.error("Description editing failed", e);
				forward = toDescriptionEdit();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toDescriptionEdit();
		}
		setMessages(request, message);
		return forward;
	}

	private String toDescriptionGet() {
		return resources.getString(JSP_DESCRIPTION_GET);
	}

	private String toDescriptionGet(HttpServletRequest request,
			CarDescription description) {
		request.setAttribute(PARAM_DESCRIPTION_GET, description);
		return toDescriptionGet();
	}

	private String toDescriptionGet(HttpServletRequest request,
			IService<CarDescription> descriptionService,
			MessageManager message, String descriptionId) {
		if (idValidator.isValid(descriptionId)) {
			long id = Long.valueOf(descriptionId);
			CarDescription description;
			try {
				description = descriptionService.get(id, message);
				return toDescriptionGet(request, description);
			} catch (LogicException e) {
				LOG.error("Brand getting failed", e);
			}
		}
		message.addMessage(idValidator.getMessage());
		return toDescriptionGet();
	}

	private String toDescriptionEdit() {
		return resources.getString(JSP_DESCRIPTION_EDIT);
	}

	private String toDescriptionEdit(HttpServletRequest request,
			IService<CarModel> modelService, MessageManager message,
			CarDescription description) {
		request.setAttribute(PARAM_DESCRIPTION_GET, description);
		try {
			List<CarModel> list = modelService.list(message);
			request.setAttribute(PARAM_MODEL_LIST, list);
		} catch (LogicException e) {
			LOG.error("Car model list getting failed", e);
		}
		return resources.getString(JSP_DESCRIPTION_EDIT);
	}

}
