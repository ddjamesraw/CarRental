package com.andreev.rental.database.dao;

import java.util.List;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarModel;

public interface IModelDao extends IDao<CarModel> {

	public CarModel findByName(String name) throws DatabaseException;

	public List<CarModel> listByBrand(Brand brand) throws DatabaseException;

}
