package com.andreev.rental.database.dao;

import java.util.List;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.CarModel;

public interface IDescriptionDao extends IDao<CarDescription> {

	public List<CarDescription> listByModel(CarModel model)
			throws DatabaseException;

	public List<CarDescription> listByBrand(Brand brand)
			throws DatabaseException;

}
