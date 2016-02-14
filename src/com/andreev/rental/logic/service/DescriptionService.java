package com.andreev.rental.logic.service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.andreev.rental.database.DaoFactory;
import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.IDaoFactory;
import com.andreev.rental.database.dao.IDescriptionDao;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.CarModel;
import com.andreev.rental.model.Entity;

public class DescriptionService implements IService<CarDescription> {

	private static final Lock LOCK = new ReentrantLock();

	private static final String MSG_GET = "msg.admin.description.get";
	private static final String MSG_LIST = "msg.admin.description.list";
	private static final String MSG_CREATE = "msg.admin.description.create";
	private static final String MSG_EDIT = "msg.admin.description.edit";
	private static final String MSG_DELET = "msg.admin.description.delete";
	private static final String MSG_COUNT = "msg.admin.description.count";

	private static volatile DescriptionService instance;

	private IDescriptionDao descriptionDao;

	private DescriptionService() throws LogicException {
		IDaoFactory daoFactory = null;
		try {
			daoFactory = DaoFactory.getInstance();
		} catch (DatabaseException e) {
			throw new LogicException("Dao initialization failed");
		}
		this.descriptionDao = daoFactory.getCarDescriptionDao();

	}

	public static IService<CarDescription> getInstance() throws LogicException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new DescriptionService();
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public CarDescription get(long id, MessageManager message)
			throws LogicException {
		CarDescription description = null;
		try {
			description = descriptionDao.findById(id);
			if(description == null) {
				message.addMessage(MSG_GET);
				throw new LogicException("Getting description failed");
			}
		} catch (DatabaseException e) {
			message.addMessage(MSG_GET);
			throw new LogicException("Finding description failed", e);
		}
		return description;
	}

	@Override
	public List<CarDescription> list(MessageManager message)
			throws LogicException {
		List<CarDescription> list = null;
		try {
			list = descriptionDao.list();
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException("Finding description's list failed", e);
		}
		return list;
	}

	@Override
	public void save(CarDescription entity, MessageManager message)
			throws LogicException {
		try {
			descriptionDao.save(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_CREATE);
			throw new LogicException("Saving description failed", e);
		}
	}

	@Override
	public void edit(CarDescription entity, MessageManager message)
			throws LogicException {
		try {
			descriptionDao.update(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_EDIT);
			throw new LogicException("Editing description failed", e);
		}
	}

	@Override
	public void delete(long id, MessageManager message)
			throws LogicException {
		try {
			descriptionDao.delete(id);
		} catch (DatabaseException e) {
			message.addMessage(MSG_DELET);
			throw new LogicException("Deleting description failed", e);
		}
	}

	@Override
	public List<CarDescription> list(Entity entity, MessageManager message)
			throws LogicException {
		if (entity instanceof CarModel) {
			return listByCarModel((CarModel) entity, message);
		} else if (entity instanceof Brand) {
			return listByBrand((Brand) entity, message);
		} else {
			message.addMessage(MSG_LIST);
			throw new LogicException("Unsupported operation");
		}
	}

	@Override
	public int count(MessageManager message) throws LogicException {
		try {
			return descriptionDao.count();
		} catch (DatabaseException e) {
			message.addMessage(MSG_COUNT);
			throw new LogicException("Count car description failed", e);
		}
	}

	private List<CarDescription> listByCarModel(CarModel carModel,
			MessageManager message) throws LogicException {
		try {
			List<CarDescription> list = descriptionDao.listByModel(carModel);
			return list;
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException(
					"Finding car description's list by car model failed", e);
		}
	}

	private List<CarDescription> listByBrand(Brand brand, MessageManager message)
			throws LogicException {
		try {
			List<CarDescription> list = descriptionDao.listByBrand(brand);
			return list;
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException(
					"Finding car description's list by brand failed", e);
		}
	}

}
