package com.andreev.rental.database.dao;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.model.User;

public interface IUserDao extends IDao<User> {

	public User findByEmail(String email) throws DatabaseException;

}
