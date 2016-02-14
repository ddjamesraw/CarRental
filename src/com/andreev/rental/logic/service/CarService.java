package com.andreev.rental.logic.service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.andreev.rental.database.DaoFactory;
import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.IDaoFactory;
import com.andreev.rental.database.dao.ICarDao;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.Entity;
import com.andreev.rental.model.Order;

public class CarService implements IService<Car> {

	private static final Lock LOCK = new ReentrantLock();

	private static final String MSG_GET = "msg.admin.car.get";
	private static final String MSG_LIST = "msg.admin.car.list";
	private static final String MSG_CREATE = "msg.admin.car.create";
	private static final String MSG_EDIT = "msg.admin.car.edit";
	private static final String MSG_DELET = "msg.admin.car.delete";
	private static final String MSG_COUNT = "msg.admin.car.count";
	private static final String MSG_FREE = "msg.admin.car.free";

	private static volatile CarService instance;

	private ICarDao carDao;

	private CarService() throws LogicException {
		IDaoFactory daoFactory = null;
		try {
			daoFactory = DaoFactory.getInstance();
		} catch (DatabaseException e) {
			throw new LogicException("Dao initialization failed");
		}
		this.carDao = daoFactory.getCarDao();

	}

	public static IService<Car> getInstance() throws LogicException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new CarService();
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public Car get(long id, MessageManager message) throws LogicException {
		Car car = null;
		try {
			car = carDao.findById(id);
			if(car == null) {
				message.addMessage(MSG_GET);
				throw new LogicException("Getting car failed");
			}
		} catch (DatabaseException e) {
			message.addMessage(MSG_GET);
			throw new LogicException("Finding car failed", e);
		}
		return car;
	}

	@Override
	public List<Car> list(MessageManager message) throws LogicException {
		List<Car> list = null;
		try {
			list = carDao.list();
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException("Finding car's list failed", e);
		}
		return list;
	}

	@Override
	public void save(Car entity, MessageManager message)
			throws LogicException {
		try {
			carDao.save(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_CREATE);
			throw new LogicException("Saving car failed", e);
		}
	}

	@Override
	public void edit(Car entity, MessageManager message) throws LogicException {
		try {
			carDao.update(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_EDIT);
			throw new LogicException("Editing car failed", e);
		}
	}

	@Override
	public void delete(long id, MessageManager message)
			throws LogicException {
		try {
			carDao.delete(id);
		} catch (DatabaseException e) {
			message.addMessage(MSG_DELET);
			throw new LogicException("Deleting car failed", e);
		}
	}

	@Override
	public List<Car> list(Entity entity, MessageManager message)
			throws LogicException {
		if (entity instanceof CarDescription) {
			return listByCarDescriptio((CarDescription) entity, message);
		} else {
			message.addMessage(MSG_LIST);
			throw new LogicException("Unsupported operation");
		}
	}

	@Override
	public int count(MessageManager message) throws LogicException {
		try {
			return carDao.count();
		} catch (DatabaseException e) {
			message.addMessage(MSG_COUNT);
			throw new LogicException("Count car failed", e);
		}
	}
	
	public Car findByOrderAndDescription(Order order, long descriptionId, MessageManager message) throws LogicException {
		Car car = null;
		try {
			long id = carDao.idByOrderDescription(order, descriptionId);
			if(id != 0l) {
				car = get(id, message);
			} else {
				message.addMessage(MSG_FREE);
			}
			return car; 
		} catch (DatabaseException e) {
			message.addMessage(MSG_GET);
			throw new LogicException("Finding car by order and description id failed", e);
		}
	}

	private List<Car> listByCarDescriptio(CarDescription carDescription,
			MessageManager message) throws LogicException {
		try {
			List<Car> list = carDao.listByCarDescription(carDescription);
			return list;
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException(
					"Finding car's list by car description failed", e);
		}
	}

}
