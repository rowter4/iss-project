package finalProject.ssfpaf.project.controller;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import finalProject.ssfpaf.project.models.IndividualItem;
import finalProject.ssfpaf.project.models.Metal;
import finalProject.ssfpaf.project.models.Order;
import finalProject.ssfpaf.project.models.User;
import finalProject.ssfpaf.project.service.MetalService;
import finalProject.ssfpaf.project.service.OrderService;
import finalProject.ssfpaf.project.service.UserException;
import finalProject.ssfpaf.project.service.UserService;

import static finalProject.ssfpaf.project.models.ConversionUtils.*;


@Controller
@RequestMapping
public class AuthenticateController {
    
    @Autowired
    public UserService userSvc;

    @Autowired
    public MetalService metalSvc;

    @Autowired
    public OrderService orderSvc;

    Integer goldPrice;
    Integer silverPrice;
    User userDetails;

    @PostMapping("/authenticate")
    public ModelAndView getAuthentication(@RequestBody MultiValueMap<String,String> form) {
        
        String email = form.getFirst("email");
        String password = form.getFirst("password");

        ModelAndView mvc = new ModelAndView();

        System.out.printf("+++ username: %s, password: %s\n", email, password);

        Optional<User> opt = userSvc.userIsAuthenticated(email,password);
        
        if (opt.isPresent()) {
            User user = opt.get();
            mvc.setViewName("welcome");
            mvc.addObject("username", user.getUsername());

        } else {
            mvc.setViewName("relogin");
            mvc.addObject("alertMode", "fail"); 
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
    public ModelAndView getNewAccount(@RequestBody MultiValueMap<String,String> form ) throws UserException {

        User user = convert(form);

        System.out.println(">>>>>> user new Email: " + user.getEmail());
        System.out.println(">>>>>> user new username: " + user.getEmail());

        ModelAndView mvc = new ModelAndView();

        try {
            userSvc.addNewUser(user);
            mvc.setViewName("success-registered");
            mvc.addObject("message", "Successful registration for %s".formatted(user.getUsername()));
            mvc.addObject("username", user.getUsername());

        } catch (UserException ex) {
            mvc.setViewName("success-registered");
            mvc.addObject("username", user.getUsername());
            mvc.addObject("message", "%s".formatted(ex.getReason()));
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            ex.printStackTrace();
        }

        
        
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
            this.goldPrice = metal.getPrice();
        } else {
            this.silverPrice = metal.getPrice();
        }
 
        System.out.println(">>>>>> getPrice activated: " );
        // mvc = new ModelAndView("redirect:/register");
        return mvc;
    }

    @GetMapping("/order")
    public ModelAndView makeNewOrder() {
        ModelAndView mvc = new ModelAndView();
        mvc = new ModelAndView("order");
        mvc.addObject("gold_price", this.goldPrice);
        mvc.addObject("silver_price", this.silverPrice);
        return mvc;
    }

    @PostMapping("/make_order")
    public ModelAndView saveOrder(@RequestBody MultiValueMap<String,String> orderForm) {

        ModelAndView mvc = new ModelAndView();
        Optional<Order> optOrder = create(orderForm);
        
        Order finalOrder = optOrder.get();

        Optional<String> saveFinalOrder = orderSvc.saveOrderDetails(finalOrder);

        if (saveFinalOrder.isPresent()) {
            mvc.addObject("orderId", finalOrder.getOrderId());
            mvc.addObject("details",finalOrder.getIndividualItems());
            mvc.setStatus(HttpStatus.CREATED);
            mvc.setViewName("finalOrder");
        } else {
            mvc.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mvc.setViewName("error");
        }

        return mvc;
    }


    private Optional<Order> create(MultiValueMap<String, String> payload) {

        Order newOrder = new Order();

        String orderId = UUID.randomUUID().toString().substring(0, 8);
        newOrder.setOrderId(orderId);

        // newOrder.setUsername(userDetails.getUsername());
        newOrder.setUsername("mockDataHere");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            newOrder.setDate(sdf.parse(payload.getFirst("date")));
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
        

        int i = 0;
        while (true) {

            String _qty = payload.getFirst("qty-%d".formatted(i));
            if ((null == _qty) || (0 == _qty.trim().length()))
                break;
            Integer quantity = Integer.parseInt(_qty);

            String _price = payload.getFirst("price-%d".formatted(i));
            Integer price = Integer.parseInt(_price);

            IndividualItem individualItem = new IndividualItem();

            individualItem.setAmount(quantity);
            individualItem.setPrice(price);

            newOrder.addLineItem(individualItem);

            i++;

            // String _prodId = payload.getFirst("prod_id-%d".formatted(i));
            // if ((null == _prodId) || (0 == _prodId.trim().length()))
            //     break;
            // String _qty = payload.getFirst("qty-%d".formatted(0));
            // Integer productId = Integer.parseInt(_prodId);
            // Integer quantity = Integer.parseInt(_qty);
            // LineItem lineItem = new LineItem();
            // lineItem.setProductId(productId);
            // lineItem.setQuantity(quantity);
            // po.addLineItem(lineItem);
            // i++;
        }

        return Optional.of(newOrder);
    }


    @GetMapping("/checkout")
    public ModelAndView getFinalBill() {
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("finalOrder");
        // mvc = new ModelAndView("redirect:/register");
        return mvc;
    }


    @GetMapping("/checkOrder")
    public ModelAndView getPreviousOrder() {
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("checkOrder");
        // mvc = new ModelAndView("redirect:/register");
        return mvc;
    }


}
