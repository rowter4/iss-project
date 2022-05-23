package finalProject.ssfpaf.project.repository;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// import finalProject.ssfpaf.project.models.IndividualItem;
import finalProject.ssfpaf.project.models.Metal;
import finalProject.ssfpaf.project.models.Order;

import static finalProject.ssfpaf.project.repository.Queries.*;

@Repository
public class DetailOrderRepository {
    
    @Autowired
    public JdbcTemplate template;

    public boolean insertAllOrderDetails(Order orderDetails) {
        for (Metal perItem: orderDetails.getMetalList())
            if (!insertLineItem(orderDetails.getOrderId(), perItem))
                return false;
        return true;
    }

    public boolean insertLineItem(String orderId, Metal perItem) {
        int count = template.update(SQL_INSERT_ITEMS_LIST,
                                    orderId, perItem.getCurrency(), perItem.getPrice() , perItem.getAmount(), perItem.getMetal());
        return 1 == count;
    }
}
