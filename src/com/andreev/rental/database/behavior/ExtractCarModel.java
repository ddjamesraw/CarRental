package com.andreev.rental.database.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarModel;
import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.Entity;

public class ExtractCarModel extends ExtractBehavior {
	
	private static final int COUNT = 2;

	private ExtractBehavior buildBrand = new ExtractBrand();

	public ExtractCarModel() {
	}

	@Override
	public Entity build(ResultSet resultSet, int position) throws SQLException {
		CarModel carModel = (CarModel) FACTORY.newEntity(EEntity.CAR_MODEL);
		Brand brand = null;
		carModel.setId(resultSet.getLong(position++));
		carModel.setName(resultSet.getString(position++));
		brand = (Brand) buildBrand.build(resultSet, position);
		carModel.setBrand(brand);
		return carModel;
	}

	@Override
	public int getCount() {
		int count = COUNT + buildBrand.getCount();
		return count;
	}

}
