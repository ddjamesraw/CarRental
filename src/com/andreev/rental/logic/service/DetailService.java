package com.andreev.rental.logic.service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.andreev.rental.database.DaoFactory;
import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.IDaoFactory;
import com.andreev.rental.database.dao.IDetailDao;
import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Entity;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.OrderDetail;

public class DetailService implements IService<OrderDetail> {

	private static final Lock LOCK = new ReentrantLock();

	private static final String MSG_GET = "msg.admin.detail.get";
	private static final String MSG_LIST = "msg.admin.detail.list";
	private static final String MSG_CREATE = "msg.admin.detail.create";
	private static final String MSG_EDIT = "msg.admin.detail.edit";
	private static final String MSG_DELET = "msg.admin.detail.delete";
	private static final String MSG_COUNT = "msg.admin.detail.count";

	private static volatile DetailService instance;

	private IDetailDao detailDao;

	private DetailService() throws LogicException {
		IDaoFactory daoFactory = null;
		try {
			daoFactory = DaoFactory.getInstance();
		} catch (DatabaseException e) {
			throw new LogicException("Dao initialization failed");
		}
		this.detailDao = daoFactory.getOrderDetailDao();
	}

	public static IService<OrderDetail> getInstance() throws LogicException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new DetailService();
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public OrderDetail get(long id, MessageManager message)
			throws LogicException {
		OrderDetail detail = null;
		try {
			detail = detailDao.findById(id);
			if (detail == null) {
				message.addMessage(MSG_GET);
				throw new LogicException("Getting order detail failed");
			}
		} catch (DatabaseException e) {
			message.addMessage(MSG_GET);
			throw new LogicException("Finding order detail failed", e);
		}
		return detail;
	}

	@Override
	public List<OrderDetail> list(MessageManager message) throws LogicException {
		List<OrderDetail> list = null;
		try {
			list = detailDao.list();
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException("Finding order detail's list failed", e);
		}
		return list;
	}

	@Override
	public void save(OrderDetail entity, MessageManager message)
			throws LogicException {
		try {
			detailDao.save(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_CREATE);
			throw new LogicException("Saving order detail failed", e);
		}
	}

	@Override
	public void edit(OrderDetail entity, MessageManager message)
			throws LogicException {
		try {
			detailDao.update(entity);
		} catch (DatabaseException e) {
			message.addMessage(MSG_EDIT);
			throw new LogicException("Editing order detail failed", e);
		}
	}

	@Override
	public void delete(long id, MessageManager message) throws LogicException {
		try {
			detailDao.delete(id);
		} catch (DatabaseException e) {
			message.addMessage(MSG_DELET);
			throw new LogicException("Deleting order detail failed", e);
		}
	}

	@Override
	public List<OrderDetail> list(Entity entity, MessageManager message)
			throws LogicException {
		if (entity instanceof Order) {
			return listByOrder((Order) entity, message);
		} else {
			message.addMessage(MSG_LIST);
			throw new LogicException("Unsupported operation");
		}
	}

	@Override
	public int count(MessageManager message) throws LogicException {
		try {
			return detailDao.count();
		} catch (DatabaseException e) {
			message.addMessage(MSG_COUNT);
			throw new LogicException("Count order detail failed", e);
		}
	}

	private List<OrderDetail> listByOrder(Order order, MessageManager message)
			throws LogicException {
		try {
			List<OrderDetail> list = detailDao.listByOrder(order);
			return list;
		} catch (DatabaseException e) {
			message.addMessage(MSG_LIST);
			throw new LogicException(
					"Finding order detail's list by order failed", e);
		}
	}

}
