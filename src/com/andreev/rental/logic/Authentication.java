package com.andreev.rental.logic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.andreev.rental.database.DaoFactory;
import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.IDaoFactory;
import com.andreev.rental.database.dao.IUserDao;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.User;

public class Authentication implements IAuthentication {

	private static final Logger LOG = Logger.getLogger(Authentication.class);
	private static final Lock LOCK = new ReentrantLock();
	private static final String MSG_ICORRECT = "msg.auth.incorrect";
	private static final String MSG_LOGIN = "msg.auth.login";
	private static final String MSG_MATCH = "msg.auth.match";
	private static final String MSG_EMAIL = "msg.auth.email";
	private static final String MSG_REGISTER = "msg.auth.register";
	private static final String MSG_EDIT = "msg.auth.edit";
	private static final String MSG_PASSWORD = "msg.auth.password";

	private static volatile Authentication instance;

	private IUserDao userDao;

	private Authentication() throws LogicException {
		IDaoFactory daoFactory = null;
		try {
			daoFactory = DaoFactory.getInstance();
		} catch (DatabaseException e) {
			throw new LogicException("Authentification init failed", e);
		}
		this.userDao = daoFactory.getUserDao();
	}

	public static IAuthentication getInstance() throws LogicException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new Authentication();
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public boolean login(MessageManager message, User user) {
		if(user == null) {
			LOG.warn("User is null");
			return false;
		}
		int password = user.getPassword();
		try {
			user = userDao.findByEmail(user.getEmail());
			if (user == null || user.getPassword() != password) {
				message.addMessage(MSG_ICORRECT);
				return false;
			}
		} catch (DatabaseException e) {
			LOG.error("Login failed", e);
			message.addMessage(MSG_LOGIN);
			return false;
		}
		return true;
	}

	@Override
	public boolean register(MessageManager message, User user) {
		if(user == null) {
			LOG.warn("User is null");
			return false;
		}
		if (isRegistered(user.getEmail())) {
			message.addMessage(MSG_EMAIL);
			return false;
		}
		try {
			userDao.save(user);
		} catch (DatabaseException e) {
			LOG.error("Register failed", e);
			message.addMessage(MSG_REGISTER);
			return false;
		}
		return true;
	}

	@Override
	public boolean edit(MessageManager message, User user) {
		if(user == null) {
			LOG.warn("User is null");
			return false;
		}
		try {
			userDao.update(user);
		} catch (DatabaseException e) {
			LOG.error("Profile editing failed", e);
			message.addMessage(MSG_EDIT);
			return false;
		}
		return true;
	}
	
	@Override
	public User getUser(MessageManager message, String email)
			throws LogicException {
		try {
			User user = userDao.findByEmail(email);
			if(user == null) {
				message.addMessage(MSG_LOGIN);
				throw new LogicException("Getting user failed");
			}
			return user;
		} catch (DatabaseException e) {
			message.addMessage(MSG_LOGIN);
			throw new LogicException("Getting user failed", e);
		}
	}

	@Override
	public boolean isRegistered(String email) {
		try {
			User user = userDao.findByEmail(email);
			if (user != null) {
				return true;
			}
		} catch (DatabaseException e) {
			LOG.error("Checking register failed", e);
		}
		return false;
	}

	@Override
	public boolean checkPasswords(MessageManager message, String password,
			String confirmPassword) {
		if (password.equals(confirmPassword)) {
			return true;
		}
		message.addMessage(MSG_MATCH);
		return false;
	}

	@Override
	public boolean checkPasswords(MessageManager message, User user,
			int password) {
		if (user.getPassword() == password) {
			return true;
		}
		message.addMessage(MSG_PASSWORD);
		return false;
	}

}
