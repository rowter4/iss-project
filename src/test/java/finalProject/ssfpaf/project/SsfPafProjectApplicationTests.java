package finalProject.ssfpaf.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import finalProject.ssfpaf.project.models.User;
import finalProject.ssfpaf.project.repository.UserRepository;
import finalProject.ssfpaf.project.service.UserException;
import finalProject.ssfpaf.project.service.UserService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@SpringBootTest
@AutoConfigureMockMvc
class SsfPafProjectApplicationTests {

	@Autowired
    private UserRepository userRepo;

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

		JsonObject reqPayload = Json.createObjectBuilder()
			.add("username", "rowter4@gmail.com") 
			.add("password", "rowter4") 
			.build();
		
			
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

	@BeforeEach
	public void setup() {
		userRepo.addUserDetails(newUser.getUsername(), newUser.getEmail(), newUser.getPassword());
	}

	@AfterEach
	public void tearDown() {
		userRepo.removeUserDetails(newUser.getUsername());
	}

	@Test
	void insertTestUserShouldFail() {
		
		try {
			userSvc.addNewUser(newUser);
		} catch (Exception ex) {
			assertFalse(false);
			return;
		}

	}

}
