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
    String currency;
    User userDetails;
    String username;

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
            this.username = user.getUsername();

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
        
        return mvc;
    }

    @GetMapping(path = "/getprices")
    public ModelAndView getMetalPrice( @RequestParam String currency ) {

        System.out.printf(">>> currency = %s\n", currency);

        Optional<Metal> opt = metalSvc.getPrice(currency);

        ModelAndView mvc = new ModelAndView();

        Metal metal = opt.get();

        mvc.addObject("username", this.username);
        mvc.addObject("metal", metal.getMetal());
        mvc.addObject("price", metal.getPrice());
        mvc.addObject("currency", metal.getCurrency());
        mvc.setViewName("result");

        this.currency = metal.getCurrency();
        this.goldPrice = metal.getPrice();
        this.silverPrice = metal.getPrice(); // by right there is a need to call another API to get the price of Silver. However, I have limited API calls. 

        // if (metal.getMetal() == "XAU") {
        //     this.goldPrice = metal.getPrice();
        // } else {
        //     this.silverPrice = metal.getPrice();
        // }
 
        System.out.println(">>>>>> getPrice activated: " );
        return mvc;
    }

    @GetMapping("/order")
    public ModelAndView makeNewOrder() {

        System.out.printf(">>> currency = %s,  price = %s\n", this.currency , this.goldPrice);
        ModelAndView mvc = new ModelAndView();
        mvc = new ModelAndView("order");
        mvc.addObject("currency", this.currency);
        mvc.addObject("gold_price", this.goldPrice);
        mvc.addObject("silver_price", this.goldPrice);
        return mvc;
    }

    @PostMapping("/make_order")
    public ModelAndView saveOrder(@RequestBody MultiValueMap<String,String> orderForm) {

        ModelAndView mvc = new ModelAndView();

        Optional<Order> optOrder = create(orderForm);

        if (optOrder.isEmpty()) {
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
            mvc.addObject("error_message", "Unable to create individual line order");
            return mvc;
        } 
        
        Order finalOrder = optOrder.get();

        System.out.printf(">>> orderID generated = %s\n", finalOrder.getOrderId());
        System.out.printf(">>> items in the metal list = %d\n", finalOrder.getMetalList().size());
        System.out.printf(">>> username = %s\n", this.username);

        Optional<String> finalOrderId = orderSvc.saveOrderDetails(finalOrder, this.username);

        if (finalOrderId.isPresent()) {
            mvc.addObject("orderId", finalOrderId);
            mvc.setStatus(HttpStatus.CREATED);
            mvc.setViewName("finalOrder");
        } else {
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
        }

        return mvc;
    }


    private Optional<Order> create(MultiValueMap<String, String> payload) {
    
        Order newOrder = new Order();

        String orderId = UUID.randomUUID().toString().substring(0, 8);
        newOrder.setOrderId(orderId);

        newOrder.setUsername(this.username);

        // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        // try {
        //     newOrder.setDate(sdf.parse(payload.getFirst("date")));
        // } catch (Exception ex) {
        //     ex.printStackTrace();
        //     return Optional.empty();
        // }
        

        for (int i = 0; i < 2; i++)  {

            Metal metalItem = new Metal();
            metalItem.setCurrency(this.currency);
            metalItem.setPrice(this.goldPrice);

            if (i == 0) {
                metalItem.setMetal("Gold");
            } else {
                metalItem.setMetal("Silver");
            }

            String _qty = payload.getFirst("qty-%d".formatted(i));
            if ((null == _qty) || (0 == _qty.trim().length()))
                _qty = "0";
            Integer quantity = Integer.parseInt(_qty);

            metalItem.setAmount(quantity);

            newOrder.addMetalList(metalItem);

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


    @GetMapping("/checkOrderId")
    public ModelAndView getPreviousOrder(@RequestParam String orderid) {
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("checkOrder");
        // mvc = new ModelAndView("redirect:/register");
        return mvc;
    }


}
