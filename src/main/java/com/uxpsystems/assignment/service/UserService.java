package com.uxpsystems.assignment.service;

import java.util.List;

import javax.validation.Valid;

import com.uxpsystems.assignment.model.User;

public interface UserService {

	/**
	 * This method is used to get all user from DB
	 * @return
	 */
	List<User> getAllUsers();

	/**
	 * This method is used to get user from DB with name passed
	 * @param name
	 * @return
	 */
	User getUser(String name);

	/**
	 * This method is used to delete user from DB based on id passed
	 * @param id
	 */
	void deleteUser(long id);

	/**
	 * This method is used to update user data in DB
	 * @param user
	 * @return
	 */
	User updateUser(@Valid User user);

	/**
	 * This method is used to delete all user entries from DB
	 */
	void deleteAllUsers();

	/**
	 * This method is used to add new user to DB
	 * @param user
	 * @return
	 */
	User addUser(@Valid User user);

}
