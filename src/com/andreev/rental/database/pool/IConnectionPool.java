package com.andreev.rental.database.pool;

import java.sql.Connection;

import com.andreev.rental.database.DatabaseException;

public interface IConnectionPool {

	Connection takeConnection() throws DatabaseException;

	void releaseConnection(Connection connection) throws DatabaseException;

	void dispose();

}
