package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.service.BrandService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Brand;

public class BrandListAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(BrandListAction.class);
	
	private static final String PARAM_BRAND_LIST = "brandList";
	private static final String JSP_BRAND_LIST = "jsp.admin.brand.list";

	public BrandListAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Brand> brandService = null;
		try {
			brandService = BrandService.getInstance();
		} catch (LogicException e) {
			LOG.error("Brand list action failed", e);
			return getForwardError();
		}
		try {
			List<Brand> list = brandService.list(message);
			request.setAttribute(PARAM_BRAND_LIST, list);
		} catch (LogicException e) {
			LOG.error("Brand list getting failed", e);
		}
		setMessages(request, message);
		String forward = resources.getString(JSP_BRAND_LIST);
		return forward;
	}

}
