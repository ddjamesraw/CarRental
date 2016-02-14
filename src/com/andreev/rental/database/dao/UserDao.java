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
import com.andreev.rental.model.User;

public class UserDao extends BaseDao implements IUserDao {

	private static final Logger LOG = Logger.getLogger(UserDao.class);
	private static final String GET_BY_ID = "dao.user.get_by_id";
	private static final String SAVE = "dao.user.save";
	private static final String UPDATE = "dao.user.update";
	private static final String DELETE = "dao.user.delete";
	private static final String LIST = "dao.user.list";
	private static final String COUNT = "dao.user.count";
	private static final String GET_BY_EMAIL = "dao.user.get_by_email";

	public UserDao(IConnectionPool connectionPool, ExtractBehavior buildBehavior) {
		super(connectionPool, buildBehavior);
		LOG.info("User dao has been initialized");
	}

	@Override
	public User findById(long id) throws DatabaseException {
		User user = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_ID);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = (User) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding user by id failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return user;
	}

	@Override
	public int save(User model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, SAVE);
			setUser(statement, model);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Saving user failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public int update(User model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, UPDATE);
			setUser(statement, model);
			statement.setLong(9, model.getId());
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Updating user failed", e);
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
			throw new DatabaseException("Deleting user failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public List<User> list() throws DatabaseException {
		List<User> list = new ArrayList<User>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((User) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding user list failed", e);
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
			throw new DatabaseException("Counting user failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return count;
	}

	@Override
	public User findByEmail(String email) throws DatabaseException {
		if(email == null) {
			throw new DatabaseException("Email is null");
		}
		User user = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_EMAIL);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = (User) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding user by email failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return user;
	}

	private void setUser(PreparedStatement statement, User user)
			throws SQLException {
		int i = 0;
		statement.setString(++i, user.getEmail());
		statement.setInt(++i, user.getPassword());
		statement.setBoolean(++i, user.isAdmin());
		statement.setString(++i, user.getName());
		statement.setString(++i, user.getSurname());
		statement.setString(++i, user.getPassportNumber());
		statement.setString(++i, user.getAddress());
		statement.setString(++i, user.getPhone());
	}
}
