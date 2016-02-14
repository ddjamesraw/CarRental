package com.andreev.rental.command.park;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.BrandService;
import com.andreev.rental.logic.service.DescriptionService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarDescription;

public class CritarionListAction extends AbstractAction {

	private static final Logger LOG = Logger
			.getLogger(CritarionListAction.class);

	private static final String PARAM_BRAND_ID = "brandId";
	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String PARAM_BRAND_LIST = "criterionList";
	private static final String JSP_DESCRIPTION_LIST = "jsp.park.list";

	private RegexValidator idValidator;

	public CritarionListAction() {
		IValidatorFactory factory = ValidatorFactory.getInstance();
		idValidator = factory.getValidator(ERegexValidator.ID);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<CarDescription> descriptionService = null;
		IService<Brand> brandService = null;
		try {
			descriptionService = DescriptionService.getInstance();
			brandService = BrandService.getInstance();
		} catch (LogicException e) {
			LOG.error("Description list action failed", e);
		}
		String idString = request.getParameter(PARAM_BRAND_ID);
		if (!idValidator.isValid(idString)) {
			message.addMessage(idValidator.getMessage());
			setMessages(request, message);
			return getForwardError();
		}
		try {
			long id = Long.valueOf(idString);
			Brand brand = brandService.get(id, message);
			if (brand != null) {
				List<CarDescription> list = descriptionService.list(brand,
						message);
				request.setAttribute(PARAM_DESCRIPTION_LIST, list);
			}
			List<Brand> brandList = brandService.list(message);
			request.setAttribute(PARAM_BRAND_LIST, brandList);
		} catch (LogicException e) {
			LOG.error("Description list getting failed", e);
			setMessages(request, message);
		}
		String forward = resources.getString(JSP_DESCRIPTION_LIST);
		return forward;
	}

}
