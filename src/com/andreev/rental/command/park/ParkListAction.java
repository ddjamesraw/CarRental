package com.andreev.rental.command.park;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.BrandService;
import com.andreev.rental.logic.service.DescriptionService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarDescription;

public class ParkListAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(ParkListAction.class);

	private static final String PARAM_DESCRIPTION_LIST = "descriptionList";
	private static final String PARAM_BRAND_LIST = "criterionList";
	private static final String JSP_DESCRIPTION_LIST = "jsp.park.list";

	public ParkListAction() {
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
			return getForwardError();
		}
		try {
			List<CarDescription> list = descriptionService.list(message);
			request.setAttribute(PARAM_DESCRIPTION_LIST, list);
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
