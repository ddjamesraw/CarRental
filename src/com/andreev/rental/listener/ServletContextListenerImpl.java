package com.andreev.rental.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.andreev.rental.database.DaoFactory;
import com.andreev.rental.database.DatabaseException;

public class ServletContextListenerImpl implements ServletContextListener {

    public ServletContextListenerImpl() {
    }

    public void contextInitialized(ServletContextEvent event) {
    }

    public void contextDestroyed(ServletContextEvent event) {
    	try {
			DaoFactory.getInstance().dispose();
		} catch (DatabaseException e) {
			Logger log = Logger.getLogger(ServletContextListenerImpl.class);
			log.error("Service factory init failed", e);
		}
    }
	
}
