package com.andreev.rental.logic.service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.andreev.rental.database.DaoFactory;
import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.IDaoFactory;
import com.andreev.rental.database.dao.IModelDao;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarModel;
import com.andreev.rental.model.Entity;

public class ModelService implements IService<CarModel> {

	private static final Lock LOCK = new ReentrantLock();

	private static final String MSG_GET = "msg.admin.model.get";
	private static final String MSG_LIST = "msg.admin.model.list";
	private static final String MSG_CREATE = "msg.admin.model.create";
	private static final String MSG_EDIT = "msg.admin.model.edit";
	private static final String MSG_DELET = "msg.admin.model.delete";
	private static final String MSG_COUNT = "msg.admin.model.count";

	private static volatile ModelService instance;

	private IModelDao modelDao;

	private ModelService() throws LogicException {
		IDaoFactory daoFactory = null;
		try {
			daoFactory = DaoFactory.getInstance();
		} catch (DatabaseException e) {
			throw new LogicException("Dao initialization failed");
		}
		this.modelDao = daoFactory.getCarModelDao();

	}

	public static IService<CarModel> getInstance() throws LogicException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new ModelService();
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public CarModel get(long id, MessageManager message) throws LogicException {
		CarModel model = null;
		try {
			model = modelDao.findById(id);
			if(model == null) {
				message.addMessage(MSG_GET);
				throw new LogicException("Getting model failed");
			}
		} catch (DatabaseException e) {
			message.addMessage(MSG_GET);
			throw new LogicException("Finding model failed", e);
		}
		return model;
	}

	@Override
	public List<CarModel> list(MessageManager message) throws LogicException {
		List<CarModel> list = null;
		try {
			list = modelDao.list();
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException("Finding model's list failed", e);
		}
		return list;
	}

	@Override
	public void save(CarModel entity, MessageManager message)
			throws LogicException {
		try {
			modelDao.save(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_CREATE);
			throw new LogicException("Saving model failed", e);
		}
	}

	@Override
	public void edit(CarModel entity, MessageManager message)
			throws LogicException {
		try {
			modelDao.update(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_EDIT);
			throw new LogicException("Editing model failed", e);
		}
	}

	@Override
	public void delete(long id, MessageManager message)
			throws LogicException {
		try {
			modelDao.delete(id);
		} catch (DatabaseException e) {
			message.addMessage(MSG_DELET);
			throw new LogicException("Deleting model failed", e);
		}

	}

	@Override
	public List<CarModel> list(Entity entity, MessageManager message)
			throws LogicException {
		if (entity instanceof Brand) {
			return listByBrand((Brand) entity, message);
		} else {
			message.addMessage(MSG_LIST);
			throw new LogicException("Unsupported operation");
		}
	}

	@Override
	public int count(MessageManager message) throws LogicException {
		try {
			return modelDao.count();
		} catch (DatabaseException e) {
			message.addMessage(MSG_COUNT);
			throw new LogicException("Count car model failed", e);
		}
	}

	private List<CarModel> listByBrand(Brand brand, MessageManager message)
			throws LogicException {
		try {
			List<CarModel> list = modelDao.listByBrand(brand);
			return list;
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException(
					"Finding car model's list by brand failed", e);
		}
	}

}
