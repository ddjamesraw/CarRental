package com.andreev.rental.command.administration;

import java.util.List;

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

public class BrandDeleteAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(BrandDeleteAction.class);
	
	private static final String PARAM_BRAND_ID = "brandId";
	private static final String PARAM_BRAND_LIST = "brandList";
	private static final String JSP_BRAND_LIST = "jsp.admin.brand.list";
	private static final String JSP_BRAND_GET = "jsp.admin.brand.get";

	private RegexValidator idValidator;

	public BrandDeleteAction() {
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
			LOG.error("Brand delete action failed", e);
			return getForwardError();
		}
		String forward = null;
		String idString = request.getParameter(PARAM_BRAND_ID);
		if(!isValidToken(request)) {
			forward = toBrandList(request, brandService, message);
		} else if(idValidator.isValid(idString)) {
			long id = Long.valueOf(idString);
			try {
				brandService.delete(id, message);
			} catch (LogicException e) {
				LOG.error("Brand deleting failed", e);
				forward = toBrandGet();
			}
			forward = toBrandList(request, brandService, message);
		} else {
			message.addMessage(idValidator.getMessage());
			forward = toBrandGet();
		}
		setMessages(request, message);
		return forward;
	}
	
	private String toBrandGet() {
		return resources.getString(JSP_BRAND_GET);		
	}
	
	private String toBrandList(HttpServletRequest request, IService<Brand> brandService, MessageManager message) {
		try {
			List<Brand> list = brandService.list(message);
			request.setAttribute(PARAM_BRAND_LIST, list);
		} catch (LogicException e) {
			LOG.error("Getting brand list failed", e);
		}
		return resources.getString(JSP_BRAND_LIST);
	}

}
