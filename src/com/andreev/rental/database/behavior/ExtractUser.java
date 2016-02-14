package com.andreev.rental.database.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.Entity;
import com.andreev.rental.model.User;

public class ExtractUser extends ExtractBehavior {
	
	private static final int COUNT = 9;

	public ExtractUser() {
	}

	@Override
	public Entity build(ResultSet resultSet, int position) throws SQLException {
		User user = (User) FACTORY.newEntity(EEntity.USER);
		user.setId(resultSet.getLong(position++));
		user.setEmail(resultSet.getString(position++));
		user.setPassword(resultSet.getInt(position++));
		user.setAdmin(resultSet.getBoolean(position++));
		user.setName(resultSet.getString(position++));
		user.setSurname(resultSet.getString(position++));
		user.setPassportNumber(resultSet.getString(position++));
		user.setAddress(resultSet.getString(position++));
		user.setPhone(resultSet.getString(position++));
		return user;
	}

	@Override
	public int getCount() {
		return COUNT;
	}

}
