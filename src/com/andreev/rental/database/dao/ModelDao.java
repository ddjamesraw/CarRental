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
import com.andreev.rental.model.CarModel;

public class ModelDao extends BaseDao implements IModelDao {

	private static final Logger LOG = Logger.getLogger(ModelDao.class);
	private static final String GET_BY_ID = "dao.car_model.get_by_id";
	private static final String SAVE = "dao.car_model.save";
	private static final String UPDATE = "dao.car_model.update";
	private static final String DELETE = "dao.car_model.delete";
	private static final String LIST = "dao.car_model.list";
	private static final String COUNT = "dao.car_model.count";
	private static final String GET_BY_NAME = "dao.car_model.get_by_name";
	private static final String LIST_BY_BRAND = "dao.car_model.list_by_brand";

	public ModelDao(IConnectionPool connectionPool, ExtractBehavior buildBehavior) {
		super(connectionPool, buildBehavior);
		LOG.info("Car model dao has been initializes");
	}

	@Override
	public CarModel findById(long id) throws DatabaseException {
		CarModel carModel = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_ID);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				carModel = (CarModel) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding car model by id failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return carModel;
	}

	@Override
	public int save(CarModel model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, SAVE);
			setCarModel(statement, model);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Saving car model failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public int update(CarModel model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, UPDATE);
			setCarModel(statement, model);
			statement.setLong(3, model.getId());
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Updating car model failed", e);
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
			throw new DatabaseException("Daleting car model failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public List<CarModel> list() throws DatabaseException {
		List<CarModel> list = new ArrayList<CarModel>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((CarModel) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding car model list failed", e);
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
			throw new DatabaseException("Counting car model failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return count;
	}

	@Override
	public CarModel findByName(String name) throws DatabaseException {
		CarModel carModel = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_NAME);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				carModel = (CarModel) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding car model by id failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return carModel;
	}

	@Override
	public List<CarModel> listByBrand(Brand brand) throws DatabaseException {
		if(brand == null) {
			throw new DatabaseException("Brand is null");
		}
		List<CarModel> list = new ArrayList<CarModel>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST_BY_BRAND);
			statement.setLong(1, brand.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((CarModel) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException(
					"Finding car model list by brand failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return list;
	}

	private void setCarModel(PreparedStatement statement, CarModel carModel)
			throws SQLException {
		int i = 0;
		statement.setString(++i, carModel.getName());
		statement.setLong(++i, carModel.getBrand().getId());
	}
}
