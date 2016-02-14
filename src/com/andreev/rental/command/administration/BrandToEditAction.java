package com.andreev.rental.command.administration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.BrandService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Brand;
import com.andreev.rental.resources.IResources;
import com.andreev.rental.resources.CommandResources;

public class BrandToEditAction extends AbstractAction {
	
	private static final Logger LOG = Logger.getLogger(BrandToEditAction.class);
	
	private static final String PARAM_BRAND_GET = "brandById";
	private static final String PARAM_BRAND_ID = "brandId";
	private static final String JSP_BRAND_EDIT = "jsp.admin.brand.edit";
	
	private RegexValidator idValidator;

	public BrandToEditAction() {
		setStatus(EUserStatus.ADMIN);
		IValidatorFactory validatorFactory = ValidatorFactory.getInstance();
		idValidator = validatorFactory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IResources resources = CommandResources.getInstance();
		IService<Brand> brandService = null;
		try {
			brandService = BrandService.getInstance();			
		} catch (LogicException e) {
			LOG.error("Brand to edit action failed", e);
			return getForwardError();
		}
		String idString = request.getParameter(PARAM_BRAND_ID);
		if(idValidator.isValid(idString)) {
			try {
				long id = Long.valueOf(idString);
				Brand brand = brandService.get(id, message);
				request.setAttribute(PARAM_BRAND_GET, brand);
			} catch (LogicException e) {
				LOG.error("Brand getting failed", e);
			}
		} else {
			message.addMessage(idValidator.getMessage());
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_BRAND_EDIT);
		return forward;
	}

}
