package com.andreev.rental.command.administration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.BrandBuilder;
import com.andreev.rental.logic.service.BrandService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Brand;

public class BrandEditAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(BrandEditAction.class);

	private static final String PARAM_BRAND_GET = "brandById";
	private static final String PARAM_BRAND_ID = "brandId";
	private static final String PARAM_BRAND_NAME = "name";
	private static final String JSP_BRAND_EDIT = "jsp.admin.brand.edit";
	private static final String JSP_BRAND_GET = "jsp.admin.brand.get";

	private RegexValidator idValidator;

	public BrandEditAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Brand> brandService = null;
		try {
			brandService = BrandService.getInstance();
		} catch (LogicException e) {
			LOG.error("Brand edit action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_BRAND_ID);
		String name = request.getParameter(PARAM_BRAND_NAME);
		if (!isValidToken(request)) {
			forward = toBrandGet(request, brandService, message, idString);
		} else if (idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Brand brand = brandService.get(id, message);
				BrandBuilder builder = new BrandBuilder(brand, message);
				if (builder.name(name).build()) {
					brand = (Brand) builder.getEntity();
					brandService.edit(brand, message);
					forward = toBrandGet(request, brand);
				} else {
					forward = toBrandEdit(request, brand);
				}
			} catch (LogicException e) {
				LOG.error("Brand editing failed", e);
				forward = toBrandEdit();
			}
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toBrandEdit();
		}
		setMessages(request, message);
		return forward;
	}

	private String toBrandEdit() {
		return resources.getString(JSP_BRAND_EDIT);
	}

	private String toBrandEdit(HttpServletRequest request, Brand brand) {
		request.setAttribute(PARAM_BRAND_GET, brand);
		return toBrandEdit();
	}

	private String toBrandGet() {
		return resources.getString(JSP_BRAND_GET);
	}

	private String toBrandGet(HttpServletRequest request, Brand brand) {
		request.setAttribute(PARAM_BRAND_GET, brand);
		return toBrandGet();
	}

	private String toBrandGet(HttpServletRequest request,
			IService<Brand> brandService, MessageManager message, String brandId) {
		if (idValidator.isValid(brandId)) {
			long id = Long.valueOf(brandId);
			Brand brand;
			try {
				brand = brandService.get(id, message);
				return toBrandGet(request, brand);
			} catch (LogicException e) {
				LOG.error("Brand getting failed", e);
			}
		}
		message.addMessage(idValidator.getMessage());
		return toBrandEdit();
	}

}
