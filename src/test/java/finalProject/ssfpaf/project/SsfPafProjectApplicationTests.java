package finalProject.ssfpaf.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import finalProject.ssfpaf.project.models.User;
import finalProject.ssfpaf.project.repository.UserRepository;
import finalProject.ssfpaf.project.service.UserException;
import finalProject.ssfpaf.project.service.UserService;

@SpringBootTest
class SsfPafProjectApplicationTests {

	@Autowired
    private UserRepository userRepo;

	@Autowired
	private UserService userSvc;


	private User newUser;

	public SsfPafProjectApplicationTests() {
		newUser = new User();
		newUser.setUsername("user1");
		newUser.setEmail("user1@gmail.com");
		newUser.setPassword("user1");
	}

	@Test
	void contextLoads() {
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
