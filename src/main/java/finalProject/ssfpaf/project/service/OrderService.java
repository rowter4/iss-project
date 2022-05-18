package finalProject.ssfpaf.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.ssfpaf.project.repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    public OrderRepository orderRepo;
    
}
