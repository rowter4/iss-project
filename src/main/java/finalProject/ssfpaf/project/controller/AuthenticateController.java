package finalProject.ssfpaf.project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import finalProject.ssfpaf.project.models.Metal;
import finalProject.ssfpaf.project.models.Order;
import finalProject.ssfpaf.project.models.User;
import finalProject.ssfpaf.project.service.MetalService;
import finalProject.ssfpaf.project.service.UserService;

import static finalProject.ssfpaf.project.models.ConversionUtils.*;


@Controller
@RequestMapping
public class AuthenticateController {
    
    @Autowired
    public UserService userSvc;

    @Autowired
    public MetalService metalSvc;

    Integer goldPrice;
    Integer silverPrice;

    @PostMapping("/authenticate")
    public ModelAndView getAuthentication(@RequestBody MultiValueMap<String,String> form) {
        
        // String username = form.getFirst("username");
        String email = form.getFirst("email");
        String password = form.getFirst("password");

        ModelAndView mvc = new ModelAndView();

        System.out.printf("+++ username: %s, password: %s\n", email, password);


        // ModelAndView mvc = new ModelAndView();

        // mvc.setViewName("index");
        Optional<User> opt = userSvc.userIsAuthenticated(email,password);
        
        User user = opt.get();

        if (opt.get() != null) {
            mvc.setViewName("welcome");
            mvc.addObject("username", user.getUsername());
            // mvc.addObject("username", username);

        } else {
            mvc.setViewName("error");
            mvc.addObject("message", "Log in failed");
        }

      
        
        return mvc;
    }

    @GetMapping("/register")
    public ModelAndView getRegistration() {
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("register");
        // mvc = new ModelAndView("redirect:/register");
        return mvc;
    }


    @PostMapping("/newaccount")
    // public ModelAndView getNewAccount(@RequestParam String username
    // , @RequestParam String email, @RequestParam String password) {
    public ModelAndView getNewAccount(@RequestBody MultiValueMap<String,String> form ) {

        User user = convert(form);

        System.out.println(">>>>>> user: " + user);
        ModelAndView mvc = new ModelAndView();

        try {
            userSvc.addNewUser(user);
            mvc.addObject("message", "%s has been added as one of your bff".formatted(user.getUsername()));
            mvc.addObject("username", user.getUsername());
        //     // Date dob = format.parse(form.getFirst("dob"));
        //     // bff.setDob(dob);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mvc.setViewName("success-registered");
        
        // String username = form.getFirst("username");
        // String email = form.getFirst("email");
        // String password = form.getFirst("password");

        // System.out.printf(">>> q = %s, limit = %d, rating = %s\n", username, email, password);

       

        // User user = new User();

        // user.setUsername(form.getFirst("name"));
        // user.setEmail(form.getFirst("email"));
        // user.setPassword(form.getFirst("password"));

        // System.out.printf(">>> username = %d\n", form.getFirst("name"));
        // System.out.printf(">>> email = %d\n", form.getFirst("email"));
        // System.out.printf(">>> password = %d\n", form.getFirst("password"));

        // boolean userId = userSvc.userIsCreated(user); 

        // if (!userSvc.userIsCreated(user.getUsername(), user.getEmail(), user.getPassword())) {
        //     mvc.setViewName("welcome");
        //     mvc.addObject("username", user.getUsername());

        // }
        
        return mvc;
    }

    @GetMapping(path = "/getprices")
    public ModelAndView getMetalPrice( @RequestParam String type, @RequestParam String currency ) {

        System.out.printf(">>> type = %s, currency = %s\n", type, currency);

        Optional<Metal> opt = metalSvc.getPrice(type,currency);

        ModelAndView mvc = new ModelAndView();

        Metal metal = opt.get();


        mvc.addObject("metal", metal.getMetal());
        mvc.addObject("price", metal.getPrice());
        mvc.setViewName("result");

        if (metal.getMetal() == "XAU") {
            goldPrice = metal.getPrice();
        } else {
            silverPrice = metal.getPrice();
        }
 
        System.out.println(">>>>>> getPrice activated: " );
        // mvc = new ModelAndView("redirect:/register");
        return mvc;
    }

    @GetMapping("/order")
    public ModelAndView makeNewOrder() {
        ModelAndView mvc = new ModelAndView();
        mvc = new ModelAndView("order");
        mvc.addObject("gold_price", goldPrice);
        mvc.addObject("silver_price", silverPrice);
        return mvc;
    }

    @PostMapping("/make_order")
    public ModelAndView saveOrder(@RequestBody MultiValueMap<String,String> orderForm) {

        ModelAndView mvc = new ModelAndView();
        Optional<Order> optOrder = create(orderForm);
        
        return null;
    }


    private Optional<Order> create(MultiValueMap<String, String> payload) {

        Order newOrder = new Order();
        // newOrder.setUsername();

        return null;
    }


}
