package com.andreev.rental.database.dao;

import java.util.List;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.Order;

public interface ICarDao extends IDao<Car> {

	public List<Car> listByCarDescription(CarDescription carDescription)
			throws DatabaseException;

	public long idByOrderDescription(Order order, long descriptionId)
			throws DatabaseException;

}
