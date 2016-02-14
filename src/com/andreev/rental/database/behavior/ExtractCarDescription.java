package com.andreev.rental.database.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.CarModel;
import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.Entity;

public class ExtractCarDescription extends ExtractBehavior {
	
	private static final int COUNT = 10;

	private ExtractBehavior buildCarModel = new ExtractCarModel();

	public ExtractCarDescription() {
	}

	@Override
	public Entity build(ResultSet resultSet, int position) throws SQLException {
		CarDescription carDescription = (CarDescription) FACTORY
				.newEntity(EEntity.CAR_DESCRIPTION);
		CarModel carModel = null;
		carDescription.setId(resultSet.getLong(position++));
		carDescription.setPrice(resultSet.getInt(position++));
		carDescription.setDoors(resultSet.getInt(position++));
		carDescription.setSeats(resultSet.getInt(position++));
		carDescription.setConsumption(resultSet.getInt(position++));
		carDescription.setAirCondition(resultSet.getBoolean(position++));
		carDescription.setAirBags(resultSet.getBoolean(position++));
		carDescription.setAutomatic(resultSet.getBoolean(position++));
		carDescription.setDescription(resultSet.getString(position++));
		carDescription.setImgUrl(resultSet.getString(position++));
		carModel = (CarModel) buildCarModel.build(resultSet, position);
		carDescription.setModel(carModel);
		return carDescription;
	}

	@Override
	public int getCount() {
		int count = COUNT + buildCarModel.getCount();
		return count;
	}

}
