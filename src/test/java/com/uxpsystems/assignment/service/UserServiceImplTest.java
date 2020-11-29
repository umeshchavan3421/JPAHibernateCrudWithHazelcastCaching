package com.uxpsystems.assignment.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.uxpsystems.assignment.dao.UserRepository;
import com.uxpsystems.assignment.model.User;
import com.uxpsystems.assignment.utils.CommonUtils;

/**
 * 
 * @author Umesh.Chavan
 *
 */
@SpringBootTest(classes = UserServiceImpl.class)
public class UserServiceImplTest {

	@MockBean
	private UserRepository repository;

	@InjectMocks
	private UserServiceImpl service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * It tests success response to get list of User.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void getAllUsersSuccessTest() throws Exception {
		@SuppressWarnings("unchecked")
		List<User> mockResponse = CommonUtils.mapObject(List.class, "/all_user_response_mock.json");
		Mockito.when(repository.findAll()).thenReturn(mockResponse);
		List<User> users = service.getAllUsers();

		System.out.println(users);
		assertNotNull(users);
	}

	/**
	 * It tests success response to User by name.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void getUserSuccessTest() throws Exception {
		Optional<User> mockResponse = Optional.of(CommonUtils.mapObject(User.class, "/single_user_response_mock.json"));
		Mockito.when(repository.findByUserName(Mockito.anyString())).thenReturn(mockResponse);
		User user = service.getUser(Mockito.anyString());
		System.out.println(user);
		assertNotNull(user);
	}

	/**
	 * It tests success response to update user by username
	 * 
	 * @throws Exception
	 * 
	 */
	@Ignore
	public void updateUserSuccessTest() throws Exception {
		Optional<User> mockResponse = Optional.of(CommonUtils.mapObject(User.class, "/single_user_response_mock.json"));

		User updateResponse = CommonUtils.mapObject(User.class, "/update_user_response_mock.json");

		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(mockResponse);
		Mockito.when(repository.save(Mockito.any())).thenReturn(updateResponse);
		User user = service.updateUser(mockResponse.get());
		System.out.println(user);
		assertNotNull(user);
	}

	/**
	 * It tests success response to delete user by id
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void deleteUserSuccessTest() throws Exception {
		service.deleteUser(Mockito.anyLong());
	}

	/**
	 * It tests success response to add new user
	 * 
	 * @throws Exception
	 * 
	 */
	@Ignore
	public void addUserSuccessTest() throws Exception {
		Optional<User> mockResponse = Optional.of(CommonUtils.mapObject(User.class, "/single_user_response_mock.json"));

		User updateResponse = CommonUtils.mapObject(User.class, "/update_user_response_mock.json");

		Mockito.when(repository.findByUserName(Mockito.anyString())).thenReturn(mockResponse);
		Mockito.when(repository.save(Mockito.any())).thenReturn(updateResponse);
		User user = service.updateUser(mockResponse.get());
		System.out.println(user);
		assertNotNull(user);
	}
}
