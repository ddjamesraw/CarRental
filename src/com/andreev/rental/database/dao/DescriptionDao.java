package com.andreev.rental.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.database.behavior.ExtractBehavior;
import com.andreev.rental.database.pool.IConnectionPool;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.CarModel;

public class DescriptionDao extends BaseDao implements IDescriptionDao {

	private static final Logger LOG = Logger.getLogger(DescriptionDao.class);
	private static final String GET_BY_ID = "dao.car_description.get_by_id";
	private static final String SAVE = "dao.car_description.save";
	private static final String UPDATE = "dao.car_description.update";
	private static final String DELETE = "dao.car_description.delete";
	private static final String LIST = "dao.car_description.list";
	private static final String COUNT = "dao.car_description.count";
	private static final String LIST_BY_MODEL = "dao.car_description.list_by_model";
	private static final String LIST_BY_BRAND = "dao.car_description.list_by_brand";

	public DescriptionDao(IConnectionPool connectionPool,
			ExtractBehavior buildBehavior) {
		super(connectionPool, buildBehavior);
		LOG.info("Car description dao has been initialized");
	}

	@Override
	public CarDescription findById(long id) throws DatabaseException {
		CarDescription carDescription = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_ID);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				carDescription = (CarDescription) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding car description by id failed",
					e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return carDescription;
	}

	@Override
	public int save(CarDescription model) throws DatabaseException {
		if (model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, SAVE);
			setCarDescription(statement, model);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Saving car description failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public int update(CarDescription model) throws DatabaseException {
		if (model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, UPDATE);
			setCarDescription(statement, model);
			statement.setLong(11, model.getId());
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Updating car description failed", e);
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
			throw new DatabaseException("Deleting car description failde", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public List<CarDescription> list() throws DatabaseException {
		List<CarDescription> list = new ArrayList<CarDescription>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((CarDescription) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Getting car description list failed",
					e);
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
			throw new DatabaseException("Counting car description failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return count;
	}

	@Override
	public List<CarDescription> listByModel(CarModel model)
			throws DatabaseException {
		if (model == null) {
			throw new DatabaseException("Model is null");
		}
		List<CarDescription> list = new ArrayList<CarDescription>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST_BY_MODEL);
			statement.setLong(1, model.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((CarDescription) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException(
					"Finding car description list by model failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return list;
	}

	@Override
	public List<CarDescription> listByBrand(Brand brand)
			throws DatabaseException {
		if (brand == null) {
			throw new DatabaseException("Brand is null");
		}
		List<CarDescription> list = new ArrayList<CarDescription>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST_BY_BRAND);
			statement.setLong(1, brand.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((CarDescription) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException(
					"Finding car description list by brand failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return list;
	}

	private void setCarDescription(PreparedStatement statement,
			CarDescription carDescription) throws SQLException {
		int i = 0;
		statement.setLong(++i, carDescription.getModel().getId());
		statement.setInt(++i, carDescription.getPrice());
		statement.setInt(++i, carDescription.getDoors());
		statement.setInt(++i, carDescription.getSeats());
		statement.setInt(++i, carDescription.getConsumption());
		statement.setBoolean(++i, carDescription.isAirCondition());
		statement.setBoolean(++i, carDescription.isAirBags());
		statement.setBoolean(++i, carDescription.isAutomatic());
		statement.setString(++i, carDescription.getDescription());
		statement.setString(++i, carDescription.getImgUrl());
	}
}
