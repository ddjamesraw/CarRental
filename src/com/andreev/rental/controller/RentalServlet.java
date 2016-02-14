package com.andreev.rental.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.andreev.rental.command.AbstractAction;
import com.andreev.rental.command.ActionFactory;
import com.andreev.rental.command.IActionFactory;

public class RentalServlet extends HttpServlet {
	private static final long serialVersionUID = 8744366460979636483L;
	private static final Logger LOG = Logger.getLogger(RentalServlet.class);
	
	@Override
	public void init() throws ServletException {
		super.init();
		LOG.info("Rentla servlet has been initialized");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		IActionFactory factory = ActionFactory.getInstance();
       	AbstractAction action = factory.getAction(request);
       	String forward;
		try {
			forward = action.execute(request);
			request.getRequestDispatcher(forward).forward(request, response);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }

}
