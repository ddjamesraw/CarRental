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
import com.andreev.rental.model.Order;
import com.andreev.rental.model.OrderDetail;

public class DetailDao extends BaseDao implements IDetailDao {

	private static final Logger LOG = Logger.getLogger(DetailDao.class);
	private static final String GET_BY_ID = "dao.order_detail.get_by_id";
	private static final String SAVE = "dao.order_detail.save";
	private static final String UPDATE = "dao.order_detail.update";
	private static final String DELETE = "dao.order_detail.delete";
	private static final String LIST = "dao.order_detail.list";
	private static final String COUNT = "dao.order_detail.count";
	private static final String LIST_BY_ORDER = "dao.order_detail.list_by_order";
	private static final String INC_ORDER_TOTAL = "dao.order_detail.inc_order_total";
	private static final String DEC_ORDER_TOTAL = "dao.order_detail.dec_order_total";

	public DetailDao(IConnectionPool connectionPool,
			ExtractBehavior buildBehavior) {
		super(connectionPool, buildBehavior);
		LOG.info("Order detail dao has been initiatiled");
	}

	@Override
	public OrderDetail findById(long id) throws DatabaseException {
		OrderDetail orderDetail = null;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, GET_BY_ID);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				orderDetail = (OrderDetail) getEntity(resultSet);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finding order detail bby id failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return orderDetail;
	}

	@Override
	public int save(OrderDetail model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		Connection connection = connectionPool.takeConnection();
		PreparedStatement saveStatement = null;
		PreparedStatement incStatement = null;
		try {
			connection.setAutoCommit(false);
			saveStatement = openPrepStatement(connection, SAVE);
			setOrderDetail(saveStatement, model);
			rows = saveStatement.executeUpdate();
			incStatement = openPrepStatement(connection, INC_ORDER_TOTAL);
			incStatement.setInt(1, model.getPrice());
			incStatement.setLong(2, model.getOrderId());
			incStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				LOG.error("Rollback failed", e);
			}
			throw new DatabaseException("Saving order detail failed", e);
		} finally {
			closeStatement(saveStatement);
			closeStatement(incStatement);
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					LOG.error("SetAutoCommit failed", e);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public int update(OrderDetail model) throws DatabaseException {
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		int rows = 0;
		OrderDetail oldModel = findById(model.getId());
		if(oldModel == null) {
			throw new DatabaseException("Old model is null");
		}
		Connection connection = connectionPool.takeConnection();
		PreparedStatement updateStatement = null;
		PreparedStatement incStatement = null;
		PreparedStatement decStatement = null;
		try {
			connection.setAutoCommit(false);
			updateStatement = openPrepStatement(connection, UPDATE);
			setOrderDetail(updateStatement, model);
			updateStatement.setLong(4, model.getId());
			rows = updateStatement.executeUpdate();
			incStatement = openPrepStatement(connection, INC_ORDER_TOTAL);
			incStatement.setInt(1, model.getPrice());
			incStatement.setLong(2, model.getOrderId());
			incStatement.executeUpdate();
			decStatement = openPrepStatement(connection, DEC_ORDER_TOTAL);
			decStatement.setInt(1, oldModel.getPrice());
			decStatement.setLong(2, model.getOrderId());
			decStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				LOG.error("Rollback failed", e);
			}
			throw new DatabaseException("Updating order detail failed", e);
		} finally {
			closeStatement(updateStatement);
			closeStatement(incStatement);
			closeStatement(decStatement);
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					LOG.error("SetAutoCommit failed", e);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public int delete(long id) throws DatabaseException {
		int rows = 0;
		OrderDetail model = findById(id);
		if(model == null) {
			throw new DatabaseException("Model is null");
		}
		Connection connection = connectionPool.takeConnection();
		PreparedStatement delStatement = null;
		PreparedStatement decStatement = null;
		try {
			connection.setAutoCommit(false);
			delStatement = openPrepStatement(connection, DELETE);
			delStatement.setLong(1, id);
			rows = delStatement.executeUpdate();
			decStatement = openPrepStatement(connection, DEC_ORDER_TOTAL);
			decStatement.setInt(1, model.getPrice());
			decStatement.setLong(2, model.getOrderId());
			decStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				LOG.error("Rollback failed", e);
			}
			throw new DatabaseException("Deleting order detail failed", e);
		} finally {
			closeStatement(delStatement);
			closeStatement(decStatement);
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					LOG.error("SetAutoCommit failed", e);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return rows;
	}

	@Override
	public List<OrderDetail> list() throws DatabaseException {
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((OrderDetail) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Finins order detail list failed", e);
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
			throw new DatabaseException("Counting order detail failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return count;
	}

	@Override
	public List<OrderDetail> listByOrder(Order order) throws DatabaseException {
		if(order == null) {
			throw new DatabaseException("Order is null");
		}
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		Connection connection = connectionPool.takeConnection();
		PreparedStatement statement = null;
		try {
			statement = openPrepStatement(connection, LIST_BY_ORDER);
			statement.setLong(1, order.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add((OrderDetail) getEntity(resultSet));
			}
		} catch (SQLException e) {
			throw new DatabaseException(
					"Finding order detail list by order failed", e);
		} finally {
			closeStatement(statement);
			connectionPool.releaseConnection(connection);
		}
		return list;
	}

	private void setOrderDetail(PreparedStatement statement,
			OrderDetail orderDetail) throws SQLException {
		int i = 0;
		statement.setLong(++i, orderDetail.getOrderId());
		statement.setInt(++i, orderDetail.getPrice());
		statement.setString(++i, orderDetail.getDescription());
	}
}
