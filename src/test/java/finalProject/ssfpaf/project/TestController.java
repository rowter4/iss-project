package finalProject.ssfpaf.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
// import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import finalProject.ssfpaf.project.repository.UserRepository;
import finalProject.ssfpaf.project.service.MetalService;
import finalProject.ssfpaf.project.service.OrderService;
import finalProject.ssfpaf.project.service.UserService;

// import sg.edu.nus.iss.vttpproject.repository.UserRepository;
// import sg.edu.nus.iss.vttpproject.services.UserService;

// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class TestController {

    @Autowired
    public UserService userSvc;

    @Autowired
    public MetalService metalSvc;

    @Autowired
    public OrderService orderSvc;

    @Autowired
    public UserRepository userRepo;

    @Autowired
    private MockMvc mvc;


    // Login Controller
    @Test
    public void signUpShouldReturn200() throws Exception {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("email", "rowter100@gmail.com");
        form.add("username", "rowter100");
        form.add("password", "rowter100");

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.post("/newaccount")
                .params(form)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("Successful registration"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    // Login Controller
    @Test
    public void signUpShouldReturn400() throws Exception {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("email", "rowter8@gmail.com"); // this is an existing account inside the database
        form.add("username", "rowter8");
        form.add("password", "rowter8");

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.post("/newaccount")
                .params(form)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("user"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @AfterEach
    public void delete() {
        userRepo.removeUserDetails("rowter100");
    }

    @Test
    public void checkOrderShouldReturn200() {

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/checkOrderId")
                .queryParam("orderid", "2fe02bdb");
                // .accept(MediaType.APPLICATION_JSON_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertFalse(payload.contains("Order ID is not valid"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void unableToCheckOrderId() {

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/checkOrderId")
                .queryParam("orderid", "2fe02bww");
                // .accept(MediaType.APPLICATION_JSON_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void makeOrderShouldReturn200() throws Exception {

        MultiValueMap<String, String> orderForm = new LinkedMultiValueMap<>();
        orderForm.add("qty-0", "12");
        orderForm.add("qty-1", "23");

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.post("/make_order")
                .params(orderForm)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void unableToMakeOrder() throws Exception {

        MultiValueMap<String, String> orderForm = new LinkedMultiValueMap<>();
        orderForm.add("qty-0", "0");
        orderForm.add("qty-1", "");

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.post("/make_order")
                .params(orderForm)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void redirectToRegisterTemplate() {

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/register");

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
	void redirectToOrderTemplate() throws Exception {
			
		RequestBuilder request = MockMvcRequestBuilders.get("/order");												
		MvcResult result = mvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(200, status);

	}

    @Test
	void redirectToCheckoutTemplate() throws Exception {
			
		RequestBuilder request = MockMvcRequestBuilders.get("/checkout");												
		MvcResult result = mvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(200, status);

	}

    @Test
	void redirectToHomeTemplate() throws Exception {
			
		RequestBuilder request = MockMvcRequestBuilders.get("/goHome");												
		MvcResult result = mvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(200, status);

	}

    @Test
    public void ableToCallPricesAndShouldReturn200() {

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/getprices")
                .queryParam("currency", "SGD");
                // .accept(MediaType.APPLICATION_JSON_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }


}
