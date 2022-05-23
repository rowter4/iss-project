package finalProject.ssfpaf.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.ssfpaf.project.models.User;
import finalProject.ssfpaf.project.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

    public Optional<User> userIsAuthenticated(String email, String password) {

        Optional<User> opt = userRepo.findUserByEmail(email,password);

        if (opt.isEmpty())
            return Optional.empty();   
        
        User user = opt.get();
        return Optional.of(user);
    }

    // public boolean userIsCreated(User newUser) {
    //     return 1 == userRepo.createNewUser(newUser.getUsername(),newUser.getEmail(),newUser.getPassword());
    // }

    public void addNewUser(User user) throws UserException {
        // check if the user already exists
        Optional<User> opt = userRepo.findUserByEmail(user.getEmail(), user.getPassword());

        if (opt.isPresent()) // if user exists, then cannot register again
            throw new UserException("%s is already a registered user".formatted(user.getUsername()));
        else { // can add new user 
            boolean opt2 = userRepo.addUserDetails(user.getUsername(), user.getEmail(), user.getPassword());
            if (!opt2)
                throw new UserException("Unable to add %s as new user. Please check with admin".formatted(user.getUsername()));
        }
      
    }
           
}
