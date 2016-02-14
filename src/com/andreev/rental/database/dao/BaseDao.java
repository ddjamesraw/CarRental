package com.andreev.rental.database.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.andreev.rental.database.behavior.ExtractBehavior;
import com.andreev.rental.database.pool.IConnectionPool;
import com.andreev.rental.model.Entity;
import com.andreev.rental.resources.DatabaseResources;
import com.andreev.rental.resources.IResources;

public abstract class BaseDao {
	
	private static final Logger LOG = Logger.getLogger(BaseDao.class);
	
	private ExtractBehavior extract;
	protected IConnectionPool connectionPool;
	protected IResources resources;

	public BaseDao(IConnectionPool connectionPool, ExtractBehavior extract) {
		this.resources = DatabaseResources.getInstance();
		this.connectionPool = connectionPool;
		this.extract = extract;
	}
	
	protected PreparedStatement openPrepStatement(Connection connection,
			String sqlPattern) throws SQLException {
		PreparedStatement statement = null;
		String sql = resources.getString(sqlPattern);
		statement = connection.prepareStatement(sql);
		return statement;
	}
	
	protected CallableStatement openCallStatement(Connection connection,
			String sqlPattern) throws SQLException {
		CallableStatement statement = null;
		String sql = resources.getString(sqlPattern);
		statement = connection.prepareCall(sql);
		return statement;
	}

	protected void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOG.error("Statement closing failed", e);
			}
		}
	}

	protected Entity getEntity(ResultSet resultSet) throws SQLException {
		return extract.build(resultSet, 1);
	}
}
