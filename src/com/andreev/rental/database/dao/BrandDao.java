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

public class BrandDao extends BaseDao implements IBrandDao {

	private static final Logger LOG = Logger.getLogger(BrandDao.class);
	private static final String GET_BY_ID = "dao.brand.get_by_id";
	private static final String SAVE = "dao.brand.save";
	private static final String UPDATE = "dao.brand.update";
	private static final String DELETE = "dao.brand.delete";
	private static final String LIST = "dao.brand.list";
	private static final String COUNT = "dao.brand.count";
	private static final String GET_BY_NAME = "dao.brand.get_by_name";

	public BrandDao(IConnectionPool connectionPool, ExtractBehavior buildBehavior) {
		super(connectionPool, buildBehavior);
		LOG.info("Brand dao has been initialized");
	}

	@Override
	public Brand findById(long id) throws DatabaseException {
		Brand brand = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_ID);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				brand = (Brand) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding brand failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return brand;
	}

	@Override
	public int save(Brand model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, SAVE);
			setBrand(statement, model);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Saving brand failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public int update(Brand model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, UPDATE);
			setBrand(statement, model);
			statement.setLong(2, model.getId());
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Updating brand failed", e);
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
			throw new DatabaseException("Deleting brand failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public List<Brand> list() throws DatabaseException {
		List<Brand> list = new ArrayList<Brand>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((Brand) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding brand list failed", e);
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
			throw new DatabaseException("Counting brand failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return count;
	}

	@Override
	public Brand findByName(String name) throws DatabaseException {
		Brand brand = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_NAME);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				brand = (Brand) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding brand by name failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return brand;
	}

	private void setBrand(PreparedStatement statement, Brand brand)
			throws SQLException {
		int i = 0;
		statement.setString(++i, brand.getName());
	}
}
