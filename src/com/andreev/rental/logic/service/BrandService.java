package com.andreev.rental.logic.service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.andreev.rental.database.DaoFactory;
import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.IDaoFactory;
import com.andreev.rental.database.dao.IBrandDao;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.Entity;

public class BrandService implements IService<Brand> {

	private static final Lock LOCK = new ReentrantLock();
	
	private static final String MSG_GET = "msg.admin.brand.get";
	private static final String MSG_LIST = "msg.admin.brand.list";
	private static final String MSG_CREATE = "msg.admin.brand.create";
	private static final String MSG_EDIT = "msg.admin.brand.edit";
	private static final String MSG_DELET = "msg.admin.brand.delete";
	private static final String MSG_COUNT = "msg.admin.brand.count";
	
	private static volatile BrandService instance;

	private IBrandDao brandDao;

	private BrandService() throws LogicException {
		IDaoFactory daoFactory = null;
		try {
			daoFactory = DaoFactory.getInstance();
		} catch (DatabaseException e) {
			throw new LogicException("Dao initialization failed");
		}
		this.brandDao = daoFactory.getBrandDao();

	}

	public static IService<Brand> getInstance() throws LogicException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new BrandService();
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public Brand get(long id, MessageManager message) throws LogicException {
		Brand brand = null;
		try {
			brand = brandDao.findById(id);
			if(brand == null) {
				message.addMessage(MSG_GET);
				throw new LogicException("Getting brand failed");
			}
		} catch (DatabaseException e) {
			message.addMessage(MSG_GET);
			throw new LogicException("Finding brand failed", e);
		}
		return brand;
	}

	@Override
	public List<Brand> list(MessageManager message) throws LogicException {
		List<Brand> list = null;
		try {
			list = brandDao.list();
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException("Finding brand's list failed", e);
		}
		return list;
	}

	@Override
	public void save(Brand entity, MessageManager message)
			throws LogicException {
		try {
			brandDao.save(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_CREATE);
			throw new LogicException("Saving brand failed", e);
		}
	}

	@Override
	public void edit(Brand entity, MessageManager message)
			throws LogicException {
		try {
			brandDao.update(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_EDIT);
			throw new LogicException("Editing brand failed", e);
		}
	}

	@Override
	public void delete(long id, MessageManager message)
			throws LogicException {
		try {
			brandDao.delete(id);
		} catch (DatabaseException e) {
			message.addMessage(MSG_DELET);
			throw new LogicException("Deleting brand failed", e);
		}

	}

	@Override
	public List<Brand> list(Entity entity, MessageManager message)
			throws LogicException {
		message.addMessage(MSG_LIST);
		throw new LogicException("Unsupported operation");
	}

	@Override
	public int count(MessageManager message) throws LogicException {
		try {
			return brandDao.count();
		} catch (DatabaseException e) {
			message.addMessage(MSG_COUNT);
			throw new LogicException("Count brand failed", e);
		}
	}

}
