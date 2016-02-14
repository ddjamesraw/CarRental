package com.andreev.rental.database.dao;

import java.util.List;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.User;

public interface IOrderDao extends IDao<Order> {

	public List<Order> listByUser(User user) throws DatabaseException;

	public List<Order> listByCar(Car car) throws DatabaseException;

}
