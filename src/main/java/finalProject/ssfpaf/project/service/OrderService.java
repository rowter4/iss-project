package finalProject.ssfpaf.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finalProject.ssfpaf.project.models.Metal;
import finalProject.ssfpaf.project.models.Order;
import finalProject.ssfpaf.project.repository.DetailOrderRepository;
import finalProject.ssfpaf.project.repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    public OrderRepository orderRepo;

    @Autowired
    private DetailOrderRepository detailOrderRepo;

    @Transactional
    public Optional<String> saveOrderDetails(Order finalOrder, String username) {
        try {
            System.out.printf(">>> running transactional details and populate the table \n");
            orderRepo.insertOrderDetails(finalOrder, username);
            detailOrderRepo.insertAllOrderDetails(finalOrder);
            
        } catch (Exception ex) {
            return Optional.empty();
        }

        return Optional.of(finalOrder.getOrderId());
    }


    public Optional<Metal> getGoldDetails(String orderid) {

        Optional<Metal> opt = detailOrderRepo.getGoldByOrderId(orderid);
        if (opt.isEmpty())
            return Optional.empty();

        return Optional.of(opt.get());
    }

    public Optional<Metal> getSilverDetails(String orderid) {

        Optional<Metal> opt = detailOrderRepo.getSilverByOrderId(orderid);
        if (opt.isEmpty())
            return Optional.empty();
            
        return Optional.of(opt.get());
    }

}
