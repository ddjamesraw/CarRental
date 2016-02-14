package com.andreev.rental.command.administration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.builder.BrandBuilder;
import com.andreev.rental.logic.service.BrandService;
import com.andreev.rental.logic.service.IService;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Brand;

public class BrandAddAction extends AbstractAction {

	private static final Logger LOG = Logger.getLogger(BrandAddAction.class);
	
	private static final String PARAM_BRAND_LIST = "brandList";
	private static final String PARAM_BRAND_NAME = "name";
	private static final String JSP_BRAND_ADD = "jsp.admin.brand.add";
	private static final String JSP_BRAND_LIST = "jsp.admin.brand.list";

	public BrandAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		IService<Brand> brandService = null;
		try {
			brandService = BrandService.getInstance();			
		} catch (LogicException e) {
			LOG.error("Brand add action failed", e);
			return getForwardError();
		}
		String forward = null;
		String name = request.getParameter(PARAM_BRAND_NAME);
		BrandBuilder builder = new BrandBuilder(message);
		if(!isValidToken(request)) {
			forward = toBrandList(request, brandService, message);
		} else if(builder.name(name).build()) {
			Brand brand = (Brand) builder.getEntity();
			try {
				 brandService.save(brand, message);
			} catch (LogicException e) {
				LOG.error("Brand adding failed", e);
				forward = toBrandAdd();
			}
			forward = toBrandList(request, brandService, message);
		} else {
			forward = toBrandAdd();
		}
		setMessages(request, message);
		return forward;
	}
	
	private String toBrandAdd() {
		return resources.getString(JSP_BRAND_ADD);
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
