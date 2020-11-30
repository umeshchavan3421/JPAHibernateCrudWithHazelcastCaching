package com.uxpsystems.assignment.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uxpsystems.assignment.model.User;
import com.uxpsystems.assignment.service.UserService;

/**
 * This call is controller class which handles all rest call from FrontEnd
 * @author Umesh .Chavan
 *
 */

@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private  UserService userService;

	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		long start = System.currentTimeMillis();
		List<User> users = userService.getAllUsers();
		long end = System.currentTimeMillis();
		LOGGER.info("Returned all users in {} ms. [{} Sec]", (end - start), (end - start) / 1000);
		LOGGER.info("Returned all users: {}", users);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * Handler method to handle the request of retrieving specific User details with
	 * name
	 * 
	 * @param name
	 * 
	 * @return ResponseEntity<? extends Object> - User or error response if not
	 *         found.
	 */
	@GetMapping("/user/{name}")
	public ResponseEntity<User> getUser(@PathVariable String name) {
		long start = System.currentTimeMillis();
		User user = userService.getUser(name);
		long end = System.currentTimeMillis();
		LOGGER.info("Returned User in {} ms. [{} Sec]", (end - start), (end - start) / 1000);
		LOGGER.info("Returned User: {}", user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Handler method to handle the request of updating User details.
	 * 
	 * @param user
	 * 
	 * @return User
	 */
	@PutMapping(value = "/update/user")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		long start = System.currentTimeMillis();
		userService.updateUser(user);
		long end = System.currentTimeMillis();
		LOGGER.info("Successfully updated User in {} ms. [{} Sec]", (end - start), (end - start) / 1000);
		LOGGER.info("Successfully updated User: {}", user);
		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}

	/**
	 * Handler method to handle the request of deleting User
	 * 
	 * @param id
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete/user/{name}")
	public ResponseEntity<String> deleteUser(@PathVariable String name) {
		long start = System.currentTimeMillis();
		userService.deleteUser(name);
		long end = System.currentTimeMillis();
		LOGGER.info("User deleted successfully in {} ms. [{} Sec]", (end - start), (end - start) / 1000);
		LOGGER.info("User deleted successfully with name: {}", name);

		return new ResponseEntity<>("User deleted successfully",HttpStatus.OK);
	}

	/**
	 * Handler method to handle the request of deleting all User
	 * 
	 * 
	 * @return String
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/delete/users")
	public ResponseEntity<String> deleteAllUser() {
		long start = System.currentTimeMillis();
		userService.deleteAllUsers();
		long end = System.currentTimeMillis();
		LOGGER.info("All users deleted successfully in {} ms. [{} Sec]", (end - start), (end - start) / 1000);
		LOGGER.info("All users deleted successfully");
		return new ResponseEntity<>("All Users entries deleted successflly", HttpStatus.OK);
	}

	/**
	 * Handler method to handle the request to add new User.
	 * Will return added user
	 * @param user    - User
	 * 
	 * @return User
	 */
	@PostMapping(value = "/add/user")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		long start = System.currentTimeMillis();
		User userRespone = userService.addUser(user);
		long end = System.currentTimeMillis();
		LOGGER.info("Successfully added User in {} ms. [{} Sec]", (end - start), (end - start) / 1000);
		LOGGER.info("Successfully added User: {}", user);
		return new ResponseEntity<>(userRespone, HttpStatus.OK);
	}

	@GetMapping("/demo")
	public String getName() {
		return "Umesh";
	}

}
