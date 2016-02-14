package com.andreev.rental.command;

import javax.servlet.http.HttpServletRequest;

public interface IActionFactory {

	public AbstractAction getAction(HttpServletRequest request);

}
