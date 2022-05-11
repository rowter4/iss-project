package finalProject.ssfpaf.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import finalProject.ssfpaf.project.service.UserService;

@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {
    
    @Autowired
    private UserService userSvc;

    @GetMapping
    public ModelAndView getAuthentication(@RequestBody MultiValueMap<String,String> form) {
        
        String username = form.getFirst("username");
        String email = form.getFirst("email");
        String password = form.getFirst("password");

        ModelAndView mvc = new ModelAndView();

        if (userSvc.userIsAuthenticated(email,password)) {
            mvc.setViewName("welcome");
            mvc.addObject("username", username);

        }
        
        return null;
    }
}
