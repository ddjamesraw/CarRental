package com.andreev.rental.command;

import javax.servlet.http.HttpServletRequest;

import com.andreev.rental.logic.util.MessageManager;

public class ErrorAction extends AbstractAction {
	
	private static final String MSG_PAGE_NOT_FOUND = "msg.error.404";

	public ErrorAction() {
	}

	@Override
	public String execute(HttpServletRequest request) {
		MessageManager message = new MessageManager();
		message.addMessage(MSG_PAGE_NOT_FOUND);
		setMessages(request, message);
		return getForwardError();
	}

}
