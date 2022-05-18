package finalProject.ssfpaf.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
// import org.springframework.util.MultiValueMap;

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

        // return 1 == userRepo.getUsername(email,password);    
        
        User user = opt.get();
        // game.setComments(gameRepo.getCommentsByGid(gid));
        return Optional.of(user);
    }

    

    public boolean userIsCreated(User newUser) {
        return 1 == userRepo.createNewUser(newUser.getUsername(),newUser.getEmail(),newUser.getPassword());
    }

    public void addNewUser(User user) throws UserException {

        boolean opt = userRepo.addUserDetails(user.getUsername(), user.getEmail(), user.getPassword());
        // if (opt.isPresent())
        //     throw new BFFException("%s is already your BFF".formatted(bff.getName()));

        if (!userRepo.addUserDetails(user.getUsername(), user.getEmail(), user.getPassword()))
            throw new UserException("Cannot add %s as new user. Please check with admin".formatted(user.getUsername()));
    }
        
    
}
