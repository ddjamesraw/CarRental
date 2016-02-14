package com.andreev.rental.database.dao;

import java.util.List;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.OrderDetail;

public interface IDetailDao extends IDao<OrderDetail> {

	public List<OrderDetail> listByOrder(Order order) throws DatabaseException;

}
