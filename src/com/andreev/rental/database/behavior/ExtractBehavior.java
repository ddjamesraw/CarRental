package com.andreev.rental.database.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.andreev.rental.model.Entity;
import com.andreev.rental.model.IEntityFactory;
import com.andreev.rental.model.EntityFactory;

public abstract class ExtractBehavior {
	
	protected static final IEntityFactory FACTORY = EntityFactory.getInstance();
	
	public ExtractBehavior() {
	}
	
	public abstract Entity build(ResultSet resultSet, int position) throws SQLException;
	
	public abstract int getCount();

}
