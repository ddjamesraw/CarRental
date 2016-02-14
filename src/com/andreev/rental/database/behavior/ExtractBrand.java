package com.andreev.rental.database.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.andreev.rental.model.Brand;
import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.Entity;

public class ExtractBrand extends ExtractBehavior {
	
	private static final int COUNT = 2;

	public ExtractBrand() {
	}

	@Override
	public Entity build(ResultSet resultSet, int position) throws SQLException {
		Brand brand = (Brand) FACTORY.newEntity(EEntity.BRAND);
		brand.setId(resultSet.getLong(position++));
		brand.setName(resultSet.getString(position++));
		return brand;
	}

	@Override
	public int getCount() {
		return COUNT;
	}

}
