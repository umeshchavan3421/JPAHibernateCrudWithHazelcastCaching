package com.uxpsystems.assignment.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.uxpsystems.assignment.dao.UserRepository;
import com.uxpsystems.assignment.exception.UserException;
import com.uxpsystems.assignment.exception.UserNotFoundException;
import com.uxpsystems.assignment.model.User;

/**
 * Service class to request for the User details database operation like
 * <code>Add, Update, Delete and retrieve<code>. It utilizes the User repository
 * to perform all DAO operations.
 * 
 * @author Umesh.Chavan
 *
 */

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;

	public UserServiceImpl() {
		super();
	}

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Cacheable(cacheNames = { "UserCache" })
	@Transactional(value = "jpaTransactionManager")
	public List<User> getAllUsers() {
		LOGGER.info("Fetching all User details.");
		List<User> users = userRepository.findAll();

		if (CollectionUtils.isEmpty(users)) {
			throw new UserNotFoundException("No data for Users.");
		}
		System.out.println("First Hit to DB findAll");
		return users;
	}

	/**
	 * Retrieve User by Name
	 * 
	 * @param name - User Name (unique)
	 * 
	 * @return User
	 */
	@Transactional(value = "jpaTransactionManager")
	@Cacheable(value = "UserCache", key = "#name", unless = "#result == null")
	public User getUser(String name) {
		LOGGER.info("Fetching user with Name: {}", name);
		Optional<User> user = userRepository.findByUserName(name);
		System.out.println("First Hit to DB get by name");
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UserNotFoundException("User [" + name + "] doesn't exist");
		}
	}

	/**
	 * Update the User details in database.
	 * 
	 * @param User
	 * 
	 */
	@Transactional(value = "jpaTransactionManager")
	@Cacheable(cacheNames = { "UserCache" })
	public User updateUser(User user) {
		LOGGER.info("Updating user details: {}", user);
		Optional<User> existingUser = userRepository.findById(user.getId());
		if (!existingUser.isPresent()) {
			throw new UserNotFoundException("Failed to update the user [" + user + "] as user doesn't exist");
		}
		User updatedUser = userRepository.save(user);
		if (null == updatedUser) {
			LOGGER.error("Failed to update the user: {}", user);
			throw new UserException("Failed to update user [" + user + "]");
		}
		return updatedUser;
	}

	/**
	 * Delete the User from Database
	 * 
	 * @param id - User ID
	 * 
	 * @return
	 */
	@Transactional(value = "jpaTransactionManager")
	@CacheEvict(cacheNames = "UserCache", key = "#id", allEntries = true)
	public void deleteUser(long id) {
		LOGGER.info("Deleting user with ID: {}", id);
		userRepository.deleteById(id);
		System.out.println("User Deleted");
	}

	/**
	 * Delete all User from Database
	 * 
	 * @param id - User ID
	 * 
	 * @return
	 */
	@Transactional(value = "jpaTransactionManager")
	@CacheEvict(value = "UserCache", allEntries = true)
	public void deleteAllUsers() {
		LOGGER.info("Deleting all users");
		userRepository.deleteAll();
	}

	/**
	 * Insert new User to database.
	 * 
	 * @param User
	 * 
	 * @return User
	 */
	@Transactional(value = "jpaTransactionManager")
	@Cacheable(cacheNames = { "UserCache" })
	public User addUser(User user) {
		LOGGER.info("Adding user: {}", user);
		Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
		if (existingUser.isPresent()) {
			throw new UserException("User already exist with the same name [" + user.getUserName() + "]");
		}
		User savedUser = userRepository.save(user);
		if (null == savedUser) {
			LOGGER.error("Failed to add user: {}", user);
			throw new UserException("Failed to add user [" + user + "]");
		}

		return savedUser;
	}

}
