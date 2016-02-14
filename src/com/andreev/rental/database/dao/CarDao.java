package com.andreev.rental.database.dao;

import java.sql.CallableStatement;
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
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.Order;

public class CarDao extends BaseDao implements ICarDao {

	private static final Logger LOG = Logger.getLogger(CarDao.class);
	private static final String GET_BY_ID = "dao.car.get_by_id";
	private static final String SAVE = "dao.car.save";
	private static final String UPDATE = "dao.car.update";
	private static final String DELETE = "dao.car.delete";
	private static final String LIST = "dao.car.list";
	private static final String COUNT = "dao.car.count";
	private static final String LIST_BY_CAR_DESCRIPTION = "dao.car.list_by_description";
	private static final String ORDER_CAR_CALL = "dao.car.proc.order";

	public CarDao(IConnectionPool connectionPool, ExtractBehavior buildBehavior) {
		super(connectionPool, buildBehavior);
		LOG.info("Car dao has been initialized");
	}

	@Override
	public Car findById(long id) throws DatabaseException {
		Car car = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_ID);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				car = (Car) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding car by id failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return car;
	}

	@Override
	public int save(Car model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, SAVE);
			setCar(statement, model);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Saving car failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public int update(Car model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, UPDATE);
			setCar(statement, model);
			statement.setLong(4, model.getId());
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Updating car failed", e);
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
			throw new DatabaseException("Deleting car failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public List<Car> list() throws DatabaseException {
		List<Car> list = new ArrayList<Car>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((Car) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding car list failed", e);
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
			throw new DatabaseException("Countin car failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return count;
	}

	@Override
	public List<Car> listByCarDescription(CarDescription carDescription)
			throws DatabaseException {
		if(carDescription == null) {
			throw new DatabaseException("Model is null");
		}
		List<Car> list = new ArrayList<Car>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST_BY_CAR_DESCRIPTION);
			statement.setLong(1, carDescription.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((Car) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException(
					"Finding car list by car description failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return list;
	}

	@Override
	public long idByOrderDescription(Order order, long descriptionId)
			throws DatabaseException {
		if(order == null) {
			throw new DatabaseException("Model is null");
		}
		long id = 0l;
		Date from = new Date(order.getFrom().getTime());
		Date to = new Date(order.getTo().getTime());
		Connection connection = connectionPool.takeConnection();
		CallableStatement statement = null;
		try {
			statement = openCallStatement(connection, ORDER_CAR_CALL);
			statement.setLong(1, descriptionId);
			statement.setDate(2, from);
			statement.setDate(3, to);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException(
					"Finding car by order and car description failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return id;
	}

	private void setCar(PreparedStatement statement, Car car)
			throws SQLException {
		int i = 0;
		statement.setLong(++i, car.getCarDescription().getId());
		statement.setBoolean(++i, car.isAvailable());
		statement.setString(++i, car.getDescription());
	}
}