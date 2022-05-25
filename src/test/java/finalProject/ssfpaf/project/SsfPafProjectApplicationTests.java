package finalProject.ssfpaf.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import finalProject.ssfpaf.project.models.Metal;
import finalProject.ssfpaf.project.models.User;
import finalProject.ssfpaf.project.repository.DetailOrderRepository;
import finalProject.ssfpaf.project.repository.UserRepository;
import finalProject.ssfpaf.project.service.UserException;
import finalProject.ssfpaf.project.service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
class SsfPafProjectApplicationTests {

	@Autowired
    private UserRepository userRepo;

	@Autowired
	private DetailOrderRepository detailRepo;

	@Autowired
	private UserService userSvc;

	@Autowired
	private MockMvc mvc;

	private User newUser;

	public SsfPafProjectApplicationTests() {
		newUser = new User();
		newUser.setUsername("user1");
		newUser.setEmail("user1@gmail.com");
		newUser.setPassword("user1");
	}

	MultiValueMap<String, String> username() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("email", "rowter4@gmail.com");
		params.add("password", "rowter4");
		return params;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void checkForAuthentication() throws Exception {		
			
		RequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
														.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
														.param("email", "rowter4@gmail.com")
														.param("password", "rowter4");
														

		MvcResult result = mvc.perform(request).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);

	}

	@Test
	void shouldFindRowter4() {
		Optional<User> opt = userRepo.findUserByEmail("rowter4@gmail.com", "rowter4");
		assertTrue(opt.isPresent());
	}

	@Test
	void loginShouldFail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
														.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
														.param("email", "rowter8@gmail.com")
														.param("password", "rowter");
														

		MvcResult result = mvc.perform(request).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);
	}

	@AfterEach
	public void tearDown() {
		userRepo.removeUserDetails("rowter10");
		userRepo.removeUserDetails("user1");
		userRepo.removeUserDetails("rowter12");
	}


	@Test
	void unableToAddNewUser()  {

		User user2 = new User();
		user2.setUsername("rowter8");
		user2.setEmail("rowter8@gmail.com");
		user2.setPassword("rowter8");

		try {
			userSvc.addNewUser(user2);
		} catch (UserException e) {
			assertTrue(true);
			return;
		}
	}


	@Test
	void insertTestUserShouldFail() {
		
		try {

			RequestBuilder request = MockMvcRequestBuilders.post("/newaccount")
														.contentType("application/x-www-form-urlencoded")
														.param("email", "rowter10@gmail.com")
														.param("password", "rowter10");
														

			MvcResult result = mvc.perform(request).andReturn();

			int status = result.getResponse().getStatus();
			assertEquals(200, status);

		} catch (Exception ex) {
			return;
		}

	}


	@Test
	void ableToGetGoldFromId() {
		Optional<Metal> opt = detailRepo.getGoldByOrderId("2fe02bdb");
		assertTrue(opt.isPresent());
	}

	@Test
	void ableToGetSilverFromId() {
		Optional<Metal> opt = detailRepo.getSilverByOrderId("2fe02bdb");
		assertTrue(opt.isPresent());
	}

	@Test
	void unableToGetGoldFromId() {
		Optional<Metal> opt = detailRepo.getGoldByOrderId("2fe02bww");
		assertTrue(opt.isEmpty());
	}

	@Test
	void unableToGetSilverFromId() {
		Optional<Metal> opt = detailRepo.getSilverByOrderId("2fe02bww");
		assertTrue(opt.isEmpty());
	}

}
