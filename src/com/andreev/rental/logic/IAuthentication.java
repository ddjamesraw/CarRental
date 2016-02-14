package com.andreev.rental.logic;

import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.model.User;

public interface IAuthentication {

	public boolean login(MessageManager message, User user);

	public boolean register(MessageManager message, User user);

	public boolean edit(MessageManager message, User user);

	public User getUser(MessageManager message, String email)
			throws LogicException;

	boolean isRegistered(String email);

	boolean checkPasswords(MessageManager message, String password,
			String confirmPassword);

	boolean checkPasswords(MessageManager message, User user, int password);

}
