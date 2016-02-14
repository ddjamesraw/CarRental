package com.andreev.rental.database.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.andreev.rental.model.Car;
import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.EOrderStatus;
import com.andreev.rental.model.Entity;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.User;

public class ExtractOrder extends ExtractBehavior {

	private static final int COUNT = 6;

	private ExtractBehavior buildCar = new ExtractCar();
	private ExtractBehavior buildUser = new ExtractUser();

	public ExtractOrder() {
	}

	@Override
	public Entity build(ResultSet resultSet, int position) throws SQLException {
		Order order = (Order) FACTORY.newEntity(EEntity.ORDER);
		User user = (User) FACTORY.newEntity(EEntity.USER);
		Car car = (Car) FACTORY.newEntity(EEntity.CAR);
		order.setId(resultSet.getLong(position++));
		order.setStatus(EOrderStatus.getStatus(resultSet.getInt(position++)));
		order.setFrom(new Date(resultSet.getDate(position++).getTime()));
		order.setTo(new Date(resultSet.getDate(position++).getTime()));
		order.setTotal(resultSet.getInt(position++));
		order.setInfo(resultSet.getString(position++));
		car = (Car) buildCar.build(resultSet, position);
		user = (User) buildUser.build(resultSet,
				position + buildCar.getCount());
		order.setCar(car);
		order.setUser(user);
		return order;
	}

	@Override
	public int getCount() {
		int count = COUNT + buildCar.getCount() + buildUser.getCount();
		return count;
	}

}
