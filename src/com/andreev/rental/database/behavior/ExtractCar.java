package com.andreev.rental.database.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.andreev.rental.model.Car;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.Entity;

public class ExtractCar extends ExtractBehavior {
	
	private static final int COUNT = 3;

	private ExtractBehavior buildCarDescription = new ExtractCarDescription();

	public ExtractCar() {
	}

	@Override
	public Entity build(ResultSet resultSet, int position) throws SQLException {
		Car car = (Car) FACTORY.newEntity(EEntity.CAR);
		CarDescription carDescription = null;
		car.setId(resultSet.getLong(position++));
		car.setAvailable(resultSet.getBoolean(position++));
		car.setDescription(resultSet.getString(position++));
		carDescription = (CarDescription) buildCarDescription.build(resultSet,
				position);
		car.setCarDescription(carDescription);
		return car;
	}

	@Override
	public int getCount() {
		int count = COUNT + buildCarDescription.getCount();
		return count;
	}

}
