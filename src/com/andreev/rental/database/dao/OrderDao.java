package com.andreev.rental.database.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.behavior.ExtractBehavior;
import com.andreev.rental.database.pool.IConnectionPool;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.User;

public class OrderDao extends BaseDao implements IOrderDao {

	private static final Logger LOG = Logger.getLogger(OrderDao.class);
	private static final String GET_BY_ID = "dao.order.get_by_id";
	private static final String SAVE = "dao.order.save";
	private static final String UPDATE = "dao.order.update";
	private static final String DELETE = "dao.order.delete";
	private static final String LIST = "dao.order.list";
	private static final String COUNT = "dao.order.count";
	private static final String LIST_BY_USER = "dao.order.list_by_user";
	private static final String LIST_BY_CAR = "dao.order.list_by_car";

	public OrderDao(IConnectionPool connectionPool, ExtractBehavior buildBehavior) {
		super(connectionPool, buildBehavior);
		LOG.info("Order dao nas been initialized");
	}

	@Override
	public Order findById(long id) throws DatabaseException {
		Order order = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_ID);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				order = (Order) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding order by id failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return order;
	}

	@Override
	public int save(Order model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, SAVE);
			setOrder(statement, model);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Saving order failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public int update(Order model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, UPDATE);
			setOrder(statement, model);
			statement.setLong(8, model.getId());
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Updating order failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public int delete(long id) throws DatabaseException {
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, DELETE);
			statement.setLong(1, id);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Deleting order failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public List<Order> list() throws DatabaseException {
		List<Order> list = new ArrayList<Order>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((Order) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding order list failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return list;
	}

	@Override
	public int count() throws DatabaseException {
		int count = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, COUNT);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt(1);
		} catch (SQLException e) {
			throw new DatabaseException("Counting order failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return count;
	}

	@Override
	public List<Order> listByUser(User user) throws DatabaseException {
		if(user == null) {
			throw new DatabaseException("User is null");
		}
		List<Order> list = new ArrayList<Order>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST_BY_USER);
			statement.setLong(1, user.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((Order) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding order list by user failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return list;
	}

	@Override
	public List<Order> listByCar(Car car) throws DatabaseException {
		if(car == null) {
			throw new DatabaseException("Car is null");
		}
		List<Order> list = new ArrayList<Order>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST_BY_CAR);
			statement.setLong(1, car.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((Order) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding order list by car failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return list;
	}

	private void setOrder(PreparedStatement statement, Order order)
			throws SQLException {
		int i = 0;
		statement.setLong(++i, order.getCar().getId());
		statement.setLong(++i, order.getUser().getId());
		statement.setInt(++i, order.getStatus().getStatusId());
		statement.setDate(++i, new Date(order.getFrom().getTime()));
		statement.setDate(++i, new Date(order.getTo().getTime()));
		statement.setInt(++i, order.getTotal());
		statement.setString(++i, order.getInfo());
	}
}
