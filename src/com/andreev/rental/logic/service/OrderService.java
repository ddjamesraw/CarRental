package com.andreev.rental.logic.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.andreev.rental.database.DaoFactory;
import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.IDaoFactory;
import com.andreev.rental.database.dao.ICarDao;
import com.andreev.rental.database.dao.IOrderDao;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.Entity;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.User;

public class OrderService implements IService<Order> {

	private static final Lock LOCK = new ReentrantLock();

	private static final String MSG_GET = "msg.admin.order.get";
	private static final String MSG_LIST = "msg.admin.order.list";
	private static final String MSG_CREATE = "msg.admin.order.create";
	private static final String MSG_EDIT = "msg.admin.order.edit";
	private static final String MSG_DELET = "msg.admin.order.delete";
	private static final String MSG_COUNT = "msg.admin.order.count";
	private static final String MSG_DATE = "msg.admin.order.date";

	private static volatile OrderService instance;

	private IOrderDao orderDao;
	private ICarDao carDao;

	private OrderService() throws LogicException {
		IDaoFactory daoFactory = null;
		try {
			daoFactory = DaoFactory.getInstance();
		} catch (DatabaseException e) {
			throw new LogicException("Dao initialization failed");
		}
		this.orderDao = daoFactory.getOrderDao();
		this.carDao = daoFactory.getCarDao();

	}

	public static IService<Order> getInstance() throws LogicException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new OrderService();
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public Order get(long id, MessageManager message) throws LogicException {
		Order order = null;
		try {
			order = orderDao.findById(id);
			if(order == null) {
				message.addMessage(MSG_GET);
				throw new LogicException("Getting order failed");
			}
		} catch (DatabaseException e) {
			message.addMessage(MSG_GET);
			throw new LogicException("Finding order failed", e);
		}
		return order;
	}

	@Override
	public List<Order> list(MessageManager message) throws LogicException {
		List<Order> list = null;
		try {
			list = orderDao.list();
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException("Finding order's list failed", e);
		}
		return list;
	}

	@Override
	public void save(Order entity, MessageManager message)
			throws LogicException {
		if(entity == null) {
			message.addMessage(MSG_CREATE);
			throw new LogicException("Saving order failed");
		}
		try {
			int total = calculateTotal(entity, message);
			entity.setTotal(total);
			orderDao.save(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_CREATE);
			throw new LogicException("Saving order failed", e);
		}
	}

	@Override
	public void edit(Order entity, MessageManager message)
			throws LogicException {
		if (entity == null) {
			message.addMessage(MSG_EDIT);
			throw new LogicException("Editing order failed");
		}
		try {
			editTotal(entity, message);
			orderDao.update(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_EDIT);
			throw new LogicException("Editing order failed", e);
		}
	}

	@Override
	public void delete(long id, MessageManager message)
			throws LogicException {
		try {
			orderDao.delete(id);
		} catch (DatabaseException e) {
			message.addMessage(MSG_DELET);
			throw new LogicException("Deleting order failed", e);
		}
	}

	@Override
	public List<Order> list(Entity entity, MessageManager message)
			throws LogicException {
		if (entity instanceof User) {
			return listByUser((User) entity, message);
		} else if (entity instanceof Car) {
			return listByCar((Car) entity, message);
		} else {
			message.addMessage(MSG_LIST);
			throw new LogicException("Unsupported operation");
		}
	}

	@Override
	public int count(MessageManager message) throws LogicException {
		try {
			return orderDao.count();
		} catch (DatabaseException e) {
			message.addMessage(MSG_COUNT);
			throw new LogicException("Count order failed", e);
		}
	}

	private List<Order> listByUser(User user, MessageManager message)
			throws LogicException {
		try {
			List<Order> list = orderDao.listByUser(user);
			return list;
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException("Finding order's list by user failed", e);
		}
	}

	private List<Order> listByCar(Car car, MessageManager message)
			throws LogicException {
		try {
			List<Order> list = orderDao.listByCar(car);
			return list;
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException("Finding order's list by car failed", e);
		}
	}

	private void editTotal(Order order, MessageManager message)
			throws LogicException, DatabaseException {
		Order oldOrder = get(order.getId(), message);
		int oldTotal = oldOrder.getTotal();
		int newTotal = calculateTotal(order, message);
		order.setTotal(oldTotal - newTotal);
	}

	private int calculateTotal(Order order, MessageManager message)
			throws LogicException, DatabaseException {
		Date from = order.getFrom();
		Date to = order.getTo();
		Car car = carDao.findById(order.getCar().getId());
		int price = car.getCarDescription().getPrice();
		int diff = getDateDiff(from, to, message);
		if (diff == 0) {
			return price;
		}
		return diff * price;
	}

	private int getDateDiff(Date from, Date to, MessageManager message)
			throws LogicException {
		long diffInMillies = to.getTime() - from.getTime();
		if (diffInMillies < 0) {
			message.addMessage(MSG_DATE);
			throw new LogicException("");
		}
		return (int) TimeUnit.DAYS
				.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
}
