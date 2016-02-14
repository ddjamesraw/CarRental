package com.andreev.rental.database.dao;

import java.util.List;

import com.andreev.rental.database.DatabaseException;
import com.andreev.rental.model.Entity;

public interface IDao<T extends Entity> {

	public T findById(long id) throws DatabaseException;

	public int save(T model) throws DatabaseException;

	public int update(T model) throws DatabaseException;

	public int delete(long id) throws DatabaseException;

	public List<T> list() throws DatabaseException;

	public int count() throws DatabaseException;
}
