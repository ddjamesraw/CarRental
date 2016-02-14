package com.andreev.rental.command.administration;

import javax.servlet.http.HttpServletRequest;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.EUserStatus;

public class UserToAddAction extends AbstractAction {

	private static final String JSP_USER_ADD = "jsp.admin.user.add";

	public UserToAddAction() {
		setStatus(EUserStatus.ADMIN);
	}

	@Override
	public String execute(HttpServletRequest request) {
		String forward = resources.getString(JSP_USER_ADD);
		return forward;
	}

}
