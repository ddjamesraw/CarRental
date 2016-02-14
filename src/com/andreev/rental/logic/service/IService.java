package com.andreev.rental.logic.service;

import java.util.List;

import com.andreev.rental.logic.LogicException;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.Entity;

public interface IService<T extends Entity> {

	public T get(long id, MessageManager message) throws LogicException;

	public List<T> list(MessageManager message) throws LogicException;

	public List<T> list(Entity entity, MessageManager message)
			throws LogicException;

	public void save(T entity, MessageManager message) throws LogicException;

	public void edit(T entity, MessageManager message) throws LogicException;

	public void delete(long id, MessageManager message) throws LogicException;

	public int count(MessageManager message) throws LogicException;

}
