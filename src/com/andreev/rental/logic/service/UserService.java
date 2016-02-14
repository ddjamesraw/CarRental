package com.andreev.rental.logic.service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.andreev.rental.database.DaoFactory;
import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.IDaoFactory;
import com.andreev.rental.database.dao.IUserDao;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Entity;
import com.andreev.rental.model.User;

public class UserService implements IService<User> {

	private static final Lock LOCK = new ReentrantLock();

	private static final String MSG_GET = "msg.admin.user.get";
	private static final String MSG_LIST = "msg.admin.user.list";
	private static final String MSG_CREATE = "msg.admin.user.create";
	private static final String MSG_EDIT = "msg.admin.user.edit";
	private static final String MSG_DELET = "msg.admin.user.delete";
	private static final String MSG_COUNT = "msg.admin.user.count";

	private static volatile UserService instance;
	
	private IUserDao userDao;

	private UserService() throws LogicException {
		IDaoFactory daoFactory = null;
		try {
			daoFactory = DaoFactory.getInstance();
		} catch (DatabaseException e) {
			throw new LogicException("Dao initialization failed");
		}
		this.userDao = daoFactory.getUserDao();

	}

	public static IService<User> getInstance() throws LogicException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new UserService();
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public User get(long id, MessageManager message) throws LogicException {
		User user = null;
		try {
			user = userDao.findById(id);
			if(user == null) {
				message.addMessage(MSG_GET);
				throw new LogicException("Getting user failed");
			}
		} catch (DatabaseException e) {
			message.addMessage(MSG_GET);
			throw new LogicException("Finding user failed", e);
		}
		return user;
	}

	@Override
	public List<User> list(MessageManager message) throws LogicException {
		List<User> list = null;
		try {
			list = userDao.list();
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException("Finding user's list failed", e);
		}
		return list;
	}

	@Override
	public void save(User entity, MessageManager message)
			throws LogicException {
		try {
			userDao.save(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_CREATE);
			throw new LogicException("Saving user failed", e);
		}
	}

	@Override
	public void edit(User entity, MessageManager message) throws LogicException {
		try {
			userDao.update(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_EDIT);
			throw new LogicException("Editing user failed", e);
		}
	}

	@Override
	public void delete(long id, MessageManager message)
			throws LogicException {
		try {
			userDao.delete(id);
		} catch (DatabaseException e) {
			message.addMessage(MSG_DELET);
			throw new LogicException("Deleting user failed", e);
		}
	}

	@Override
	public List<User> list(Entity entity, MessageManager message)
			throws LogicException {
		message.addMessage(MSG_LIST);
		throw new LogicException("Unsupported operation");
	}

	@Override
	public int count(MessageManager message) throws LogicException {
		try {
			return userDao.count();
		} catch (DatabaseException e) {
			message.addMessage(MSG_COUNT);
			throw new LogicException("Count user failed", e);
		}
	}

}
