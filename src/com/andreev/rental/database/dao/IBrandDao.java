package com.andreev.rental.database.dao;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.model.Brand;

public interface IBrandDao extends IDao<Brand> {
	
	public Brand findByName(String name) throws DatabaseException;

}
