package com.uxpsystems.assignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.uxpsystems.assignment.exception.UserException;
import com.uxpsystems.assignment.exception.UserNotFoundException;
import com.uxpsystems.assignment.model.User;
import com.uxpsystems.assignment.service.UserService;
import com.uxpsystems.assignment.utils.CommonUtils;

/**
 * 
 * @author Umesh.Chavan
 *
 */
@SpringBootTest(classes = UserController.class)
public class UserControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private UserService userServiceMock;

	@InjectMocks
	private UserController controller;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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
		Mockito.when(userServiceMock.getAllUsers()).thenReturn(mockResponse);
		String controllerResponse = mockMvc.perform(get("/users")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		@SuppressWarnings("unchecked")
		List<User> userList = CommonUtils.mapStringToObject(controllerResponse, List.class);
		System.out.println(userList);
		assertNotNull(userList);
	}

	/**
	 * It tests failure response for no user
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void getAllUsersFailureNoUserTest() throws Exception {
		Mockito.when(userServiceMock.getAllUsers()).thenThrow(new UserNotFoundException("User doesn't exist"));
		MvcResult resp = mockMvc.perform(get("/users")).andExpect(status().is4xxClientError())
				.andExpect(status().isNotFound()).andReturn();
	}

	/**
	 * It tests success response to get user by name
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void getUserByNameTest() throws Exception {
		User mockResponse = CommonUtils.mapObject(User.class, "/single_user_response_mock.json");
		Mockito.when(userServiceMock.getUser(Mockito.anyString())).thenReturn(mockResponse);
		String controllerResponse = mockMvc.perform(get("/user/TestUser1")).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		User userList = CommonUtils.mapStringToObject(controllerResponse, User.class);
		System.out.println(userList);
		assertNotNull(userList);
	}

	/**
	 * It tests failure response to get user by name
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void getUserByNameFailureTest() throws Exception {
		Mockito.when(userServiceMock.getUser(Mockito.anyString()))
				.thenThrow(new UserNotFoundException("User doesn't exist"));
		MvcResult resp = mockMvc.perform(get("/user/TestUser1")).andExpect(status().is4xxClientError())
				.andExpect(status().isNotFound()).andReturn();
	}

	/**
	 * It tests success response to update user
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void updateUserSuccessTest() throws Exception {
		User mockResponse = CommonUtils.mapObject(User.class, "/update_user_response_mock.json");
		String requestBody = "{\"id\": \"1\",\"userName\": \"TestUser1\",\"password\": \"password2\",\"status\": \"DeActivated\"}";
		Mockito.when(userServiceMock.updateUser(Mockito.any())).thenReturn(mockResponse);
		String controllerResponse = mockMvc
				.perform(put("/update/user").accept(MediaType.APPLICATION_JSON).content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		User userList = CommonUtils.mapStringToObject(controllerResponse, User.class);
		System.out.println(userList);
		assertNotNull(userList);
	}

	/**
	 * It tests Failure response to update user request body not passed
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void updateUserFailureTest() throws Exception {
		String requestBody = "{\"id\": \"1\",\"userName\": \"TestUser1\",\"password\": \"password2\",\"status\": \"DeActivated\"}";
		Mockito.when(userServiceMock.updateUser(Mockito.any())).thenThrow(new UserException("Failed to update user"));
		MvcResult controllerResponse = mockMvc.perform(put("/update/user")).andExpect(status().is4xxClientError())
				.andExpect(status().isBadRequest()).andReturn();
	}

	/**
	 * It tests success response to delete user by name
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void deleteUserSuccessTest() throws Exception {
		String controllerResponse = mockMvc.perform(delete("/delete/user/test1")).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		System.out.println(controllerResponse);
		assertEquals("User deleted successfully", controllerResponse);
	}

	/**
	 * It tests success response to delete all users
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void deleteAllUserSuccessTest() throws Exception {
		String controllerResponse = mockMvc.perform(delete("/delete/users")).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		System.out.println(controllerResponse);
		assertEquals("All Users entries deleted successflly", controllerResponse);
	}

	/**
	 * It tests success response to update user
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void addUserSuccessTest() throws Exception {
		User mockResponse = CommonUtils.mapObject(User.class, "/update_user_response_mock.json");
		String requestBody = "{\"id\": \"1\",\"userName\": \"TestUser1\",\"password\": \"password2\",\"status\": \"DeActivated\"}";
		Mockito.when(userServiceMock.addUser(Mockito.any())).thenReturn(mockResponse);
		String controllerResponse = mockMvc
				.perform(post("/add/user").accept(MediaType.APPLICATION_JSON).content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		User userList = CommonUtils.mapStringToObject(controllerResponse, User.class);
		System.out.println(userList);
		assertNotNull(userList);
	}

	/**
	 * It tests Failure response to update user request body not passed
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void addUserFailureTest() throws Exception {
		Mockito.when(userServiceMock.addUser(Mockito.any())).thenThrow(new UserException("Failed to update user"));
		MvcResult controllerResponse = mockMvc.perform(post("/add/user")).andExpect(status().is4xxClientError())
				.andExpect(status().isBadRequest()).andReturn();
	}

}
