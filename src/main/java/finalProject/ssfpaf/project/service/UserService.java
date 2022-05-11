package finalProject.ssfpaf.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.ssfpaf.project.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

    public boolean userIsAuthenticated(String email, String password) {
        return 1 == userRepo.getUsername(email,password);
            
    }
}
