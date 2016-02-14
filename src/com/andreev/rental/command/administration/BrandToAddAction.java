package com.andreev.rental.command.administration;

import javax.servlet.http.HttpServletRequest;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;

public class BrandToAddAction extends AbstractAction {
	
	private static final String JSP_BRAND_ADD = "jsp.admin.brand.add";

	public BrandToAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		String forward = resources.getString(JSP_BRAND_ADD);
		return forward;
	}

}
