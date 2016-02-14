package com.andreev.rental.database.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.resources.DatabaseResources;
import com.andreev.rental.resources.IResources;

public class ConnectionPool implements IConnectionPool {

	private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
	private static final Lock LOCK = new ReentrantLock();
	private static final String POOLSIZE = "connection.poolsize";
	private static final String DRIVER = "connection.driver";
	private static final String URL = "connection.url";
	private static final String USER = "connection.user";
	private static final String PASSWORD = "connection.password";
	private static volatile ConnectionPool instance;

	private BlockingQueue<Connection> connectionQueue;
	private IResources resources;

	private ConnectionPool() throws DatabaseException {
		resources = DatabaseResources.getInstance();
		String poolSize = resources.getString(POOLSIZE);
		String driverName = resources.getString(DRIVER);
		String url = resources.getString(URL);
		String user = resources.getString(USER);
		String password = resources.getString(PASSWORD);
		int size = Integer.parseInt(poolSize);
		connectionQueue = new ArrayBlockingQueue<Connection>(size);
		registerDriver(driverName);
		fillConnectionPool(size, url, user, password);
		LOG.info("Connection pool was initialized");
	}

	public static IConnectionPool getInstance() throws DatabaseException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new ConnectionPool();
					LOG.info("Connection pool has been initialized");
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public void dispose() {
		if (instance != null) {
			try {
				LOCK.lock();
				Connection connection;
				while ((connection = connectionQueue.poll()) != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						LOG.error("Connection cloasing failed", e);
					}
				}
				instance = null;
				LOG.info("Connection pool was disposed");
			} finally {
				LOCK.unlock();
			}
		}
	}

	@Override
	public Connection takeConnection() throws DatabaseException {
		Connection connection = null;
		try {
			connection = connectionQueue.take();
		} catch (InterruptedException e) {
			throw new DatabaseException("Taking connection failed", e);
		}
		return connection;
	}

	@Override
	public void releaseConnection(Connection connection)
			throws DatabaseException {
		if(connection == null) {
			throw new DatabaseException("Connection is null");
		}
		try {
			if (!connection.isClosed()) {
				if (!connectionQueue.offer(connection)) {
					throw new DatabaseException("Connection pool is full");
				}
			} else {
				throw new DatabaseException("Connection is closed");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Release connection failed", e);
		}

	}

	private void registerDriver(String driverName) throws DatabaseException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			throw new DatabaseException("Driver creating failed", e);
		}
	}

	private void fillConnectionPool(int size, String url, String user,
			String password) throws DatabaseException {
		try {
			for (int i = 0; i < size; i++) {
				Connection connection = DriverManager.getConnection(url, user,
						password);
				connectionQueue.offer(connection);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Connection creating failed", e);
		}
	}
}
